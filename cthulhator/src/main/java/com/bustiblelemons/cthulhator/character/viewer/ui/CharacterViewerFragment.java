package com.bustiblelemons.cthulhator.character.viewer.ui;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.bustiblelemons.cthulhator.R;
import com.bustiblelemons.cthulhator.character.persistance.CharacterWrapper;
import com.bustiblelemons.cthulhator.character.portrait.model.Portrait;
import com.bustiblelemons.cthulhator.character.viewer.CharacterViewerCard;
import com.bustiblelemons.cthulhator.character.viewer.logic.CharacterViewerAdapter;
import com.bustiblelemons.cthulhator.character.viewer.logic.OnExpandCharacterViewer;
import com.bustiblelemons.cthulhator.character.viewer.logic.OnShowSkills;
import com.bustiblelemons.cthulhator.fragments.AbsFragmentWithArg;
import com.bustiblelemons.cthulhator.view.FabListener;
import com.bustiblelemons.views.loadingimage.RemoteImage;

import at.markushi.ui.CircleButton;
import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by hiv on 17.11.14.
 */
public class CharacterViewerFragment extends AbsFragmentWithArg<CharacterWrapper>
        implements FabListener<CircleButton> {

    private static final int DEFAULT_SCREEN_PERCENTAGE = 75;
    @InjectView(R.id.recycler)
    RecyclerView mRecyclerView;

    @InjectView(android.R.id.icon)
    RemoteImage mImage;

    private Animation                  mSlideInAnimation;
    private Animation                  mSlideOutAnimation;
    private CharacterWrapper           mCharacterWrapper;
    private RecyclerView.LayoutManager mManager;
    private RecyclerView.ItemAnimator  mAnimator;
    private OnExpandCharacterViewer    mExpandCallback;
    private final Animation.AnimationListener sAnimationListener = new Animation.AnimationListener() {
        @Override
        public void onAnimationStart(Animation animation) {

        }

        @Override
        public void onAnimationEnd(Animation animation) {
            if (animation != null && animation.equals(mSlideOutAnimation)) {
                if (mExpandCallback != null) {
                    mExpandCallback.onFinishCollapseAnimation();
                }
            } else if (animation != null && animation.equals(mSlideInAnimation)) {
                if (mExpandCallback != null) {
                    mExpandCallback.onFinishExpandAnimation();
                }
            }
        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    };

    private CharacterViewerAdapter mAdapter;
    private OnShowSkills mOnShowSkillsCallback;

    public static CharacterViewerFragment newInstance(CharacterWrapper savedCharacter) {
        CharacterViewerFragment r = new CharacterViewerFragment();
        r.setNewInstanceArgument(savedCharacter);
        return r;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (mSlideInAnimation == null) {
            mSlideInAnimation = AnimationUtils.loadAnimation(activity, R.anim.abc_slide_in_bottom);
            mSlideInAnimation.setAnimationListener(sAnimationListener);
        }
        if (mSlideOutAnimation == null) {
            mSlideOutAnimation = AnimationUtils.loadAnimation(activity, R.anim.abc_slide_out_bottom);
            mSlideOutAnimation.setAnimationListener(sAnimationListener);
        }
        if (activity instanceof OnShowSkills) {
            mOnShowSkillsCallback = (OnShowSkills) activity;            
        }
        setupCallbacks(activity);
    }


    private void setupCallbacks(Activity activity) {
        if (activity instanceof OnExpandCharacterViewer) {
            mExpandCallback = (OnExpandCharacterViewer) activity;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_character_viewer, container, false);
        ButterKnife.inject(this, rootView);
        initRecycler(inflater.getContext());
        return rootView;
    }

    private void initRecycler(Context context) {
        if (mRecyclerView != null) {
            if (mManager == null) {
                mManager = new LinearLayoutManager(context);
            }
            mRecyclerView.setLayoutManager(mManager);
            if (mAnimator == null) {
                mAnimator = new DefaultItemAnimator();
            }
            mRecyclerView.setItemAnimator(mAnimator);
        }
    }

    @Override
    protected void onInstanceArgumentRead(CharacterWrapper instanceArgument) {
        if (instanceArgument != null) {
            mCharacterWrapper = instanceArgument;
            Portrait p = mCharacterWrapper.getMainPortrait();
            if (mImage != null && p != null) {
                mImage.loadFrom(p.getUrl());
            }
            if (mAdapter == null) {
                mAdapter = new CharacterViewerAdapter();
                mAdapter.withCharacterWrapper(mCharacterWrapper)
                        .withFabListener(this)
                        .withOnShowSkillsCallback(mOnShowSkillsCallback);
                mRecyclerView.setAdapter(mAdapter);
            }
            mAdapter.refreshData(CharacterViewerCard.values());
        }
    }

    @Override
    public void onButtonClicked(CircleButton button) {
        if (mRecyclerView != null) {
            mRecyclerView.scrollToPosition(0);
        }
    }
}
