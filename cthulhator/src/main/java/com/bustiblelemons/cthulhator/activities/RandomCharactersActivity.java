package com.bustiblelemons.cthulhator.activities;

import android.os.Bundle;
import android.support.v4.view.ViewPager;

import com.bustiblelemons.Storage;
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
import com.bustiblelemons.views.LoadMoreViewPager;
import com.manuelpeinado.fadingactionbar.extras.actionbarcompat.FadingActionBarHelper;

import java.io.File;
import java.io.IOException;
import java.util.List;

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
public class RandomCharactersActivity extends BaseActionBarActivity implements
                                                                    LoadMoreViewPager.LoadMore,
                                                                    OnRandomUsersRetreived,
                                                                    OnTraitsDownload {

    @InjectView(R.id.photos_pager)
    LoadMoreViewPager photosPager;
    @InjectView(R.id.names_pager)
    LoadMoreViewPager namesPager;
    @InjectView(R.id.characteristic_pager)
    LoadMoreViewPager characteristicPager;
    @InjectView(R.id.location_pager)
    LoadMoreViewPager locationsPager;

    private RandomUserMEPhotoPagerAdapter    photosPagerAdapter;
    private RandomUserMENamePagerAdapter     namesPagerAdapter;
    private RandomUserMELocationPagerAdapter locationPagerAdapter;
    private RandomUserMEQuery                query;
    private RandomUserMEQuery.Options        queryOptions;
    private CharacteristicTraitsAdapter      characteristicAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FadingActionBarHelper helper = new FadingActionBarHelper()
                .actionBarBackground(R.drawable.actionbar_brp)
                .headerLayout(R.layout.header_random_characters)
                .headerOverlayLayout(R.layout.header_random_characters_overlay)
                .contentLayout(R.layout.activity_random_characters)
                .lightActionBar(true);
        setContentView(helper.createView(this));
        onSetActionBarToClosable();
        helper.initActionBar(this);
        ButterKnife.inject(this);
        setupPhotosPager();
        setupNamesPager();
        setupLocationsPager();
        setupRandomUserMEQuery();
        setupCharacteristicsPager();
        DownloadDefaultTraitsAsyn traitsAsyn = new DownloadDefaultTraitsAsyn(this, this);
        traitsAsyn.executeCrossPlatform();
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
                id == R.id.location_pager ||
                id == R.id.photos_pager) {
            RandomUserDotMeAsyn async = new RandomUserDotMeAsyn(this, this);
            async.executeCrossPlatform(query);
        } else if (id == R.id.characteristic_pager) {
            onTraitsDownloaded(TraitsSet.FILE);
        }
    }

    @Override
    public int onRandomUsersRetreived(RandomUserMEQuery query, List<User> users) {
        photosPagerAdapter.addData(users);
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
}
