package com.bustiblelemons;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.bustiblelemons.logging.Logger;

/**
 * Created by bhm on 18.07.14.
 */
public class BaseFragment extends Fragment {

    protected static Logger log;

    private Context context;

    public Context getContext() {
        return context;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        log = new Logger(getClass());
        context = activity;
    }

    public boolean hasArguments() {
        return getArguments() != null;
    }

    public boolean hasArgument(String key) {
        return hasArguments() ? getArguments().containsKey(key) : false;
    }

    public static BaseFragment newInstance() {
        BaseFragment r = new BaseFragment();
        Bundle args = new Bundle();
        r.setArguments(args);
        return r;
    }
}
