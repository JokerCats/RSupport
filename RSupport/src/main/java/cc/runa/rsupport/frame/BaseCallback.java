package cc.runa.rsupport.frame;

/**
 * 数据请求回调
 *
 * @author JokerCats on 2020.08.03
 */
public interface BaseCallback<T> {

    /**
     * 取消数据请求
     *
     * @param initiative 主动请求
     * @param msg        反馈信息
     */
    void onCancel(boolean initiative, String msg);

    /**
     * 数据请求成功
     *
     * @param data 请求到的数据
     */
    void onSuccess(T data);

    /**
     * 数据请求失败
     *
     * @param msg 请求成功但由于{@code msg}的原因无法正常返回数据
     */
    void onFailure(String msg);

    /**
     * 数据请求错误
     *
     * @param ex 在网络请求时出现网络不可用等原因导致无法连接到数据源
     */
    void onError(Exception ex);

    /**
     * 当请求数据结束时，无论请求结果是成功
     */
    void onComplete();

}