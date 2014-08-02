package com.bustiblelemons.cthulhator.activities;

import android.os.Bundle;
import android.support.v4.view.ViewPager;

import com.bustiblelemons.Storage;
import com.bustiblelemons.activities.BaseActionBarActivity;
import com.bustiblelemons.api.random.names.randomuserdotme.RandomUserDotMeQuery;
import com.bustiblelemons.api.random.names.randomuserdotme.asyn.OnRandomUsersRetreived;
import com.bustiblelemons.api.random.names.randomuserdotme.asyn.RandomUserDotMeAsyn;
import com.bustiblelemons.api.random.names.randomuserdotme.model.User;
import com.bustiblelemons.cthulhator.R;
import com.bustiblelemons.cthulhator.adapters.CharacteristicTraitsAdapter;
import com.bustiblelemons.cthulhator.adapters.RandomUserDotMePagerAdapter;
import com.bustiblelemons.views.LoadMoreViewPager;

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
public class RandomCharactersActivity extends BaseActionBarActivity implements LoadMoreViewPager.LoadMore,
                                                                               OnRandomUsersRetreived,
                                                                               OnTraitsDownload {

    @InjectView(R.id.pager)
    LoadMoreViewPager pager;
    @InjectView(R.id.characteristic_pager)
    LoadMoreViewPager characteristicPager;

    private RandomUserDotMePagerAdapter  pagerAdapter;
    private RandomUserDotMeQuery         query;
    private RandomUserDotMeQuery.Options queryOptions;
    private CharacteristicTraitsAdapter  characteristicAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_random_characters);
        ButterKnife.inject(this);
        setupRandomUserDotMe();
        DownloadDefaultTraitsAsyn traitsAsyn = new DownloadDefaultTraitsAsyn(this, this);
        traitsAsyn.executeCrossPlatform();
        if (characteristicPager != null) {
            characteristicAdapter = new CharacteristicTraitsAdapter(getSupportFragmentManager());
            characteristicPager.setAdapter(characteristicAdapter);
            characteristicPager.setLoadMoreListener(this);
        }
    }

    private void setupRandomUserDotMe() {
        pagerAdapter = new RandomUserDotMePagerAdapter(getSupportFragmentManager());
        pager.setLoadMoreListener(this);
        pager.setAdapter(pagerAdapter);
        queryOptions = new RandomUserDotMeQuery.Options();
        query = queryOptions.build();
        onLoadMore(pager);
    }

    @Override
    public void onLoadMore(ViewPager pager) {
        if (pager.getId() == R.id.pager) {
            RandomUserDotMeAsyn async = new RandomUserDotMeAsyn(this, this);
            async.executeCrossPlatform(query);
        } else if (pager.getId() == R.id.characteristic_pager) {
            onTraitsDownloaded(TraitsSet.FILE);
        }
    }

    @Override
    public int onRandomUsersRetreived(RandomUserDotMeQuery query, List<User> users) {
        pagerAdapter.addData(users);
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
