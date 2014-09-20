package com.bustiblelemons.cthulhator.fragments;

import android.app.Activity;

import com.bustiblelemons.api.random.names.randomuserdotme.model.Name;
import com.bustiblelemons.api.random.names.randomuserdotme.model.User;
import com.bustiblelemons.cthulhator.R;
import com.bustiblelemons.cthulhator.view.LocationWidget;
import com.bustiblelemons.cthulhator.view.NameWidget;
import com.bustiblelemons.views.LoadingImage;
import com.manuelpeinado.fadingactionbar.FadingActionBarHelperBase;
import com.manuelpeinado.fadingactionbar.extras.actionbarcompat.FadingActionBarHelper;

import butterknife.InjectView;

/**
 * Created by bhm on 26.07.14.
 */
public class RandomUserFragment extends AbsArgFragment<User> {
    @InjectView(R.id.name)
    NameWidget     nameWidget;
    @InjectView(R.id.location)
    LocationWidget locationWidget;
    @InjectView(R.id.image_header)
    LoadingImage   pictureView;
    private FadingActionBarHelperBase mFadingHelper;

    public static RandomUserFragment newInstane(User user) {
        RandomUserFragment r = new RandomUserFragment();
        r.setNewInstanceArgument(user);
        return r;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mFadingHelper = new FadingActionBarHelper()
                .actionBarBackground(R.drawable.actionbar_brp)
                .headerLayout(R.layout.header_brp_header_background)
                .headerOverlayLayout(R.layout.fragment_random_user_header)
                .contentLayout(R.layout.fragment_random_user)
                .lightActionBar(false);
        mFadingHelper.initActionBar(activity);
        setActionBarCloseIcon();
    }

    @Override
    protected void onInstanceArgumentRead(User instanceArgument) {
        loadUserInfo(instanceArgument);
    }

    public void loadUserInfo(User user) {
        if (locationWidget != null) {
            locationWidget.setLocation(user.getLocation());
        }
        if (nameWidget != null) {
            Name name = user.getName();
            String fullName = name.getFirst() + " " + name.getLast();
            nameWidget.setName(fullName);
            nameWidget.setTitle(name.getTitle());
        }
        if (pictureView != null) {
            String picUrl = user.getPicture().getMedium();
            pictureView.loadFrom(picUrl);
        }
    }
}
