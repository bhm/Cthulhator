package com.bustiblelemons.cthulhator.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.bustiblelemons.activities.BaseActionBarActivity;
import com.bustiblelemons.api.random.names.randomuserdotme.RandomUserMEQuery;
import com.bustiblelemons.api.random.names.randomuserdotme.asyn.OnRandomUsersRetreived;
import com.bustiblelemons.api.random.names.randomuserdotme.asyn.RandomUserDotMeAsyn;
import com.bustiblelemons.api.random.names.randomuserdotme.model.User;
import com.bustiblelemons.cthulhator.R;
import com.bustiblelemons.cthulhator.adapters.CharacteristicTraitsAdapter;
import com.bustiblelemons.cthulhator.adapters.RandomUserMELocationPagerAdapter;
import com.bustiblelemons.cthulhator.adapters.RandomUserMENamePagerAdapter;
import com.bustiblelemons.cthulhator.adapters.RandomUserMEPhotoPagerAdapter;
import com.bustiblelemons.cthulhator.async.QueryGImagesAsyn;
import com.bustiblelemons.cthulhator.fragments.OnBroadcastOnlineSearchSettings;
import com.bustiblelemons.cthulhator.fragments.OnOpenSearchSettings;
import com.bustiblelemons.cthulhator.fragments.PortraitsSettingsFragment;
import com.bustiblelemons.cthulhator.fragments.dialog.RandomCharSettingsDialog;
import com.bustiblelemons.cthulhator.model.OnlinePhotoSearchQuery;
import com.bustiblelemons.cthulhator.view.TouchRedelegate;
import com.bustiblelemons.google.apis.model.GoogleImageObject;
import com.bustiblelemons.google.apis.search.params.GImageSearch;
import com.bustiblelemons.google.apis.search.params.GoogleImageSearch;
import com.bustiblelemons.storage.Storage;
import com.bustiblelemons.views.LoadMoreViewPager;
import com.manuelpeinado.fadingactionbar.extras.actionbarcompat.FadingActionBarHelper;

import java.io.File;
import java.io.IOException;
import java.util.List;

import at.markushi.ui.CircleButton;
import butterknife.ButterKnife;
import butterknife.InjectView;
import io.github.scottmaclure.character.traits.model.RandomTraitsSet;
import io.github.scottmaclure.character.traits.model.TraitsSet;
import io.github.scottmaclure.character.traits.model.providers.RandomTraitsSetProvider;
import io.github.scottmaclure.character.traits.network.api.asyn.AsyncInfo;
import io.github.scottmaclure.character.traits.network.api.asyn.DownloadDefaultTraitsAsyn;
import io.github.scottmaclure.character.traits.network.api.asyn.OnTraitsDownload;

/**
 * Created by bhm on 25.07.14.
 */
