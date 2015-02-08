package com.bustiblelemons.cthulhator.character.viewer.logic;

/**
 * Created by hiv on 08.02.15.
 */
public class HeightSizeRelay implements HeightSizeListener {

    private HeightSizeListener mHeightSizeListener;

    public HeightSizeRelay(HeightSizeListener heightSizeListener) {
        mHeightSizeListener = heightSizeListener;
    }

    @Override
    public void onHeightSizeReported(Object reporter, int height) {
        if (mHeightSizeListener != null) {
            mHeightSizeListener.onHeightSizeReported(reporter, height);
        }
    }
}
