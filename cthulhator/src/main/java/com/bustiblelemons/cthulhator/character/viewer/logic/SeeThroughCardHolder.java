package com.bustiblelemons.cthulhator.character.viewer.logic;

import android.content.Context;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;

import com.bustiblelemons.cthulhator.character.viewer.CharacterViewerCard;
import com.bustiblelemons.cthulhator.character.viewer.SeeThrough;
import com.bustiblelemons.recycler.AbsRecyclerHolder;

/**
 * Created by hiv on 08.02.15.
 */
public class SeeThroughCardHolder extends AbsRecyclerHolder<CharacterViewerCard> implements
                                                                                 HeightSizeListener {

    private final View mRootView;

    public SeeThroughCardHolder(View view) {
        super(view);
        mRootView = view;
    }

    @Override
    public void onBindData(CharacterViewerCard item) {

    }

    @Override
    public void onHeightSizeReported(Object reporter, int height) {
        if (mRootView != null) {
            int calulatedHeight = getCalculatedHeight(height);
            mRootView.setMinimumHeight(calulatedHeight);
        }
    }

    private int getCalculatedHeight(int height) {
        WindowManager manager = (WindowManager) mRootView.getContext().getSystemService(Context.WINDOW_SERVICE);
        Display display = manager.getDefaultDisplay();
        SeeThrough seeThrough= new SeeThrough().withHeightSubstract(height);
        display.getSize(seeThrough);
        return seeThrough.getCalculatedHeight();
    }
}