public class RandomCharactersActivity extends BaseActionBarActivity
        implements
        LoadMoreViewPager.LoadMore,
        OnRandomUsersRetreived,
        OnTraitsDownload,
        View.OnClickListener,
        OnOpenSearchSettings,
        OnBroadcastOnlineSearchSettings,
        QueryGImagesAsyn.ReceiveGoogleImages {

    @InjectView(R.id.photos_pager)
    LoadMoreViewPager photosPager;
    @InjectView(R.id.dummy_photos_pager)
    LoadMoreViewPager dummyPhotosPager;
    @InjectView(R.id.names_pager)
    LoadMoreViewPager namesPager;
    @InjectView(R.id.characteristic_pager)
    LoadMoreViewPager characteristicPager;
    @InjectView(R.id.location_pager)
    LoadMoreViewPager locationsPager;
    @InjectView(R.id.fab)
    CircleButton      settingsFab;

    private RandomUserMEPhotoPagerAdapter    photosPagerAdapter;
    private RandomUserMENamePagerAdapter     namesPagerAdapter;
    private RandomUserMELocationPagerAdapter locationPagerAdapter;
    private RandomUserMEQuery                query;
    private GImageSearch                     imageSearch;
    private GoogleImageSearch.Options        googleSearchOptions;
    private RandomUserMEQuery.Options        queryOptions;
    private CharacteristicTraitsAdapter      characteristicAdapter;
    private RandomCharSettingsDialog         randomCharSettingsDialog;
    private PortraitsSettingsFragment        portraitSettingsFragment;
    private OnlinePhotoSearchQuery           mOnlinePhotoSearchQuery;
    private TouchRedelegate touchRedelegate = new TouchRedelegate();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FadingActionBarHelper helper = setupFadingBar();
        attachPortraitSettings();
        onSetActionBarToClosable();
        helper.initActionBar(this);
        ButterKnife.inject(this);
        settingsFab.setOnClickListener(this);
//        redelegateTouch();
        setupPhotosPager();
        setupNamesPager();
        setupLocationsPager();
        setupRandomUserMEQuery();
        setupCharacteristicsPager();
        DownloadDefaultTraitsAsyn traitsAsyn = new DownloadDefaultTraitsAsyn(this, this);
        traitsAsyn.executeCrossPlatform();
    }

    private void redelegateTouch() {
        if (photosPager != null && dummyPhotosPager != null) {
            touchRedelegate.setReceiver(photosPager);
            touchRedelegate.setReceiver(dummyPhotosPager);
        }
    }

    private FadingActionBarHelper setupFadingBar() {
        FadingActionBarHelper helper = new FadingActionBarHelper()
                .actionBarBackground(R.drawable.actionbar_brp)
                .headerLayout(R.layout.header_random_characters)
                .headerOverlayLayout(R.layout.header_random_characters_overlay)
                .contentLayout(R.layout.activity_random_characters)
                .lightActionBar(true);
        setContentView(helper.createView(this));
        return helper;
    }

    private void attachPortraitSettings() {
        if (portraitSettingsFragment == null) {
            GoogleImageSearch.Options opts = new GoogleImageSearch.Options();
            portraitSettingsFragment = PortraitsSettingsFragment.newInstance(opts);
            portraitSettingsFragment.setFoldedOnly(true);
            portraitSettingsFragment.setOnOpenSearchSettings(this);
        }
        addFragment(R.id.bottom_card, portraitSettingsFragment);
    }


    private void setupLocationsPager() {
        if (locationsPager != null) {
            locationPagerAdapter = new RandomUserMELocationPagerAdapter(
                    getSupportFragmentManager());
            locationsPager.setAdapter(locationPagerAdapter);
            locationsPager.setLoadMoreListener(this);
        }
    }

    private void setupCharacteristicsPager() {
        if (characteristicPager != null) {
            characteristicAdapter = new CharacteristicTraitsAdapter(getSupportFragmentManager());
            characteristicPager.setAdapter(characteristicAdapter);
            characteristicPager.setLoadMoreListener(this);
        }
    }

    private void setupPhotosPager() {
        photosPagerAdapter = new RandomUserMEPhotoPagerAdapter(getSupportFragmentManager());
        photosPager.setLoadMoreListener(this);
        photosPager.setAdapter(photosPagerAdapter);
    }

    private void setupRandomUserMEQuery() {
        queryOptions = new RandomUserMEQuery.Options();
        query = queryOptions.build();
        onLoadMore(namesPager);
    }

    private void setupNamesPager() {
        namesPagerAdapter = new RandomUserMENamePagerAdapter(getSupportFragmentManager());
        namesPager.setLoadMoreListener(this);
        namesPager.setAdapter(namesPagerAdapter);
    }

    @Override
    public void onLoadMore(ViewPager pager) {
        int id = pager.getId();
        if (id == R.id.names_pager ||
                id == R.id.names_pager ||
                id == R.id.location_pager) {
            executeRandomUserMeQuery();
        } else if (id == R.id.characteristic_pager) {
            onTraitsDownloaded(TraitsSet.FILE);
        } else if (id == R.id.photos_pager) {
            if (mOnlinePhotoSearchQuery.isModern()) {
                executeRandomUserMeQuery();
            } else {
                if (googleSearchOptions == null) {
                    googleSearchOptions = new GoogleImageSearch.Options();
                    googleSearchOptions.setQuery(mOnlinePhotoSearchQuery.getQuery());
                    executeGoogleImageSearch(googleSearchOptions);
                }
            }
        }
    }

    private void executeGoogleImageSearch(GoogleImageSearch.Options options) {
        if (options != null) {
            queryForImages(options.build());
        }
    }

    private void queryForImages(GImageSearch search) {
        QueryGImagesAsyn queryGImagesAsyn = new QueryGImagesAsyn(this, this);
        queryGImagesAsyn.executeCrossPlatform(search);
    }
    public void executeRandomUserMeQuery() {
        RandomUserDotMeAsyn async = new RandomUserDotMeAsyn(this, this);
        async.executeCrossPlatform(query);
    }

    @Override
    public int onRandomUsersRetreived(RandomUserMEQuery query, List<User> users) {
        if (mOnlinePhotoSearchQuery.isModern()) {
            photosPagerAdapter.addData(users);
        }
        namesPagerAdapter.addData(users);
        locationPagerAdapter.addData(users);
        return 0;
    }

    public boolean onTraitsDownloaded(String fileName) {
        File traitsFile = Storage.getStorageFile(this, fileName);
        try {
            RandomTraitsSetProvider rtsp = RandomTraitsSetProvider.getInstance(traitsFile);
            if (rtsp != null) {
                List<RandomTraitsSet> data = rtsp.getRandomTraitSets(4);
                characteristicAdapter.addData(data);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void onAsynTaskProgress(AsyncInfo<String, TraitsSet> info, String param, TraitsSet result) {
        onTraitsDownloaded(param);
    }

    @Override
    public void onAsynTaskFinish(AsyncInfo<String, TraitsSet> info, TraitsSet result) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.action_settings:
                handleSettingsButton();
                break;
            case R.id.fab:
                //TODO Save the set as a character properties set
                this.finish();
                break;
        }
    }

    public void handleSettingsButton() {
        if (randomCharSettingsDialog == null) {
            randomCharSettingsDialog = RandomCharSettingsDialog.newInstance();
        }
        FragmentManager fm = getSupportFragmentManager();
        randomCharSettingsDialog.show(fm, randomCharSettingsDialog.TAG);
    }

    @Override
    public void onOpenSearchSettings(OnlinePhotoSearchQuery search) {
        handleSettingsButton();
    }

    @Override
    public boolean onGoogleImagesReceived(GImageSearch search, List<GoogleImageObject> results) {
        Object cachedSearch = photosPager.getTag(R.id.tag_search);
        if (cachedSearch != null) {
            if (cachedSearch.equals(search)) {
                photosPagerAdapter.addData(results);
                return true;
            } else {
                photosPagerAdapter.removeAll();
                photosPagerAdapter.addData(results);
                photosPager.setTag(R.id.tag_search, search);
                return true;
            }
        } else {
            photosPagerAdapter.addData(results);
            return true;
        }
    }

    @Override
    public void onBroadcastOnlineSearchSettings(OnlinePhotoSearchQuery onlinePhotoSearchQuery,
                                                boolean apply) {
        mOnlinePhotoSearchQuery = onlinePhotoSearchQuery;
    }
}
