package cc.runa.rsupport.network.exception;

/**
 * 回调统一请求异常
 * Created by gaobin on 2016/12/12.
 */

public class CommonException extends Exception {
    /*错误码*/
    private String code;
    /*显示的信息*/
    private String message;

    public CommonException() {
    }

    public CommonException(String message) {
        this.message = message;
    }

    public CommonException(String code, Throwable throwable) {
        super(throwable);
        this.code = code;
        this.message = throwable.getMessage();
    }

    public CommonException(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}