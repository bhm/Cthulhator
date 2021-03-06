package com.bustiblelemons.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;

import com.bustiblelemons.bustiblelibs.R;

/**
 * Created by bhm on 19.07.14.
 */
public class LoadMoreViewPager extends ViewPager {

    public static final int LOAD_MORE_THRESHOLD = 1;

    private int loadMoreThreshold = LOAD_MORE_THRESHOLD;
    private LoadMore loadMoreListener;
    private OnPageChangeListener mCachedPageListener;
    OnPageChangeListener mOnPageChangeListener = new OnPageChangeListener() {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            if (mCachedPageListener != null) {
                mCachedPageListener.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }
        }

        @Override
        public void onPageSelected(int position) {
            if (mCachedPageListener != null) {
                mCachedPageListener.onPageSelected(position);
            }
            if (getAdapter() != null) {
                int count = getAdapter().getCount();
                int currentItem = getCurrentItem();
                if (currentItem >= (count - loadMoreThreshold)) {
                    if (loadMoreListener != null) {
                        loadMoreListener.onLoadMore(LoadMoreViewPager.this);
                    }
                }
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            if (mCachedPageListener != null) {
                mCachedPageListener.onPageScrollStateChanged(state);
            }
        }
    };

    public LoadMoreViewPager(Context context) {
        super(context);
        setOnPageChangeListener(mOnPageChangeListener);
    }

    public LoadMoreViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
        setOnPageChangeListener(mOnPageChangeListener);
    }

    @Override
    public void setOnPageChangeListener(OnPageChangeListener listener) {
        if (!listener.equals(mOnPageChangeListener)) {
            this.mCachedPageListener = listener;
        } else {
            super.setOnPageChangeListener(listener);
        }
    }

    public void setLoadMoreThreshold(int loadMoreThreshold) {
        this.loadMoreThreshold = loadMoreThreshold;
    }

    private void init(Context context, AttributeSet attrs) {
        if (attrs != null) {
            TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.LoadMoreViewPager);
            loadMoreThreshold = array.getInteger(R.styleable.LoadMoreViewPager_threshold,
                    LOAD_MORE_THRESHOLD);
        }
    }


    public void setLoadMoreListener(LoadMore loadMoreListener) {
        this.loadMoreListener = loadMoreListener;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (getLayoutParams().height > 0) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        } else {
            int height = 0;
            for (int i = 0; i < getChildCount(); i++) {
                View child = getChildAt(i);
                child.measure(widthMeasureSpec,
                        MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
                int h = child.getMeasuredHeight();
                if (h > height) { height = h; }
            }
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY);
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }

    public interface LoadMore {
        public void onLoadMore(ViewPager pager);
    }
}
