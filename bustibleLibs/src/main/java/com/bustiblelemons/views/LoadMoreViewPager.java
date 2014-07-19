package com.bustiblelemons.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;

import com.bustiblelemons.bustiblelibs.R;

/**
 * Created by bhm on 19.07.14.
 */
public class LoadMoreViewPager extends ViewPager {

    public static final int LOAD_MORE_THRESHOLD = 1;

    private int loadMoreThreshold = LOAD_MORE_THRESHOLD;
    private LoadMore loadMoreListener;

    OnPageChangeListener mOnPageChangeListener = new OnPageChangeListener() {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            if (getAdapter() != null) {
                int count = getAdapter().getCount();
                int currentItem = getCurrentItem();
                if (currentItem <= (count - loadMoreThreshold)) {
                    if (loadMoreListener != null) {
                        loadMoreListener.onLoadMore(LoadMoreViewPager.this);
                    }
                }
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    public void setLoadMoreThreshold(int loadMoreThreshold) {
        this.loadMoreThreshold = loadMoreThreshold;
    }

    public LoadMoreViewPager(Context context) {
        super(context);
        setOnPageChangeListener(mOnPageChangeListener);
    }

    public LoadMoreViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
        setOnPageChangeListener(mOnPageChangeListener);
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

    public interface LoadMore {
        public void onLoadMore(ViewPager pager);
    }
}
