package cc.runa.rsupport.base;

import cc.runa.rsupport.frame.BasePresenter;
import cc.runa.rsupport.frame.BaseView;

/**
 * 避免数据重复加载的 Fragment (配合 ViewPager 使用)
 *
 * @author JokerCats on 2020.08.04
 */
public abstract class BaseLazyFragment<T extends BasePresenter> extends BaseRxFragment<T> implements BaseView {

    // 是否第一次加载
    protected boolean isViewInitFinished = true;

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // 业务只再创建时刷新一次，因此屏蔽重置状态
        isViewInitFinished = true;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (isViewInitFinished) {
            getLazyData();// 将数据加载逻辑放到onResume()方法中
            isViewInitFinished = false;
        }
    }

    /**
     * this moment the fragment is visible to user, so request data
     */
    public abstract void getLazyData();

}
