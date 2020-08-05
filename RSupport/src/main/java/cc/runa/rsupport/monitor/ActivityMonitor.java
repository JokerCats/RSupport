package cc.runa.rsupport.monitor;

import android.app.Activity;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Activity 栈管理对象
 *
 * @author JokerCats on 2020.08.03
 */
public class ActivityMonitor {

    private List<Activity> mBarrel;

    private static ActivityMonitor sInstance = null;

    private ActivityMonitor() {
        mBarrel = Collections.synchronizedList(new LinkedList<Activity>());
    }

    public static ActivityMonitor newInstance() {

        if (sInstance == null) {
            synchronized (ActivityMonitor.class) {
                if (null == sInstance) {
                    sInstance = new ActivityMonitor();
                }
            }
        }

        return sInstance;
    }

    /**
     * 将Activity缓存到栈中
     */
    public void pourActivity(Activity activity) {
        if (activity == null)
            return;
        mBarrel.add(activity);
    }

    /**
     * 将Activity从栈中移除
     */
    public void drainActivity(Class<?> cls) {
        for (Activity activity : mBarrel) {
            if (activity.getClass().equals(cls)) {
                drainActivity(activity);
                break;
            }
        }
    }

    public void drainActivity(Activity activity) {
        if (activity == null)
            return;

        if (!activity.isFinishing()) {
            activity.finish();
        }

        mBarrel.remove(activity);
    }

    /**
     * Activity栈内是否为空
     */
    public boolean isBarrelEmpty() {
        return mBarrel == null || mBarrel.isEmpty();
    }

    /**
     * 清空Activity栈并退出应用
     */
    public void clearBarrel() {
        for (Activity activity : mBarrel) {
            drainActivity(activity);
        }

        if (!mBarrel.isEmpty()) {
            mBarrel.clear();
        }
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);
    }

}
