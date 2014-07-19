package com.bustiblelemons.activities;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.bustiblelemons.logging.Logger;

/**
 * Created by bhm on 19.07.14.
 */
public class BaseFragmentActivity extends FragmentActivity {

    protected static Logger log;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        log = new Logger(getClass());
    }

    protected boolean startActivity(Class<? extends Activity> activity) {
        try {
            Intent intent = new Intent(this, activity.getClass());
            startActivity(intent);
            return true;
        } catch (ActivityNotFoundException e) {
            log.d("could not found activity %s", activity);
            return false;
        }
    }
}
