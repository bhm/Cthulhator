package com.bustiblelemons.cthulhator.activities;

import android.os.Bundle;
import android.support.v4.view.ViewPager;

import com.bustiblelemons.activities.BaseActionBarActivity;
import com.bustiblelemons.cthulhator.R;
import com.bustiblelemons.cthulhator.adapters.PortraitsPagerAdapter;
import com.bustiblelemons.cthulhator.async.QueryGImagesAsyn;
import com.bustiblelemons.cthulhator.async.ReceiveGoogleImages;
import com.bustiblelemons.cthulhator.fragments.PortraitsSettingsFragment;
import com.bustiblelemons.google.apis.model.GoogleImageObject;
import com.bustiblelemons.google.apis.search.params.GImageSearch;
import com.bustiblelemons.google.apis.search.params.GoogleImageSearch;
import com.bustiblelemons.logging.Logger;
import com.bustiblelemons.views.LoadMoreViewPager;

import java.util.List;

import io.github.scottmaclure.character.traits.network.api.asyn.AsyncInfo;

/**
 * Created by bhm on 18.07.14.
 */
public class PortraitsActivity extends BaseActionBarActivity
        implements LoadMoreViewPager.LoadMore,
                   PortraitsSettingsFragment.GoogleSearchOptsListener, ReceiveGoogleImages {

    private LoadMoreViewPager pager;

    private PortraitsPagerAdapter pagerAdapter;
    private static final Logger log = new Logger(PortraitsActivity.class);
    private GoogleImageSearch.Options searchOptions;
    private GImageSearch              mImageSearch;
    private PortraitsSettingsFragment settingsFragment;
    private GImageSearch              mSearchToPublish;

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
        this.mImageSearch = searchOptions.build();
        queryForImages(mImageSearch);
        attachSettings();
        onSetActionBarToClosable();
    }

    private void attachSettings() {
        if (settingsFragment == null) {
            settingsFragment = PortraitsSettingsFragment.newInstance(searchOptions);
        }
        addFragment(R.id.bottom_card, settingsFragment);

    }

    private void queryForImages(GImageSearch search) {
        QueryGImagesAsyn queryGImagesAsyn = new QueryGImagesAsyn(this, this);
        queryGImagesAsyn.executeCrossPlatform(search);
    }

    @Override
    public void onLoadMore(ViewPager pager) {
        queryForImages(mImageSearch);
        log.d("onLoadMore %s", pager.getCurrentItem());
    }

    @Override
    public boolean onGoogleSearchOptionsChanged(GoogleImageSearch.Options newOptions) {
        mSearchToPublish = newOptions.build();
        queryForImages(mSearchToPublish);
        return false;
    }

    @Override
    public void onAsynTaskProgress(AsyncInfo<GImageSearch, List<GoogleImageObject>> info,
                                   GImageSearch search, List<GoogleImageObject> results) {
        if (mSearchToPublish != null && search.equals(mSearchToPublish)) {
            pagerAdapter.removeAll();
            mImageSearch = search;
            mSearchToPublish = null;
        }
        pagerAdapter.addData(results);
    }

    @Override
    public void onAsynTaskFinish(AsyncInfo<GImageSearch, List<GoogleImageObject>> info, List<GoogleImageObject> result) {

    }
}
