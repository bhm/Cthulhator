package com.bustiblelemons.cthulhator.character.creation.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bustiblelemons.cthulhator.R;
import com.bustiblelemons.cthulhator.character.persistance.SavedCharacter;
import com.bustiblelemons.fragments.AbsParcelableArgFragment;
import com.bustiblelemons.views.loadingimage.RemoteImage;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by bhm on 21.09.14.
 */
public class PreviewCardFragment extends AbsParcelableArgFragment<SavedCharacter> {

    @InjectView(R.id.icon)
    RemoteImage imageView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_preview_card, container, false);
        ButterKnife.inject(this, rootView);
        return rootView;
    }

    @Override
    protected void onInstanceArgumentRead(SavedCharacter instanceArgument) {

    }
}
