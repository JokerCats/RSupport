package cc.runa.rsupport.widget.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.AnimationDrawable;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import cc.runa.rsupport.R;

/**
 * Load animation for time-consuming operations
 *
 * @author JokerCats on 2020.02.24
 */
public class LoadingDialog extends Dialog implements DialogInterface.OnDismissListener {

    public static final int SPINNER_APPLE = 0;
    public static final int SPINNER_FADE = 1;

    private static LoadingBuilder sBuilder;

    private Context mContext;
    private Handler mHandler;
    private LoadingListener mListener;

    private TextView mTipsTv;
    private ImageView mSuccessIv, mFailureIv, mSpinnerIv;
    private AnimationDrawable mSpinnerDrawable;

    public static LoadingBuilder with(Context context) {
        sBuilder = new LoadingBuilder(context);
        return sBuilder;
    }

    /**
     * 获取 Loading 框配置信息
     *
     * @return sBuilder
     */
    public LoadingBuilder getConfig() {
        return sBuilder;
    }

    private LoadingDialog(LoadingConfig config) {
        super(config.context, config.themeId);
        mContext = config.context;
        mHandler = new Handler();

        View convertView = getLayoutInflater().inflate(R.layout.dialog_loading, null);
        mTipsTv = convertView.findViewById(R.id.tv_loading_tips);
        mSuccessIv = convertView.findViewById(R.id.iv_loading_success);
        mFailureIv = convertView.findViewById(R.id.iv_loading_failure);
        mSpinnerIv = convertView.findViewById(R.id.iv_loading_spinner);

        if (!TextUtils.isEmpty(config.tips)) {
            mTipsTv.setText(config.tips);
        }

        if (config.successId != 0) {
            mSuccessIv.setImageResource(config.successId);
        }

        if (config.failureId != 0) {
            mFailureIv.setImageResource(config.failureId);
        }

        mListener = config.listener;
        if (mListener == null) {
            setOnDismissListener(this);
        } else {
            setOnDismissListener(dialog -> mListener.onDismiss());
            setOnCancelListener(dialog -> mListener.onCancel());
        }

        setContentView(convertView);
        setSpinnerType(config.type);
        setCancelable(config.cancelable);
        setCanceledOnTouchOutside(config.touchOutside);
    }

    private static class LoadingConfig {
        private Context context;
        private String tips;
        private int successId = R.mipmap.ic_loading_success;
        private int failureId = R.mipmap.ic_loading_failure;
        private int type;
        private int themeId = R.style.LoadingDialog;
        private boolean cancelable = true;
        private boolean touchOutside = false;
        private LoadingListener listener;
    }

    public static class LoadingBuilder {

        private LoadingConfig mConfig;

        LoadingBuilder(Context context) {
            mConfig = new LoadingConfig();
            mConfig.context = context;
        }

        public LoadingDialog create() {
            return new LoadingDialog(mConfig);
        }

        public void clear() {
            mConfig = null;
        }

        /**
         * 设置Loading弹框的提示信息
         *
         * @param tips 提示内容
         */
        public LoadingBuilder setTips(String tips) {
            mConfig.tips = tips;
            return this;
        }

        public LoadingBuilder setSuccess(int success) {
            mConfig.successId = success;
            return this;
        }

        public LoadingBuilder setFailure(int failure) {
            mConfig.failureId = failure;
            return this;
        }

        public LoadingBuilder setType(int type) {
            mConfig.type = type;
            return this;
        }

        public LoadingBuilder setThemeId(int themeId) {
            mConfig.themeId = themeId;
            return this;
        }

        public LoadingBuilder setCancelable(boolean cancelable) {
            mConfig.cancelable = cancelable;
            return this;
        }

        public LoadingBuilder setTouchOutside(boolean touch) {
            mConfig.touchOutside = touch;
            return this;
        }

        public LoadingBuilder onLoadingListener(LoadingListener listener) {
            mConfig.listener = listener;
            return this;
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        mSpinnerIv.post(() -> mSpinnerDrawable.start());
    }

    @Override
    public void show() {
        if (isActivated((Activity) mContext)) {
            resetLoading();
            super.show();
        }
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        mSpinnerDrawable.stop();
        mSpinnerDrawable.selectDrawable(0);
        super.dismiss();
    }

    /**
     * Set loading spinner type
     *
     * @param spinnerType 0 apple | 1 fade
     */
    private void setSpinnerType(int spinnerType) {
        if (spinnerType == SPINNER_APPLE) {
            mSpinnerIv.setImageResource(R.drawable.spinner_round_apple);
        } else {
            mSpinnerIv.setImageResource(R.drawable.spinner_round_fade);
        }
        mSpinnerDrawable = (AnimationDrawable) mSpinnerIv.getDrawable();
    }

    public void dismissWithSuccess(String message) {
        showSuccessImage();

        if (TextUtils.isEmpty(message)) {
            mTipsTv.setText(mContext.getString(R.string.text_tips_success));
        } else {
            mTipsTv.setText(message);
        }

        mHandler.postDelayed(this::dismiss, 500);
    }

    public void dismissWithFailure(String message) {
        showFailureImage();

        if (TextUtils.isEmpty(message)) {
            mTipsTv.setText(mContext.getString(R.string.text_tips_failure));
        } else {
            mTipsTv.setText(message);
        }

        mHandler.postDelayed(this::dismiss, 500);
    }

    private void showSuccessImage() {
        mSpinnerIv.setVisibility(View.GONE);
        mSuccessIv.setVisibility(View.VISIBLE);
    }

    private void showFailureImage() {
        mSpinnerIv.setVisibility(View.GONE);
        mFailureIv.setVisibility(View.VISIBLE);
    }

    private void resetLoading() {
        mSpinnerIv.setVisibility(View.VISIBLE);
        mFailureIv.setVisibility(View.GONE);
        mSuccessIv.setVisibility(View.GONE);
//        mTipsTv.setText(mContext.getString(R.string.text_tips_loading));
        mTipsTv.setText(sBuilder.mConfig.tips);
    }

    /**
     * Check if the activity is active
     */
    private boolean isActivated(Activity activity) {
        boolean res = (activity != null) && !activity.isFinishing();
        return res && !activity.isDestroyed();
    }

    public interface LoadingListener {

        void onDismiss();

        void onCancel();
    }
}
