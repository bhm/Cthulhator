package com.bustiblelemons;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.bustiblelemons.google.apis.search.params.GoogleImageSearch;
import com.bustiblelemons.logging.Logger;

import butterknife.ButterKnife;

/**
 * Created by bhm on 18.07.14.
 */
public abstract class BaseFragment extends Fragment {

    protected static Logger log;

    private Context            context;
    private ActionBarInterface actionbarInterface;

    public Context getContext() {
        return context;
    }


    public interface ActionBarInterface {
        boolean onSetActionBarToClosable();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        log = new Logger(getClass());
        context = activity;
        if (activity instanceof ActionBarInterface) {
            actionbarInterface = (ActionBarInterface) activity;
        }
    }

    public boolean hasArguments() {
        return getArguments() != null;
    }

    public boolean hasArgument(String key) {
        return hasArguments() ? getArguments().containsKey(key) : false;
    }

    protected void setActionBarCloseIcon() {
        if (actionbarInterface != null) {
            actionbarInterface.onSetActionBarToClosable();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }
}
