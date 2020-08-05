package cc.runa.rsupport.network.exception;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class CodeException {

    /*网络错误*/
    public static final int NETWORD_ERROR = 0x1;
    /*http_错误*/
    public static final int HTTP_ERROR = 0x2;
    /*fastjson错误*/
    public static final int JSON_ERROR = 0x3;
    /*请求超时*/
    public static final int HTTP_TIME_OUT = 0x4;
    /*未知错误*/
    public static final int UNKNOWN_ERROR = 0x5;
    /*运行时异常-包含自定义异常*/
    public static final int RUNTIME_ERROR = 0x6;
    /*无法解析该域名*/
    public static final int UNKOWNHOST_ERROR = 0x7;

    @IntDef({NETWORD_ERROR, HTTP_ERROR, RUNTIME_ERROR, UNKNOWN_ERROR, JSON_ERROR, UNKOWNHOST_ERROR, HTTP_TIME_OUT})
    @Retention(RetentionPolicy.SOURCE)

    public @interface CodeEp {

    }

}