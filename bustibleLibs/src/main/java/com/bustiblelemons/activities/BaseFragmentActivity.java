package com.bustiblelemons.activities;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;

import com.bustiblelemons.BaseFragment;
import com.bustiblelemons.bustiblelibs.R;
import com.bustiblelemons.logging.Logger;

/**
 * Created by bhm on 19.07.14.
 */
public class BaseFragmentActivity extends ActionBarActivity
        implements BaseFragment.ActionBarInterface {

    protected static Logger log;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        log = new Logger(getClass());
    }

    @Override
    public boolean onSetActionBarToClosable() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setIcon(null);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayUseLogoEnabled(false);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_close);
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
