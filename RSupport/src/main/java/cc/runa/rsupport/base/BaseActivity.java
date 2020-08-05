package cc.runa.rsupport.base;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Looper;
import android.view.MotionEvent;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import cc.runa.rsupport.bean.DefaultEvent;
import cc.runa.rsupport.utils.EventBusUtils;
import cc.runa.rsupport.utils.KeyboardUtil;
import cc.runa.rsupport.utils.RLog;
import cc.runa.rsupport.widget.agent.CenterToast;
import cc.runa.rsupport.widget.dialog.LoadingDialog;

/**
 * 基础封装 Activity
 *
 * @author JokerCats on 2020.08.03
 */
public abstract class BaseActivity extends AppCompatActivity {

    protected Context mContext;
    protected CenterToast mToastMaster;
    protected LoadingDialog mLoadingDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mContext = this;

        try {
            setContentView(bindLayoutID());
        } catch (Resources.NotFoundException ex) {
            RLog.e("Wait!! ==>  Unbound layout resource ID");
        }

        BaseApplication.getMonitor().pourActivity(this);

        if (registerEvent()) {
            EventBusUtils.register(this);
        }

        mToastMaster = new CenterToast(mContext);

        mLoadingDialog = LoadingDialog.with(this).create();

    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        initResView();
    }

    @Override
    protected void onDestroy() {
        if (mToastMaster != null) {
            mToastMaster.intercept();
        }

        if (mLoadingDialog != null && mLoadingDialog.isShowing()) {
            mLoadingDialog.dismiss();
            mLoadingDialog = null;
        }

        if (registerEvent()) {
            EventBusUtils.unregister(this);
        }

        BaseApplication.getMonitor().drainActivity(this);
        super.onDestroy();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        KeyboardUtil.INSTANCE.hideSoftInput(this, ev);
        return super.dispatchTouchEvent(ev);
    }

    protected abstract int bindLayoutID();

    protected abstract void initResView();

    /**
     * 需要接收事件 重写该方法 并返回true
     */
    protected boolean registerEvent() {
        return false;
    }

    /**
     * 子类接收事件 需重写该方法
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventBus(DefaultEvent event) {

    }

    /**
     * 可在子线程中打印的 Toast
     *
     * @param tips 提示信息
     */
    protected void showToast(String tips) {
        if (Looper.getMainLooper() == Looper.myLooper()) {
            mToastMaster.updateTips(tips).launch();
        } else {
            Looper.prepare();
            mToastMaster.updateTips(tips).launch();
            Looper.loop();
        }
    }

}
