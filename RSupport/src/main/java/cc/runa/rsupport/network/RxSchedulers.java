package cc.runa.rsupport.network;


import java.io.IOException;
import java.util.concurrent.TimeUnit;

import cc.runa.rsupport.R;
import cc.runa.rsupport.base.BaseApplication;
import cc.runa.rsupport.utils.RLog;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;


/**
 * Simplified thread switching schedule file
 *
 * @author JokerCats on 2020.02.21
 */
public class RxSchedulers {

    // Number of connections
    private int mConnectTimes = 3;
    // Number of retry times
    private int mRetryTimes = 0;
    // Time of wait
    private int mWaitTime = 0;

    /**
     * Handling threads, network exceptions, etc
     */
    public <T> ObservableTransformer<T, T> compose() {
        return observable -> observable
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(disposable -> {

                })
                .retryWhen(throwableObservable -> throwableObservable.flatMap((Function<Throwable, ObservableSource<?>>) throwable -> {
                    RLog.e("Http Request Exception is：" + throwable.toString());
                    // 只对网络异常进行处理
                    if (throwable instanceof IOException) {
                        if (mRetryTimes < mConnectTimes) {
                            mRetryTimes++;
                            mWaitTime = 1000 + mRetryTimes * 1000;
                            return Observable.just(1).delay(mWaitTime, TimeUnit.MILLISECONDS);
                        } else {
                            resetTimes();
                            return Observable.error(new Throwable(BaseApplication.getInstance().getString(R.string.warn_network_busy)));
                        }
                    } else {
                        resetTimes();
                        return Observable.error(throwable);
                    }
                }))
                .observeOn(AndroidSchedulers.mainThread());
    }

    private void resetTimes() {
        mRetryTimes = 0;
        mWaitTime = 0;
    }
}
