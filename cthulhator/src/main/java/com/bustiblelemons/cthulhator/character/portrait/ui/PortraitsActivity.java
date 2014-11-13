package com.bustiblelemons.cthulhator.character.portrait.ui;

import android.os.Bundle;
import android.support.v4.view.ViewPager;

import com.bustiblelemons.activities.AbsActionBarActivity;
import com.bustiblelemons.cthulhator.R;
import com.bustiblelemons.cthulhator.async.QueryGImagesAsyn;
import com.bustiblelemons.cthulhator.async.ReceiveGoogleImages;
import com.bustiblelemons.cthulhator.character.description.logic.PortraitsPagerAdapter;
import com.bustiblelemons.cthulhator.character.portrait.logic.OnBroadcastOnlineSearchSettings;
import com.bustiblelemons.cthulhator.settings.Settings;
import com.bustiblelemons.cthulhator.settings.character.CharacterSettings;
import com.bustiblelemons.cthulhator.settings.character.CharacterSettingsImpl;
import com.bustiblelemons.google.apis.GenderTransformer;
import com.bustiblelemons.google.apis.GoogleSearchGender;
import com.bustiblelemons.google.apis.search.params.GImageSearch;
import com.bustiblelemons.google.apis.search.params.GoogleImageSearch;
import com.bustiblelemons.logging.Logger;
import com.bustiblelemons.model.Gender;
import com.bustiblelemons.model.OnlinePhotoUrl;
import com.bustiblelemons.randomuserdotme.logic.OnRandomUsersRetreived;
import com.bustiblelemons.randomuserdotme.logic.RandomUserDotMeAsyn;
import com.bustiblelemons.randomuserdotme.logic.RandomUserDotMePortraitsAsyn;
import com.bustiblelemons.randomuserdotme.logic.RandomUserMEQuery;
import com.bustiblelemons.randomuserdotme.model.RandomUserMe;
import com.bustiblelemons.randomuserdotme.model.User;
import com.bustiblelemons.views.LoadMoreViewPager;

import java.util.List;

/**
 * Created by bhm on 18.07.14.
 */
public class PortraitsActivity extends AbsActionBarActivity
        implements LoadMoreViewPager.LoadMore,
                   PortraitsSettingsFragment.GoogleSearchOptsListener,
                   ReceiveGoogleImages,
                   OnRandomUsersRetreived,
                   OnBroadcastOnlineSearchSettings {

    private static final Logger log = new Logger(PortraitsActivity.class);
    private LoadMoreViewPager         photosPager;
    private PortraitsPagerAdapter     photosPagerAdapter;
    private GImageSearch              mImageSearch;
    private PortraitsSettingsFragment settingsFragment;
    private GImageSearch              mSearchToPublish;
    private CharacterSettings         mCharacterSettings;
    private GoogleSearchGender        mGender;
    private int                       mYear;
    private RandomUserMEQuery         query;
    private GoogleImageSearch.Options googleSearchOptions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_portraits);
        mCharacterSettings = Settings.getLastPortraitSettings(this);
        mGender = mCharacterSettings.getGender();
        mYear = mCharacterSettings.getYear();
        mCharacterSettings = CharacterSettingsImpl.create(mYear, mGender);
        photosPager = (LoadMoreViewPager) findViewById(R.id.pager);
        photosPagerAdapter = new PortraitsPagerAdapter(getSupportFragmentManager());
        photosPager.setAdapter(photosPagerAdapter);
        photosPager.setLoadMoreListener(this);
        googleSearchOptions = new GoogleImageSearch.Options();
        googleSearchOptions.setQuery("1920+male+portrait");
        this.mImageSearch = googleSearchOptions.build();
        queryForImages(mImageSearch);
        attachSettings();
        onSetActionBarToClosable();
    }

    private void attachSettings() {
        if (settingsFragment == null) {
            settingsFragment = PortraitsSettingsFragment.newInstance(mCharacterSettings);
        }
        addFragment(R.id.bottom_card, settingsFragment);

    }

    private void queryForImages(GImageSearch search) {
        QueryGImagesAsyn queryGImagesAsyn = new QueryGImagesAsyn(this, this);
        queryGImagesAsyn.executeCrossPlatform(search);
    }

    @Override
    public void onLoadMore(ViewPager pager) {
        CharacterSettings settings = (CharacterSettings) pager.getTag(R.id.tag_search);
        mCharacterSettings = settings;
        if (settings.isModern()) {
            executeRandomUserMeQuery(RandomUserMe.Portraits);
        } else {
            if (googleSearchOptions == null) {
                googleSearchOptions = new GoogleImageSearch.Options();
            }
            String lastQuery = googleSearchOptions.getQuery();
            String settingsQuery = settings.getQuery().concat("+portrait");
            if (!settingsQuery.equalsIgnoreCase(lastQuery)) {
                googleSearchOptions.setQuery(settingsQuery);
                mImageSearch = googleSearchOptions.build();
            }
            queryForImages(mImageSearch);
        }
    }

    public void executeRandomUserMeQuery(RandomUserMe postPart) {
        Gender gender = GenderTransformer.toRandomUserMe(mCharacterSettings.getGender());
        query.setGender(gender);
        if (RandomUserMe.Portraits.equals(postPart)) {
            RandomUserDotMePortraitsAsyn async = new RandomUserDotMePortraitsAsyn(this, this);
            async.executeCrossPlatform(query);
        } else {
            RandomUserDotMeAsyn async = new RandomUserDotMeAsyn(this, this);
            async.postPart(postPart);
            async.executeCrossPlatform(query);
        }
    }

    @Override
    public boolean onGoogleSearchOptionsChanged(GoogleImageSearch.Options newOptions) {
        mSearchToPublish = newOptions.build();
        queryForImages(mSearchToPublish);
        return false;
    }

    @Override
    public boolean onGoogleImageObjectsDownloaded(GImageSearch search, List<OnlinePhotoUrl> objects) {
        if (mSearchToPublish != null && search.equals(mSearchToPublish)) {
            photosPagerAdapter.removeAll();
            mImageSearch = search;
            mSearchToPublish = null;
        }
        photosPagerAdapter.addData(objects);
        return false;
    }

    @Override
    public int onRandomUsersRetreived(RandomUserMEQuery query, List<User> users) {
        return 0;
    }

    @Override
    public int onRandomUsersLocations(RandomUserMEQuery query, List<User> users) {
        return 0;
    }

    @Override
    public int onRandomUsersNames(RandomUserMEQuery query, List<User> users) {
        return 0;
    }

    @Override
    public int onRandomUsersPortraits(RandomUserMEQuery query, List<OnlinePhotoUrl> users) {
        photosPagerAdapter.addData(users);
        return users != null ? users.size() : 0;
    }


    @Override
    public void onSettingsChanged(CharacterSettings characterSettings, boolean apply) {
        photosPager.setTag(R.id.tag_search, characterSettings);
        photosPager.removeAllViews();
        photosPagerAdapter = new PortraitsPagerAdapter(getSupportFragmentManager());
        photosPager.setAdapter(photosPagerAdapter);
        onLoadMore(photosPager);
    }
}
