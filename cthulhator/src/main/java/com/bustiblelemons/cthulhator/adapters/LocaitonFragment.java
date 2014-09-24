package com.bustiblelemons.cthulhator.adapters;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bustiblelemons.api.random.names.randomuserdotme.model.Location;
import com.bustiblelemons.cthulhator.R;
import com.bustiblelemons.cthulhator.view.LocationWidget;
import com.bustiblelemons.fragments.AbsParcelableArgFragment;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by bhm on 02.08.14.
 */
public class LocaitonFragment extends AbsParcelableArgFragment<Location> {

    @InjectView(R.id.location)
    LocationWidget locationWidget;

    public static LocaitonFragment newInstance(Location location) {
        LocaitonFragment r = new LocaitonFragment();
        r.setNewInstanceArgument(location);
        return r;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_location, container, false);
        ButterKnife.inject(this, rootView);
        return rootView;
    }

    @Override
    protected void onInstanceArgumentRead(Location instanceArgument) {
        loadLocaction(instanceArgument);
    }

    private void loadLocaction(Location location) {
        if (locationWidget != null) {
            locationWidget.setLocation(location);
        }
    }
}
