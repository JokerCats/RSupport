package cc.runa.rsupport.base;

import android.content.Context;
import android.os.Bundle;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import cc.runa.rsupport.bean.DefaultEvent;
import cc.runa.rsupport.utils.EventBusUtils;
import cc.runa.rsupport.widget.agent.CenterToast;
import cc.runa.rsupport.widget.dialog.LoadingDialog;

/**
 * 基础封装 Fragment
 *
 * @author JokerCats on 2020.08.03
 */
public abstract class BaseFragment extends Fragment {

    protected View mRootView;
    protected Context mContext;
    protected CenterToast mToastMaster;
    protected LoadingDialog mLoadingDialog;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
        mToastMaster = new CenterToast(mContext);
        mLoadingDialog = LoadingDialog.with(mContext).create();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        int layoutRes = bindLayoutID();
        if (layoutRes != 0) {
            mRootView = inflater.inflate(layoutRes, container, false);
        } else {
            mRootView = super.onCreateView(inflater, container, savedInstanceState);
        }

        if (registerEvent()) {
            EventBusUtils.register(this);
        }
        return mRootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initResView();
    }

    @Override
    public void onDestroy() {
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
        super.onDestroy();
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
