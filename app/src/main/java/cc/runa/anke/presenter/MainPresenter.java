package cc.runa.anke.presenter;

import cc.runa.anke.base.ApiManifest;
import cc.runa.anke.bean.NewsResult;
import cc.runa.anke.config.NetConfig;
import cc.runa.anke.contract.MainContract;
import cc.runa.rsupport.frame.BasePresenter;
import cc.runa.rsupport.network.BaseObserver;
import cc.runa.rsupport.network.RetrofitFactory;
import cc.runa.rsupport.network.RxSchedulers;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import retrofit2.Retrofit;

/**
 * @author JokerCats on 2020.08.05
 */
public class MainPresenter extends BasePresenter<MainContract.View> implements MainContract.Model {

    @Override
    public void getNews() {
        Observable<NewsResult> observable = mRetrofit.create(ApiManifest.class).getLatestNews();
        observable.compose(new RxSchedulers().compose())
                .subscribe(new BaseObserver<NewsResult>() {
                    @Override
                    protected void onHandlePrepare(Disposable disposable) {
                        addSubscribe(disposable);
                    }

                    @Override
                    protected void onHandleSuccess(NewsResult result) {
                        if (isViewAttached()) {
                            getView().getNewsSuccess(result);
                        }
                    }

                    @Override
                    protected void onHandleError(int code, String msg) {
                        if (isViewAttached()) {
                            getView().onFailure(msg);
                        }
                    }

                    @Override
                    protected void onHandleComplete() {

                    }
                });
    }

    @Override
    public Retrofit init() {
        return RetrofitFactory.getInstance().create(NetConfig.Url.getBaseUrl());
    }
}
