package cc.runa.rsupport.network;


import cc.runa.rsupport.R;
import cc.runa.rsupport.base.BaseApplication;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import retrofit2.HttpException;

/**
 * 通用观察对象
 *
 * @author JokerCats on 2020.02.20
 */
public abstract class BaseObserver<T extends BaseResult> implements Observer<T> {

    private static final String TAG = "BaseObserver";

    protected BaseObserver() {

    }

    @Override
    public void onSubscribe(Disposable disposable) {
        onHandlePrepare(disposable);
    }

    @Override
    public void onNext(T value) {
        if (value.isSuccess()) {
            onHandleSuccess(value);
        } else {
            onHandleError(value.getCode(), value.getMsg());
        }
    }

    @Override
    public void onError(Throwable throwable) {
        if (throwable instanceof HttpException) {
            HttpException httpException = (HttpException) throwable;
            // httpException.response().errorBody().string()
            int code = httpException.code();
            switch (code) {
                case 400:
                    onHandleError(code, BaseApplication.getInstance().getString(R.string.code_network_error_400));
                    break;
                case 403:
                    onHandleError(code, BaseApplication.getInstance().getString(R.string.code_network_error_403));
                    break;
                case 404:
                    onHandleError(code, BaseApplication.getInstance().getString(R.string.code_network_error_404));
                    break;
                case 500:
                    onHandleError(code, BaseApplication.getInstance().getString(R.string.code_network_error_500));
                    break;
                case 502:
                    onHandleError(code, BaseApplication.getInstance().getString(R.string.code_network_error_502));
                    break;
                case 503:
                    onHandleError(code, BaseApplication.getInstance().getString(R.string.code_network_error_503));
                    break;
                case 505:
                    onHandleError(code, BaseApplication.getInstance().getString(R.string.code_network_error_505));
                    break;
                default:
                    onHandleError(code, httpException.getMessage());
                    break;
            }
        } else {
            onHandleError(throwable.getMessage());
        }
    }

    @Override
    public void onComplete() {
        onHandleComplete();
    }

    protected abstract void onHandlePrepare(Disposable disposable);

    protected abstract void onHandleSuccess(T result);

    private void onHandleError(String msg) {
        onHandleError(1, msg);
    }

    protected abstract void onHandleError(int code, String msg);

    protected abstract void onHandleComplete();
}

