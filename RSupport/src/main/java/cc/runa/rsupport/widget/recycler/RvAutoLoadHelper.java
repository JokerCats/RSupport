package cc.runa.rsupport.widget.recycler;

import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

/**
 * 监听 RecyclerView 滑动到底部时自动加载数据
 *
 * @author JokerCats on 2020.08.04
 */
public class RvAutoLoadHelper {

    private boolean enable = true;
    private RecyclerView mRecyclerView;
    private LoadMoreListener mListener;

    public RvAutoLoadHelper(RecyclerView mRecyclerView) {
        this.mRecyclerView = mRecyclerView;
    }

    public void setListener(LoadMoreListener listener) {
        this.mListener = listener;
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NotNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (newState == RecyclerView.SCROLL_STATE_IDLE && isBottom(mRecyclerView)) {
                    if (enable && mListener != null) {
                        mListener.onLoadMore();
                    }
                }
            }
        });
    }

    public void setLoadMoreEnable(boolean enable) {
        this.enable = enable;
    }

    private boolean isBottom(RecyclerView recyclerView) {
        if (recyclerView == null)
            return false;

        return recyclerView.computeVerticalScrollExtent() + recyclerView.computeVerticalScrollOffset() >= recyclerView.computeVerticalScrollRange();
    }

    public interface LoadMoreListener {

        void onLoadMore();
    }

}
