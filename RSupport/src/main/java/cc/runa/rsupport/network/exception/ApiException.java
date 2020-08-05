package cc.runa.rsupport.network.exception;

/**
 * 描述：
 * 创建人：gaobin
 *  * 创建时间：2019/10/25
 */
public class ApiException extends CommonException {

    public ApiException(String code, String message) {
        super(code, message);
    }
}