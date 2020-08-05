package cc.runa.rsupport.frame;

import android.content.Context;

import java.lang.ref.WeakReference;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import retrofit2.Retrofit;

/**
 * P —— View Model 调度对象
 *
 * @author JokerCats on 2020.08.03
 */
public abstract class BasePresenter<V extends BaseView> {

    private V mProxyView;
    private WeakReference<V> mViewReference;
    private CompositeDisposable mDisposables;
    protected Retrofit mRetrofit;

    /**
     * 绑定 View (避免持有View的Presenter执行耗时操作而引起内存泄漏)
     */
    @SuppressWarnings("unchecked")
    public void attachView(V view) {
        mViewReference = new WeakReference<>(view);

        mProxyView = (V) Proxy.newProxyInstance(
                view.getClass().getClassLoader(),
                view.getClass().getInterfaces(),
                new AvoidNullPointerHandler(mViewReference.get())
        );

        mRetrofit = init();
    }

    /**
     * 解绑 View
     */
    public void detachView() {
        if (isViewAttached()) {
            mViewReference.clear();
            mViewReference = null;
        }
        unSubscribe();
    }

    protected Context getContext() {
        return mProxyView.getContext();
    }

    protected V getView() {
        return mProxyView;
    }

    protected void showLoading() {
        mProxyView.showLoading();
    }

    private void hideLoading() {
        mProxyView.hideLoading();
    }

    protected boolean isViewAttached() {
        return mViewReference != null && mViewReference.get() != null;
    }

    public abstract Retrofit init();

    /**
     * 绑定 disposable
     */
    public void addSubscribe(Disposable disposable) {
        if (mDisposables == null) {
            mDisposables = new CompositeDisposable();
        }
        mDisposables.add(disposable);
    }

    /**
     * 解绑 disposable (取消网络请求 & 避免内存泄露)
     */
    public void unSubscribe() {
        if (mDisposables != null && mDisposables.isDisposed()) {
            mDisposables.dispose();
        }
    }

    /**
     * View 代理类
     *
     * 防止页面关闭后 P 异步回调 调用 V 方法导致空指针异常
     */
    private class AvoidNullPointerHandler implements InvocationHandler {

        private BaseView view;

        public AvoidNullPointerHandler(BaseView view) {
            this.view = view;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            // V 还存在的情况下，执行 V 的方法
            if (isViewAttached()) {
                return method.invoke(view, args);
            }
            return null;
        }
    }
}
