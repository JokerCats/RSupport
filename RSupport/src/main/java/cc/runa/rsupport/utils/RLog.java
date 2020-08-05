package cc.runa.rsupport.utils;

import androidx.annotation.Nullable;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;

import cc.runa.rsupport.base.BaseApplication;

/**
 * Encapsulate the Logger framework
 *
 * @author JokerCats on 2020.02.21
 */
public class RLog {

    private static final String sTag = "RLog";

    /**
     * 初始化log工具，在BaseApplication文件中调用
     */
    public static void init() {
        FormatStrategy format = PrettyFormatStrategy.newBuilder()
                .showThreadInfo(false)
                .methodCount(0)
                .methodOffset(7)
                .tag(sTag)
                .build();

        Logger.addLogAdapter(new AndroidLogAdapter(format) {
            @Override
            public boolean isLoggable(int priority, @Nullable String tag) {
                return BaseApplication.isDebug();
            }
        });
    }

    /**
     * LOG TYPE ==> DEBUG
     */
    public static void d(String message) {
        d(sTag, message);
    }

    public static void d(String tag, String message) {
        Logger.t(tag).d(message);
    }

    /**
     * LOG TYPE ==> INFORMATION
     */
    public static void i(String message) {
        i(sTag, message);
    }

    public static void i(String tag, String message) {
        Logger.t(tag).i(message);
    }

    /**
     * LOG TYPE ==> WARNING
     */
    public static void w(String message) {
        w(sTag, message, null);
    }

    public static void w(String tag, String message) {
        w(tag, message, null);
    }

    public static void w(String message, Throwable throwable) {
        w(sTag, message, throwable);
    }

    public static void w(String tag, String message, Throwable throwable) {
        String info = throwable != null ? throwable.toString() : "Unspecified exception description";
        Logger.t(tag).w(message + "：" + info);
    }

    /**
     * LOG TYPE ==> ERROR
     */
    public static void e(String message) {
        e(sTag, message, null);
    }

    public static void e(String tag, String message) {
        e(tag, message, null);
    }

    public static void e(String message, Throwable throwable) {
        e(sTag, message, throwable);
    }

    public static void e(String tag, String message, Throwable throwable) {
        Logger.t(tag).e(throwable, message);
    }

    /**
     * LOG TYPE ==> JSON
     */
    public static void json(String json) {
        json(sTag, json);
    }

    public static void json(String tag, String json) {
        Logger.t(tag).json(json);
    }
}
