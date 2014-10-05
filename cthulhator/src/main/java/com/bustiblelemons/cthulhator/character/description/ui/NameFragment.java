package com.bustiblelemons.cthulhator.character.description.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bustiblelemons.cthulhator.R;
import com.bustiblelemons.cthulhator.view.NameWidget;
import com.bustiblelemons.fragments.AbsParcelableArgFragment;
import com.bustiblelemons.randomuserdotme.model.Name;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by bhm on 02.08.14.
 */
public class NameFragment extends AbsParcelableArgFragment<Name> {
    @InjectView(R.id.name)
    NameWidget nameWidget;

    public static NameFragment newInstance(Name name) {
        NameFragment r = new NameFragment();
        r.setNewInstanceArgument(name);
        return r;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_name, container, false);
        ButterKnife.inject(this, rootView);
        readInstanceArgument(savedInstanceState);
        return rootView;
    }

    @Override
    protected void onInstanceArgumentRead(Name instanceArgument) {
        loadName();
    }

    public void loadName() {
        if (nameWidget != null) {
            Name name = getInstanceArgument();
            String fullName = name.getFirst() + " " + name.getLast();
            nameWidget.setName(fullName);
            nameWidget.setTitle(name.getTitle());
        }
    }

}
