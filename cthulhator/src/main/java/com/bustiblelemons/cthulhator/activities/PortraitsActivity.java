package com.bustiblelemons.cthulhator.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;

import com.bustiblelemons.cthulhator.R;
import com.bustiblelemons.cthulhator.adapters.PortraitsPagerAdapter;

/**
 * Created by bhm on 18.07.14.
 */
public class PortraitsActivity extends FragmentActivity implements ViewPager.OnPageChangeListener {

    private ViewPager pager;

    private PortraitsPagerAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_portraits);
        pager = (ViewPager) findViewById(R.id.pager);
        pagerAdapter = new PortraitsPagerAdapter(getSupportFragmentManager());
        pager.setAdapter(pagerAdapter);
        pager.setOnPageChangeListener(this);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
