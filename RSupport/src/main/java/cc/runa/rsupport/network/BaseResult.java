package cc.runa.rsupport.network;

import com.google.gson.annotations.SerializedName;

/**
 * 网络请求固定返回信息（用于判断请求状态）
 *
 * @author JokerCats on 2020.02.19
 */
public class BaseResult {

    @SerializedName("code")
    private int code;

    @SerializedName("msg")
    private String msg;

    public int getCode() {
        return code;
    }

    boolean isSuccess() {
        return 0 == code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
