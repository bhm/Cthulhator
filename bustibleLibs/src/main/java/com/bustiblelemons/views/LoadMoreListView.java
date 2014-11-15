package com.bustiblelemons.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.bustiblelemons.bustiblelibs.R;
import com.nostra13.universalimageloader.core.ImageLoader;

public class LoadMoreListView extends ListView implements AbsListView.OnScrollListener {

    private static final String TAG = "LoadMoreListView";

    /**
     * Listener that will receive notifications every time the list scrolls.
     */
    private OnScrollListener mOnScrollListener;
    private LayoutInflater   mInflater;

    // footer view
    private RelativeLayout mFooterView;
    // private TextView mLabLoadMore;
    private ProgressBar    mProgressBarLoadMore;

    // Listener to process load more items when user reaches the end of the list
    private OnLoadMoreListener mOnLoadMoreListener;
    // To know if the list is loading more items
    private boolean mIsLoadingMore = false;
    private int mCurrentScrollState;
    private ImageLoader imageLoader   = null;
    private boolean     pauseOnScroll = false;
    private boolean     pauseOnFling  = false;
    private boolean     showLoader    = true;

    public LoadMoreListView(Context context) {
        super(context);
        init(context);
    }

    public LoadMoreListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public LoadMoreListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    public void setShowLoader(boolean showLoader) {
        this.showLoader = showLoader;
    }

    public boolean isPauseOnFling() {
        return pauseOnFling;
    }

    public void setPauseOnFling(boolean pauseOnFling) {
        this.pauseOnFling = pauseOnFling;
    }

    public boolean isPauseOnScroll() {
        return pauseOnScroll;
    }

    public void setPauseOnScroll(boolean pauseOnScroll) {
        this.pauseOnScroll = pauseOnScroll;
    }

    private void init(Context context) {
        try {
            if (pauseOnFling) {
                imageLoader = ImageLoader.getInstance();
            }
        } catch (IllegalArgumentException e) {

        }

        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (!isInEditMode()) {
            // footer
            mFooterView = (RelativeLayout) mInflater.inflate(R.layout.load_more_footer, this, false);
        /*
         * mLabLoadMore = (TextView) mFooterView
		 * .findViewById(R.id.load_more_lab_view);
		 */
            mProgressBarLoadMore = (ProgressBar) mFooterView.findViewById(android.R.id.progress);
            addFooterView(mFooterView, null, false);
        }

        super.setOnScrollListener(this);
    }

    @Override
    public void setAdapter(ListAdapter adapter) {
        super.setAdapter(adapter);
    }

    /**
     * Set the listener that will receive notifications every time the list
     * scrolls.
     *
     * @param l The scroll listener.
     */
    @Override
    public void setOnScrollListener(AbsListView.OnScrollListener l) {
        mOnScrollListener = l;
    }

    /**
     * Register a callback to be invoked when this list reaches the end (last
     * item be visible)
     *
     * @param onLoadMoreListener The callback to run.
     */

    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        mOnLoadMoreListener = onLoadMoreListener;
    }

    public void onScroll(AbsListView view, int firstVisibleItem,
                         int visibleItemCount, int totalItemCount) {

        if (mOnScrollListener != null) {
            mOnScrollListener.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount);
        }

        if (mOnLoadMoreListener != null) {

            if (visibleItemCount == totalItemCount) {
                if (showLoader) {
                    mProgressBarLoadMore.setVisibility(View.GONE);
                }
                return;
            }

            boolean loadMore = firstVisibleItem + visibleItemCount >= totalItemCount;

            if (!mIsLoadingMore && loadMore && mCurrentScrollState != SCROLL_STATE_IDLE) {
                if (showLoader) {
                    mProgressBarLoadMore.setVisibility(View.VISIBLE);
                }
                mIsLoadingMore = true;
                onLoadMore();
            }
        }
    }

    public void onScrollStateChanged(AbsListView view, int scrollState) {

        //bug fix: listview was not clickable after scroll
        switch (scrollState) {
        case OnScrollListener.SCROLL_STATE_IDLE:
            if ((pauseOnScroll || pauseOnFling) && imageLoader != null) {
                imageLoader.resume();
            }
//            view.invalidateViews();
            break;
        case OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:
            if ((pauseOnFling || pauseOnScroll) && imageLoader != null) {
                imageLoader.pause();
            }
            break;
        case OnScrollListener.SCROLL_STATE_FLING:
            if ((pauseOnFling || pauseOnScroll) && imageLoader != null) {
                imageLoader.pause();
            }
            break;
        }

        mCurrentScrollState = scrollState;

        if (mOnScrollListener != null) {
            mOnScrollListener.onScrollStateChanged(view, scrollState);
        }

    }

    public void onLoadMore() {
        if (mOnLoadMoreListener != null) {
            mOnLoadMoreListener.onLoadMore();
        }
    }

    /**
     * Notify the loading more operation has finished
     */
    public void onLoadMoreComplete() {
        mIsLoadingMore = false;
        if (showLoader) {
            mProgressBarLoadMore.setVisibility(View.GONE);
        }
    }

    /**
     * Interface definition for a callback to be invoked when list reaches the
     * last item (the user load more items in the list)
     */
    public interface OnLoadMoreListener {
        /**
         * Called when the list reaches the last item (the last item is visible
         * to the user)
         */
        public void onLoadMore();
    }

}