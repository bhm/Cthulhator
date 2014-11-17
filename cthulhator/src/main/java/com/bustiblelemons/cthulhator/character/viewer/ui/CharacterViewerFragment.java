package com.bustiblelemons.cthulhator.character.viewer.ui;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.bustiblelemons.cthulhator.R;
import com.bustiblelemons.cthulhator.character.characterslist.logic.SavedCharacterTransformer;
import com.bustiblelemons.cthulhator.character.characterslist.model.SavedCharacter;
import com.bustiblelemons.cthulhator.character.viewer.logic.OnExpandCharacterViewer;
import com.bustiblelemons.cthulhator.fragments.AbsArgFragment;
import com.bustiblelemons.cthulhator.view.charactercard.CharacterInfo;

import at.markushi.ui.CircleButton;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import butterknife.Optional;

/**
 * Created by hiv on 17.11.14.
 */
public class CharacterViewerFragment extends AbsArgFragment<SavedCharacter> {
    private static Animation sSlideInAnimation;
    private static Animation sSlideOutAnimation;
    @InjectView(R.id.recycler)
    RecyclerView mRecyclerView;
    @Optional
    @InjectView(R.id.main_info)
    TextView     mMainInfoView;
    @Optional
    @InjectView(R.id.short_info)
    TextView     mShortInfoView;
    @Optional
    @InjectView(R.id.extra_info)
    TextView     mExtraInfoView;
    private SavedCharacter             mSavedCharacter;
    private RecyclerView.LayoutManager mManager;
    private RecyclerView.ItemAnimator  mAnimator;
    private OnExpandCharacterViewer    mExpandCallback;
    private final Animation.AnimationListener sAnimationListener = new Animation.AnimationListener() {
        @Override
        public void onAnimationStart(Animation animation) {

        }

        @Override
        public void onAnimationEnd(Animation animation) {
            if (animation != null && animation.equals(sSlideOutAnimation)) {
                if (mExpandCallback != null) {
                    mExpandCallback.onFinishCollapseAnimation();
                }
            } else if (animation != null && animation.equals(sSlideInAnimation)) {
                if (mExpandCallback != null) {
                    mExpandCallback.onFinishExpandAnimation();
                }
            }
        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    };

    public static CharacterViewerFragment newInstance(SavedCharacter savedCharacter) {
        CharacterViewerFragment r = new CharacterViewerFragment();
        r.setNewInstanceArgument(savedCharacter);
        return r;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (sSlideInAnimation == null) {
            sSlideInAnimation = AnimationUtils.loadAnimation(activity, R.anim.abc_slide_in_bottom);
            sSlideInAnimation.setAnimationListener(sAnimationListener);
        }
        if (sSlideOutAnimation == null) {
            sSlideOutAnimation = AnimationUtils.loadAnimation(activity, R.anim.abc_slide_out_bottom);
            sSlideOutAnimation.setAnimationListener(sAnimationListener);
        }
        if (mManager == null) {
            mManager = new LinearLayoutManager(activity);
        }

        if (activity instanceof OnExpandCharacterViewer) {
            mExpandCallback = (OnExpandCharacterViewer) activity;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_character_viewer, container, false);
        ButterKnife.inject(this, rootView);
        initRecycler();
        return rootView;
    }

    @Override
    protected void onInstanceArgumentRead(SavedCharacter instanceArgument) {
        if (instanceArgument != null) {
            mSavedCharacter = instanceArgument;
            CharacterInfo characterInfo = SavedCharacterTransformer.getInstance()
                    .withContext(getContext()).transform(mSavedCharacter);
            loadCharacterInfo(characterInfo);

        }
    }

    private void initRecycler() {
        if (mRecyclerView != null) {
            mRecyclerView.setLayoutManager(mManager);
            if (mAnimator == null) {
                mAnimator = new DefaultItemAnimator();
            }
            mRecyclerView.setItemAnimator(mAnimator);
        }
    }

    private void loadCharacterInfo(CharacterInfo provider) {
        if (provider != null) {
            setMainText(provider.getName());
            setShortText(provider.getMainInfo());
            setExtraText(provider.getExtraInfo());
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

    @Optional
    @OnClick(R.id.expand)
    public void onExpandRecycler(CircleButton circleButton) {
        if (mRecyclerView != null) {
            boolean expand = mRecyclerView.getVisibility() != View.VISIBLE;
            if (expand) {
                mRecyclerView.setVisibility(View.VISIBLE);
                mRecyclerView.setAnimation(sSlideInAnimation);
                sSlideInAnimation.start();
            } else {
                mRecyclerView.setAnimation(sSlideOutAnimation);
                sSlideOutAnimation.start();
                mRecyclerView.setVisibility(View.GONE);
            }
            if (mExpandCallback != null) {
                if (expand) {
                    mExpandCallback.onExpandCharacterViewer();
                } else {
                    mExpandCallback.onCollapseCharacterViewer();
                }
            }
        }
    }
}
