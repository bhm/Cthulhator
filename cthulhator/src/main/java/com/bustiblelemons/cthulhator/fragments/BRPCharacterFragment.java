package com.bustiblelemons.cthulhator.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bustiblelemons.BaseFragment;
import com.bustiblelemons.cthulhator.R;
import com.bustiblelemons.cthulhator.fragments.main.BRPCard;
import com.bustiblelemons.cthulhator.model.BRPSKills;
import com.bustiblelemons.cthulhator.model.brp.BRPCharacter;

/**
 * Created by bhm on 20.07.14.
 */
public class BRPCharacterFragment extends BaseFragment {

    private LinearLayout cardsContainer;
    private BRPCharacter character;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_brp_statistics, container, false);
        cardsContainer = (LinearLayout) rootView.findViewById(R.id.cards_container);
        if (cardsContainer != null) {
            inflateCards(cardsContainer, inflater);
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
        mythosValue.setText(character.getSkillValue(BRPSKills.mythos));
        TextView sanityValue = (TextView) view.findViewById(R.id.sanity_value);
        sanityValue.setText(character.getCurrentSanity());
    }
}
