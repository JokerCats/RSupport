package cc.runa.rsupport.base;

import android.app.Activity;
import android.content.pm.ApplicationInfo;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.multidex.MultiDexApplication;

import cc.runa.rsupport.monitor.ActivityMonitor;
import cc.runa.rsupport.utils.RLog;

/**
 * RSupport ：Discard "completeness" & Pursue completeness.
 *
 * @author JokerCats on 2020.08.03
 */
public class BaseApplication extends MultiDexApplication {

    private static BaseApplication sInstance;

    private static ActivityMonitor sMonitor;

    private static boolean sDebug;

    @Override
    public void onCreate() {
        super.onCreate();

        sInstance = this;

        initDebug();

        RLog.init();

        registerActivityMonitor();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        sMonitor.clearBarrel();
    }

    public static BaseApplication getInstance() {
        return sInstance;
    }

    public static ActivityMonitor getMonitor() {
        return sMonitor;
    }

    /**
     * 初始化 Debug 信息
     */
    private void initDebug() {
        sDebug = getApplicationInfo() != null &&
                (getApplicationInfo().flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
    }

    /**
     * 获取 Debug 信息
     */
    public static boolean isDebug() {
        return sDebug;
    }

    private void registerActivityMonitor() {
        sMonitor = ActivityMonitor.newInstance();

        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle savedInstanceState) {
                sMonitor.pourActivity(activity);
            }

            @Override
            public void onActivityStarted(@NonNull Activity activity) {

            }

            @Override
            public void onActivityResumed(@NonNull Activity activity) {

            }

            @Override
            public void onActivityPaused(@NonNull Activity activity) {

            }

            @Override
            public void onActivityStopped(@NonNull Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(@NonNull Activity activity) {
                sMonitor.drainActivity(activity);
            }
        });
    }

}
