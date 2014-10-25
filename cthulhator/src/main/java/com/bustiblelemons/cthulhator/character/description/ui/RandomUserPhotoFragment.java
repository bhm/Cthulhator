package com.bustiblelemons.cthulhator.character.description.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bustiblelemons.cthulhator.R;
import com.bustiblelemons.cthulhator.fragments.AbsArgFragment;
import com.bustiblelemons.model.OnlinePhotoUrl;
import com.bustiblelemons.views.loadingimage.RemoteImage;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by bhm on 02.08.14.
 */
public class RandomUserPhotoFragment extends AbsArgFragment<OnlinePhotoUrl> {

    @InjectView(android.R.id.icon)
    RemoteImage imageView;

    public static RandomUserPhotoFragment newInstance(OnlinePhotoUrl user) {
        RandomUserPhotoFragment r = new RandomUserPhotoFragment();
        r.setNewInstanceArgument(user);
        return r;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_random_photo, container, false);
        ButterKnife.inject(this, rootView);
        return rootView;
    }

    @Override
    protected void onInstanceArgumentRead(OnlinePhotoUrl instanceArgument) {
        loadUrl(instanceArgument.getUrl());
    }

    public void loadUrl(String url) {
        log.d("url %s", url);
        imageView.loadFrom(url);
    }
}
