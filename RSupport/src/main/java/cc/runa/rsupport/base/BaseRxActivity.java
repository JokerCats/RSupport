package cc.runa.rsupport.base;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;

import androidx.annotation.Nullable;

import cc.runa.rsupport.R;
import cc.runa.rsupport.frame.BasePresenter;
import cc.runa.rsupport.frame.BaseView;

/**
 * 基础封装 Activity (MVP)
 *
 * @author JokerCats on 2020.08.03
 */
@SuppressWarnings("unchecked")
public abstract class BaseRxActivity<P extends BasePresenter> extends BaseActivity implements BaseView {

    protected P mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = createPresenter();
        if (mPresenter != null) {
            mPresenter.attachView(this);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.detachView();
            mPresenter = null;
        }

    }

    @Override
    public void showLoading() {
        showLoading(getString(R.string.text_tips_loading));
    }

    public void showLoading(String tips) {
        if (mLoadingDialog != null && !mLoadingDialog.isShowing()) {
            if (!TextUtils.isEmpty(tips)) {
                mLoadingDialog.getConfig().setTips(tips);
            }
            mLoadingDialog.show();
        }
    }

    @Override
    public void hideLoading() {
        hideLoading(false, null);
    }

    public void hideLoading(boolean isFailure, String tips) {
        if (!isFinishing() && mLoadingDialog != null && mLoadingDialog.isShowing()) {
            if (!TextUtils.isEmpty(tips)) {
                if (isFailure) {
                    mLoadingDialog.dismissWithFailure(tips);
                } else {
                    mLoadingDialog.dismissWithSuccess(tips);
                }
            } else {
                mLoadingDialog.dismiss();
            }
        }
    }

    @Override
    public Context getContext() {
        return mContext;
    }

    @Override
    public void onFailure(String msg) {
        showToast(msg);
    }

    @Override
    public void onError(Exception ex) {

    }

    protected abstract P createPresenter();

}
