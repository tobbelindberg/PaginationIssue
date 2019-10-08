package com.paginationissue.paging;


import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

/**
 * Listener calling {@link OnLoadMoreListener#onLoadMore()} whenever the last view is visible. The
 * listener will continue to call onLoadMore until the client disables the listener.
 * The listener starts in a disabled state and clients are responsible for enabling the listener
 * using {@link #setEnabled(boolean)}.
 */
public class PaginationScrollListener extends RecyclerView.OnScrollListener {

    private final RecyclerView.LayoutManager mLayoutManager;
    private int mVisibleThreshold;
    private final OnLoadMoreListener mOnLoadMoreListener;
    private int mLastVisibleItem;
    private boolean mEnabled;

    public PaginationScrollListener(RecyclerView.LayoutManager layoutManager, int visibleThreshold,
                                    OnLoadMoreListener onLoadMoreListener) {
        mVisibleThreshold = visibleThreshold;
        mOnLoadMoreListener = onLoadMoreListener;
        mLayoutManager = layoutManager;

        if (mLayoutManager == null) {
            throw new IllegalArgumentException("LayoutManager cannot be null");
        }
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        if (dx == 0 && dy == 0) {
            return;
        }

        long totalItemCount = mLayoutManager.getItemCount();

        if (mLayoutManager instanceof LinearLayoutManager) {
            mLastVisibleItem = ((LinearLayoutManager) mLayoutManager).findLastVisibleItemPosition();
        }

        if (mLayoutManager instanceof StaggeredGridLayoutManager) {
            int[] columns = ((StaggeredGridLayoutManager) mLayoutManager)
                    .findLastVisibleItemPositions(null);
            mLastVisibleItem = columns[columns.length - 1];
        }

        if (totalItemCount <= (mLastVisibleItem + mVisibleThreshold)) {
            dispatchLoadMore();
        }
    }

    private void dispatchLoadMore() {
        if (mEnabled) {
            mOnLoadMoreListener.onLoadMore();
        }
    }

    public void setEnabled(boolean enabled) {
        mEnabled = enabled;
    }

    public interface OnLoadMoreListener {
        void onLoadMore();
    }
}
