package cc.runa.rsupport.network;

import cc.runa.rsupport.network.exception.FactoryException;
import io.reactivex.Observable;
import io.reactivex.functions.Function;


/**
 * 异常处理
 */

public class ExceptionFunc<T> implements Function<Throwable, Observable<T>> {

    @Override
    public Observable<T> apply(Throwable throwable) throws Exception {
        return Observable.error(FactoryException.analysisExcetpion(throwable));
    }
}