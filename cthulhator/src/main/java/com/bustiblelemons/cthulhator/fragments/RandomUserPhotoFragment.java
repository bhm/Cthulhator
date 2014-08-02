package com.bustiblelemons.cthulhator.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bustiblelemons.api.random.names.randomuserdotme.model.User;
import com.bustiblelemons.cthulhator.R;
import com.bustiblelemons.views.LoadingImage;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by bhm on 02.08.14.
 */
public class RandomUserPhotoFragment extends AbsArgFragment<User> {

    @InjectView(android.R.id.icon)
    LoadingImage imageView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_random_photo, container, false);
        ButterKnife.inject(this, rootView);
        return rootView;
    }

    @Override
    protected void onInstanceArgumentRead(User instanceArgument) {
        loadUrl(instanceArgument.getPicture());
    }

    public void loadUrl(String url) {
        log.d("url %s", url);
        imageView.loadFrom(url);
    }

    public static RandomUserPhotoFragment newInstance(User user) {
        RandomUserPhotoFragment r = new RandomUserPhotoFragment();
        r.setNewInstanceArgument(user);
        return r;
    }
}
