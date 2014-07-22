package com.bustiblelemons.views;

/**
 * Created by bhm on 22.07.14.
 */
public interface PropertyViewListener<T> {
    void onSkillValueClick(T view);

    void onSkillTitleClick(T view);

    boolean onIncreaseClicked(T view);

    boolean onDecreaseClicked(T view);
}
