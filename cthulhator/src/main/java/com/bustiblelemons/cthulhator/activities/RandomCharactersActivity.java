package com.bustiblelemons.cthulhator.activities;

import android.os.Bundle;

import com.bustiblelemons.activities.BaseActionBarActivity;
import com.bustiblelemons.cthulhator.R;
import com.bustiblelemons.cthulhator.adapters.RandomUserDotMePagerAdapter;
import com.bustiblelemons.views.LoadMoreViewPager;

/**
 * Created by bhm on 25.07.14.
 */
public class RandomCharactersActivity extends BaseActionBarActivity {

    private LoadMoreViewPager           pager;
    private RandomUserDotMePagerAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_random_characters);
        pager = (LoadMoreViewPager) findViewById(R.id.pager);
        pagerAdapter = new RandomUserDotMePagerAdapter(getSupportFragmentManager());
        pager.setAdapter(pagerAdapter);
    }
}
