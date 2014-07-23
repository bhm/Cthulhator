package com.bustiblelemons.cthulhator.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;

import com.bustiblelemons.activities.BaseActionBarActivity;
import com.bustiblelemons.cthulhator.R;
import com.bustiblelemons.cthulhator.adapters.PortraitsPagerAdapter;
import com.bustiblelemons.cthulhator.async.QueryGImagesAsyn;
import com.bustiblelemons.cthulhator.fragments.PortraitsSettingsFragment;
import com.bustiblelemons.google.apis.model.GoogleImageObject;
import com.bustiblelemons.google.apis.search.params.GoogleImageSearch;
import com.bustiblelemons.google.apis.search.params.ImageSearch;
import com.bustiblelemons.logging.Logger;
import com.bustiblelemons.views.LoadMoreViewPager;

import java.util.List;

/**
 * Created by bhm on 18.07.14.
 */
public class PortraitsActivity extends BaseActionBarActivity implements
                                                        LoadMoreViewPager.LoadMore,
                                                        QueryGImagesAsyn.ReceiveGoogleImages {

    private LoadMoreViewPager pager;

    private PortraitsPagerAdapter pagerAdapter;
    private static final Logger log = new Logger(PortraitsActivity.class);
    private GoogleImageSearch.Options searchOptions;
    private ImageSearch               imageSearch;
    private PortraitsSettingsFragment settingsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_portraits);
        pager = (LoadMoreViewPager) findViewById(R.id.pager);
        pagerAdapter = new PortraitsPagerAdapter(getSupportFragmentManager());
        pager.setAdapter(pagerAdapter);
        pager.setLoadMoreListener(this);
        searchOptions = new GoogleImageSearch.Options();
        searchOptions.setQuery("1920+male+portrait");
        this.imageSearch =searchOptions.build();
        queryForImages(imageSearch);
        attachSettings();
        onSetActionBarToClosable();
    }

    private void attachSettings() {
        if (settingsFragment == null) {
            settingsFragment = PortraitsSettingsFragment.newInstance(searchOptions);
        }
        addFragment(R.id.bottom_card, settingsFragment);

    }

    private int addFragment(int containerId, Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(containerId, fragment);
        return transaction.commit();
    }

    private void queryForImages(ImageSearch search) {
        QueryGImagesAsyn queryGImagesAsyn = new QueryGImagesAsyn(this, this);
        queryGImagesAsyn.executeCrossPlatform(search);
    }

    @Override
    public void onLoadMore(ViewPager pager) {
        queryForImages(imageSearch);
        log.d("onLoadMore %s", pager.getCurrentItem());
    }

    @Override
    public boolean onGoogleImagesReceived(List<GoogleImageObject> results) {
        pagerAdapter.addData(results);
        return false;
    }
}