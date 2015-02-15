package com.bustiblelemons.cthulhator.character.viewer.logic;

import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.bustiblelemons.cthulhator.R;
import com.bustiblelemons.cthulhator.character.viewer.CharacterViewerCard;
import com.bustiblelemons.cthulhator.view.FabListener;
import com.bustiblelemons.cthulhator.view.charactercard.CharacterInfo;
import com.bustiblelemons.cthulhator.view.charactercard.CharacterInfoProvider;

import at.markushi.ui.CircleButton;
import butterknife.InjectView;
import butterknife.OnClick;
import butterknife.Optional;

/**
 * Created by hiv on 08.02.15.
 */
public class TitleCardHolder extends CharacterViewerCardHolder {

    private final View                  mRootView;
    @Optional
    @InjectView(R.id.main_info)
    TextView mMainInfoView;
    @Optional
    @InjectView(R.id.short_info)
    TextView mShortInfoView;
    @Optional
    @InjectView(R.id.extra_info)
    TextView mExtraInfoView;
    private       CharacterInfoProvider mCharacterInfoProvider;
    private HeightSizeListener        mHeightSizeListener;
    private FabListener<CircleButton> mFabListener;

    public TitleCardHolder(View view) {
        super(view);
        mRootView = view;
    }

    @Optional
    @OnClick(R.id.expand)
    public void onExpand(CircleButton button) {
        if (mFabListener != null) {
            mFabListener.onButtonClicked(button);
        }
    }

    public TitleCardHolder withHeightSizeListener(HeightSizeListener callback) {
        mHeightSizeListener = callback;
        return this;
    }

    public TitleCardHolder withCharacterInfoProivder(CharacterInfoProvider proivder) {
        mCharacterInfoProvider = proivder;
        return this;
    }

    @Override
    public void onBindData(CharacterViewerCard item) {
        if (mCharacterInfoProvider != null && mMainInfoView != null) {
            CharacterInfo info = mCharacterInfoProvider.onRetreiveCharacterInfo(mMainInfoView.getContext());
            loadCharacterInfo(info);
            if (mRootView != null) {
                mRootView.measure(0, 0);
                int height = mRootView.getMeasuredHeight();
                mHeightSizeListener.onHeightSizeReported(this, height);
            }
        }
    }

    private void loadCharacterInfo(CharacterInfo characterInfo) {
        if (characterInfo != null) {
            setMainText(characterInfo.getName());
            setShortText(characterInfo.getMainInfo());
            setExtraText(characterInfo.getExtraInfo());
        }
    }

    public void setMainText(String text) {
        if (mMainInfoView != null && !TextUtils.isEmpty(text)) {
            mMainInfoView.setText(text);
        }
    }

    private void setExtraText(String text) {
        if (mExtraInfoView != null && !TextUtils.isEmpty(text)) {
            mExtraInfoView.setText(text);
        }
    }

    private void setShortText(String text) {
        if (mShortInfoView != null && !TextUtils.isEmpty(text)) {
            mShortInfoView.setText(text);
        }
    }

    public TitleCardHolder withFabListener(FabListener<CircleButton> fabListener) {
        mFabListener = fabListener;
        return null;
    }
}
