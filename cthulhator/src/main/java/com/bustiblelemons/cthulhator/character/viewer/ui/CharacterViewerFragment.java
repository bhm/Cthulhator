package com.bustiblelemons.cthulhator.character.viewer.ui;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.bustiblelemons.cthulhator.R;
import com.bustiblelemons.cthulhator.character.persistance.CharacterWrappper;
import com.bustiblelemons.cthulhator.character.portrait.model.Portrait;
import com.bustiblelemons.cthulhator.character.viewer.CharacterViewerCard;
import com.bustiblelemons.cthulhator.character.viewer.SeeThrough;
import com.bustiblelemons.cthulhator.character.viewer.logic.CharacterViewerAdapter;
import com.bustiblelemons.cthulhator.character.viewer.logic.OnExpandCharacterViewer;
import com.bustiblelemons.cthulhator.fragments.AbsFragmentWithParcelable;
import com.bustiblelemons.views.loadingimage.RemoteImage;

import at.markushi.ui.CircleButton;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import butterknife.Optional;

/**
 * Created by hiv on 17.11.14.
 */
public class CharacterViewerFragment extends AbsFragmentWithParcelable<CharacterWrappper> {

    private static final int DEFAULT_SCREEN_PERCENTAGE = 75;
    @InjectView(R.id.recycler)
    RecyclerView mRecyclerView;

    @InjectView(android.R.id.icon)
    RemoteImage mImage;

    private Animation                  mSlideInAnimation;
    private Animation                  mSlideOutAnimation;
    private CharacterWrappper          mCharacterWrappper;
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
    private int                    mSeeThroughSize;

    public static CharacterViewerFragment newInstance(CharacterWrappper savedCharacter) {
        CharacterViewerFragment r = new CharacterViewerFragment();
        r.setNewInstanceArgument(savedCharacter);
        return r;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mSeeThroughSize = getSeeThroughHeight(activity);
        if (mSlideInAnimation == null) {
            mSlideInAnimation = AnimationUtils.loadAnimation(activity, R.anim.abc_slide_in_bottom);
            mSlideInAnimation.setAnimationListener(sAnimationListener);
        }
        if (mSlideOutAnimation == null) {
            mSlideOutAnimation = AnimationUtils.loadAnimation(activity, R.anim.abc_slide_out_bottom);
            mSlideOutAnimation.setAnimationListener(sAnimationListener);
        }
        setupCallbacks(activity);
    }

    private int getSeeThroughHeight(Activity activity) {
        Display display = activity.getWindowManager().getDefaultDisplay();
        if (display != null) {
            SeeThrough p = new SeeThrough().withHeightPercentage(DEFAULT_SCREEN_PERCENTAGE);
            display.getSize(p);
            return p.getCalculatedHeight();
        }
        return 0;
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

    @Override
    protected void onInstanceArgumentRead(CharacterWrappper instanceArgument) {
        if (instanceArgument != null) {
            mCharacterWrappper = instanceArgument;
            Portrait p = mCharacterWrappper.getMainPortrait();
            if (mImage != null &&  p != null) {
                mImage.loadFrom(p.getUrl());
            }
            expandRecyclerView(true);
        }
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

    @Optional
    @OnClick(R.id.expand)
    public void onExpandRecycler(CircleButton circleButton) {
        if (mRecyclerView != null) {
            boolean expand = mRecyclerView.getVisibility() != View.VISIBLE;
            expandRecyclerView(expand);
        }
    }

    private void expandRecyclerView(boolean expand) {
        if (expand) {
            mRecyclerView.setVisibility(View.VISIBLE);
            mRecyclerView.setAnimation(mSlideInAnimation);
            if (mAdapter == null) {
                mAdapter = new CharacterViewerAdapter(getActivity());
                mAdapter.withCharacterWrapper(mCharacterWrappper)
                        .withSeeThroughSize(mSeeThroughSize);
                mRecyclerView.setAdapter(mAdapter);
                mAdapter.refreshData(CharacterViewerCard.values());
            }
            mSlideInAnimation.start();
        } else {
            mRecyclerView.setAnimation(mSlideOutAnimation);
            mSlideOutAnimation.start();
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
