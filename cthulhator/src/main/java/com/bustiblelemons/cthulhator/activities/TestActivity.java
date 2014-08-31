package com.bustiblelemons.cthulhator.activities;

import android.os.Bundle;
import android.support.v4.view.ViewPager;

import com.bustiblelemons.activities.BaseActionBarActivity;
import com.bustiblelemons.cthulhator.R;
import com.bustiblelemons.cthulhator.adapters.BRPCharacterPagerAdapter;
import com.bustiblelemons.cthulhator.model.CoCFiveCharacter;
import com.bustiblelemons.cthulhator.model.brp.AbsBRPCharacter;
import com.bustiblelemons.logging.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bhm on 21.07.14.
 */
public class TestActivity extends BaseActionBarActivity implements ViewPager.OnPageChangeListener {

    private ViewPager                pager;
    private BRPCharacterPagerAdapter adapter;
    private static final Logger log = new Logger(TestActivity.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.statistic_pager_view);
        pager = (ViewPager) findViewById(android.R.id.custom);
        adapter = new BRPCharacterPagerAdapter(getSupportFragmentManager());
        List<AbsBRPCharacter> data = getDataList();
        adapter.addData(data);
        pager.setAdapter(adapter);
        pager.setOnPageChangeListener(this);
    }

    public List<AbsBRPCharacter> getDataList() {
        List<AbsBRPCharacter> r = new ArrayList<AbsBRPCharacter>();
        for (int i = 0; i < 100; i++) {
            r.add(new CoCFiveCharacter());
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
