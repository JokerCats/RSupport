package cc.runa.rsupport.network.exception;

import org.json.JSONException;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.text.ParseException;

import retrofit2.HttpException;

/**
 * 描述：
 * -异常处理工厂
 * 主要是解析异常，输出自定义ApiException
 * 创建人：gaobin
 * 创建时间：2017/6/6
 */

public class FactoryException {
    private static final String HTTP_EXCEPTION_MSG = "网络异常，请稍后重试";
    private static final String CONNECT_EXCEPTION_MSG = "网络无连接，请检查网络";
    private static final String JSON_EXCEPTION_MSG = "数据解析失败";
    private static final String UNKNOWN_HOST_EXCEPTION_MSG = "无法解析该域名";
    private static final String UNKNOWN_EXCEPTION_MSG = "请求失败，请稍后重试";
    private static final String TIME_OUT_MSG = "网络请求超时，请稍后再试";

    private FactoryException() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * 解析异常
     *
     * @param e
     * @return
     */
    public static Exception analysisExcetpion(Throwable e) {
//        Timber.tag("analysisExcetpion").w(e);
        CommonException commonException = new CommonException();
        if (e instanceof HttpException) {
            /*网络异常*/
            commonException.setCode(String.valueOf(CodeException.HTTP_ERROR));
            commonException.setMessage(HTTP_EXCEPTION_MSG);
        } else if (e instanceof HttpTimeException) {
            /*自定义运行时异常*/
            HttpTimeException exception = (HttpTimeException) e;
            commonException.setCode(String.valueOf(CodeException.RUNTIME_ERROR));
            commonException.setMessage(exception.getMessage());
        } else if (e instanceof ConnectException) {
            /*链接异常*/
            commonException.setCode(String.valueOf(CodeException.HTTP_ERROR));
            commonException.setMessage(CONNECT_EXCEPTION_MSG);
        } else if (e instanceof SocketTimeoutException) {
            /*请求超时异常*/
            commonException.setCode(String.valueOf(CodeException.HTTP_TIME_OUT));
            commonException.setMessage(TIME_OUT_MSG);
        } else if (e instanceof JSONException || e instanceof ParseException) {
            commonException.setCode(String.valueOf(CodeException.JSON_ERROR));
            commonException.setMessage(JSON_EXCEPTION_MSG);
        } else if (e instanceof UnknownHostException) {
            /*无法解析该域名异常*/
            commonException.setCode(String.valueOf(CodeException.UNKOWNHOST_ERROR));
            commonException.setMessage(UNKNOWN_HOST_EXCEPTION_MSG);
        } else if (e instanceof ApiException) {
            commonException = (CommonException) e;
        } else {
            /*未知异常*/
            commonException.setCode(String.valueOf(CodeException.UNKNOWN_ERROR));
            commonException.setMessage(UNKNOWN_EXCEPTION_MSG);
        }
        return commonException;
    }
}