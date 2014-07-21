package com.bustiblelemons.cthulhator.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;

import com.bustiblelemons.adapters.ValuePagerAdapter;
import com.bustiblelemons.cthulhator.R;
import com.bustiblelemons.logging.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bhm on 21.07.14.
 */
public class TestActivity extends FragmentActivity implements ViewPager.OnPageChangeListener {

    private ViewPager         pager;
    private ValuePagerAdapter adapter;
    private static final Logger log = new Logger(TestActivity.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.statistic_pager_view);
        pager = (ViewPager) findViewById(android.R.id.custom);
        adapter = new ValuePagerAdapter(this);
        List<String> data = getDataList();
        adapter.addItems(data);
        pager.setAdapter(adapter);
        pager.setOnPageChangeListener(this);
    }

    public List<String> getDataList() {
        List<String> r = new ArrayList<String>();
        for (int i = 0; i < 100; i++) {
            r.add(i + "");
        }
        return r;
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
