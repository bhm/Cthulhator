package com.bustiblelemons.cthulhator.activities;

import android.os.Bundle;
import android.support.v4.view.ViewPager;

import com.bustiblelemons.activities.AbsActionBarActivity;
import com.bustiblelemons.cthulhator.R;
import com.bustiblelemons.cthulhator.adapters.BRPCharacterPagerAdapter;
import com.bustiblelemons.logging.Logger;

/**
 * Created by bhm on 21.07.14.
 */
public class TestActivity extends AbsActionBarActivity implements ViewPager.OnPageChangeListener {

    private static final Logger log = new Logger(TestActivity.class);
    private ViewPager                pager;
    private BRPCharacterPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.statistic_pager_view);
        pager = (ViewPager) findViewById(android.R.id.custom);
        adapter = new BRPCharacterPagerAdapter(getSupportFragmentManager());
        pager.setAdapter(adapter);
        pager.setOnPageChangeListener(this);
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        log.d("position %s", position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
