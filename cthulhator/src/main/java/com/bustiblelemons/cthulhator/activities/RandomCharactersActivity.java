package com.bustiblelemons.cthulhator.activities;

import android.os.Bundle;
import android.support.v4.view.ViewPager;

import com.bustiblelemons.activities.BaseActionBarActivity;
import com.bustiblelemons.api.random.names.randomuserdotme.RandomUserDotMeQuery;
import com.bustiblelemons.api.random.names.randomuserdotme.asyn.OnRandomUsersRetreived;
import com.bustiblelemons.api.random.names.randomuserdotme.asyn.RandomUserDotMeAsyn;
import com.bustiblelemons.api.random.names.randomuserdotme.model.User;
import com.bustiblelemons.cthulhator.R;
import com.bustiblelemons.cthulhator.adapters.RandomUserDotMePagerAdapter;
import com.bustiblelemons.views.LoadMoreViewPager;

import java.util.List;

/**
 * Created by bhm on 25.07.14.
 */
public class RandomCharactersActivity extends BaseActionBarActivity
        implements LoadMoreViewPager.LoadMore, OnRandomUsersRetreived {

    private LoadMoreViewPager            pager;
    private RandomUserDotMePagerAdapter  pagerAdapter;
    private RandomUserDotMeQuery         query;
    private RandomUserDotMeQuery.Options queryOptions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_random_characters);
        pager = (LoadMoreViewPager) findViewById(R.id.pager);
        pagerAdapter = new RandomUserDotMePagerAdapter(getSupportFragmentManager());
        pager.setLoadMoreListener(this);
        pager.setAdapter(pagerAdapter);
        queryOptions = new RandomUserDotMeQuery.Options();
        query = queryOptions.build();
        onLoadMore(pager);
    }

    @Override
    public void onLoadMore(ViewPager pager) {
        RandomUserDotMeAsyn async = new RandomUserDotMeAsyn(this, this);
        async.executeCrossPlatform(query);
    }

    @Override
    public int onRandomUsersRetreived(RandomUserDotMeQuery query, List<User> users) {
        pagerAdapter.addData(users);
        return 0;
    }
}
