package com.bustiblelemons.cthulhator.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bustiblelemons.cthulhator.R;
import com.bustiblelemons.cthulhator.fragments.main.BRPCard;
import com.bustiblelemons.fragments.AbsFragment;
import com.manuelpeinado.fadingactionbar.FadingActionBarHelperBase;
import com.manuelpeinado.fadingactionbar.extras.actionbarcompat.FadingActionBarHelper;

/**
 * Created by bhm on 20.07.14.
 */
public class BRPCharacterFragment extends AbsFragment implements View.OnClickListener {

    private LinearLayout              cardsContainer;
    private FadingActionBarHelperBase mFadingHelper;
    private ImageButton               addPicture;
    private BRPCharacterListener      characterListener;

    public static BRPCharacterFragment newInstance(Bundle extras) {
        BRPCharacterFragment r = new BRPCharacterFragment();
        extras = extras == null ? new Bundle() : extras;
        r.setArguments(extras);
        return r;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mFadingHelper = new FadingActionBarHelper()
                .actionBarBackground(R.drawable.actionbar_brp)
                .headerLayout(R.layout.header_brp_header_background)
                .headerOverlayLayout(R.layout.fragment_brp_header_overlay)
                .contentLayout(R.layout.fragment_brp_statistics)
                .lightActionBar(false);
        mFadingHelper.initActionBar(activity);
        setActionBarCloseIcon();
        setCharacterListener(activity);
    }

    private void setCharacterListener(Activity activity) {
        if (activity instanceof BRPCharacterListener) {
            characterListener = (BRPCharacterListener) activity;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = mFadingHelper.createView(getContext());
        addPicture = (ImageButton) rootView.findViewById(R.id.add_picture);
        if (addPicture != null) {
            addPicture.setOnClickListener(this);
        }
        cardsContainer = (LinearLayout) rootView.findViewById(R.id.cards_container);
        if (cardsContainer != null) {
//            inflateCards(cardsContainer, inflater);
        }
        return rootView;
    }

    private int inflateCards(LinearLayout container, LayoutInflater inflater) {
        int r = 0;
        for (BRPCard card : BRPCard.values()) {
            View view = getCard(container, inflater, card);
            switch (card) {
                case MYTHOS:
                    fillMythosCard(view);
                    break;
                case MIND:
                    fillMindCard(view);
                    break;

            }
            container.addView(view);

        }
        return r;
    }

    private View getCard(LinearLayout container, LayoutInflater inflater, BRPCard card) {
        View view = inflater.inflate(card.getLayoutId(), container, false);
        return view;
    }

    private void fillMindCard(View view) {

    }

    private void fillMythosCard(View view) {
        TextView mythosValue = (TextView) view.findViewById(R.id.mythos_value);
        TextView sanityValue = (TextView) view.findViewById(R.id.sanity_value);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.add_picture:
                if (characterListener != null) {
                    characterListener.onPickPicture(-1);
                }
                break;
        }
    }

    public interface BRPCharacterListener {
        boolean onPickPicture(int characterId);
    }
}
