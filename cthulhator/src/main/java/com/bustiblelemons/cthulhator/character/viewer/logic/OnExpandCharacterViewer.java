package com.bustiblelemons.cthulhator.character.viewer.logic;

/**
 * Created by hiv on 17.11.14.
 */
public interface OnExpandCharacterViewer {
    void onExpandCharacterViewer();

    void onCollapseCharacterViewer();

    void onFinishExpandAnimation();

    void onFinishCollapseAnimation();
}
