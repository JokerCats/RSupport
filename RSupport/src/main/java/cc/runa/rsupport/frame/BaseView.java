package cc.runa.rsupport.frame;

import android.content.Context;

/**
 * V —— UI处理
 *
 * @author JokerCats on 2020.08.03
 */
public interface BaseView {

    /**
     * 显示加载框
     */
    void showLoading();

    /**
     * 隐藏加载框
     */
    void hideLoading();

    /**
     * 数据请求失败
     *
     * @param msg 失败信息
     */
    void onFailure(String msg);

    /**
     * 请求数据错误
     *
     * @param ex 异常信息
     */
    void onError(Exception ex);

    /**
     * 获取上下文
     */
    Context getContext();
}
