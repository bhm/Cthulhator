package com.bustiblelemons.cthulhator.creation.description.ui;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.bustiblelemons.api.model.Gender;
import com.bustiblelemons.api.random.names.randomuserdotme.RandomUserMEQuery;
import com.bustiblelemons.api.random.names.randomuserdotme.asyn.OnRandomUsersRetreived;
import com.bustiblelemons.api.random.names.randomuserdotme.asyn.RandomUserDotMeAsyn;
import com.bustiblelemons.api.random.names.randomuserdotme.asyn.RandomUserDotMePortraitsAsyn;
import com.bustiblelemons.api.random.names.randomuserdotme.model.Location;
import com.bustiblelemons.api.random.names.randomuserdotme.model.Name;
import com.bustiblelemons.api.random.names.randomuserdotme.model.RandomUserMe;
import com.bustiblelemons.api.random.names.randomuserdotme.model.User;
import com.bustiblelemons.cthulhator.R;
import com.bustiblelemons.cthulhator.adapters.CharacteristicTraitsAdapter;
import com.bustiblelemons.cthulhator.adapters.PhotosPagerAdapter;
import com.bustiblelemons.cthulhator.adapters.RandomUserMELocationPagerAdapter;
import com.bustiblelemons.cthulhator.adapters.RandomUserMENamePagerAdapter;
import com.bustiblelemons.cthulhator.async.QueryGImagesAsyn;
import com.bustiblelemons.cthulhator.async.ReceiveGoogleImages;
import com.bustiblelemons.cthulhator.creation.ui.AbsCharacterCreationActivity;
import com.bustiblelemons.cthulhator.fragments.OnBroadcastOnlineSearchSettings;
import com.bustiblelemons.cthulhator.fragments.OnOpenSearchSettings;
import com.bustiblelemons.cthulhator.fragments.PortraitsSettingsFragment;
import com.bustiblelemons.cthulhator.fragments.dialog.RandomCharSettingsDialog;
import com.bustiblelemons.cthulhator.model.CharacterSettings;
import com.bustiblelemons.cthulhator.model.CthulhuCharacter;
import com.bustiblelemons.cthulhator.model.CthulhuEdition;
import com.bustiblelemons.cthulhator.model.Portrait;
import com.bustiblelemons.cthulhator.model.cache.SavedCharacter;
import com.bustiblelemons.cthulhator.model.desc.CharacterDescription;
import com.bustiblelemons.cthulhator.settings.Settings;
import com.bustiblelemons.google.apis.GenderTransformer;
import com.bustiblelemons.google.apis.search.params.GImageSearch;
import com.bustiblelemons.google.apis.search.params.GoogleImageSearch;
import com.bustiblelemons.model.OnlinePhotoUrl;
import com.bustiblelemons.model.OnlinePhotoUrlImpl;
import com.bustiblelemons.storage.Storage;
import com.bustiblelemons.views.LoadMoreViewPager;
import com.manuelpeinado.fadingactionbar.extras.actionbarcompat.FadingActionBarHelper;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import at.markushi.ui.CircleButton;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import io.github.scottmaclure.character.traits.model.RandomTraitsSet;
import io.github.scottmaclure.character.traits.model.TraitsSet;
import io.github.scottmaclure.character.traits.model.providers.RandomTraitsSetProvider;

/**
 * Created by bhm on 25.07.14.
 */
public class RandomCharactersActivity extends AbsCharacterCreationActivity
        implements
        LoadMoreViewPager.LoadMore,
        OnRandomUsersRetreived,
        View.OnClickListener,
        OnOpenSearchSettings,
        OnBroadcastOnlineSearchSettings,
        ReceiveGoogleImages {

    public static final int REQUEST_CODE = 2;
    @InjectView(R.id.photos_pager)
    LoadMoreViewPager photosPager;
    @InjectView(R.id.names_pager)
    LoadMoreViewPager namesPager;
    @InjectView(R.id.characteristic_pager)
    LoadMoreViewPager characteristicPager;
    @InjectView(R.id.location_pager)
    LoadMoreViewPager locationsPager;
    @InjectView(R.id.fab)
    CircleButton      settingsFab;

    private PhotosPagerAdapter               photosPagerAdapter;
    private RandomUserMENamePagerAdapter     namesPagerAdapter;
    private RandomUserMELocationPagerAdapter locationPagerAdapter;
    private RandomUserMEQuery                query;
    private GImageSearch                     imageSearch;
    private GoogleImageSearch.Options        googleSearchOptions;
    private RandomUserMEQuery.Options        queryOptions;
    private CharacteristicTraitsAdapter      characteristicAdapter;
    private RandomCharSettingsDialog         randomCharSettingsDialog;
    private PortraitsSettingsFragment        portraitSettingsFragment;
    private CharacterSettings                characterSettings;
    private SavedCharacter                   mSavedCharacter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FadingActionBarHelper helper = setupFadingBar();
        characterSettings = Settings.getLastPortratiSettings(this);
        attachPortraitSettings();
        onSetActionBarToClosable();
        helper.initActionBar(this);
        ButterKnife.inject(this);
        mSavedCharacter = getInstanceArgument();
        setupView();
    }

    private void setupView() {
        setupPhotosPager();
        setupNamesPager();
        setupLocationsPager();
        setupCharacteristicsPager();
        setupRandomUserMEQuery();
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
            portraitSettingsFragment = PortraitsSettingsFragment.newInstance(characterSettings);
            portraitSettingsFragment.setFoldedOnly(true);
            portraitSettingsFragment.setOnOpenSearchSettings(this);
        }
        addFragment(R.id.bottom_card, portraitSettingsFragment);
    }


    private void setupLocationsPager() {
        if (locationsPager != null) {
            locationPagerAdapter = new RandomUserMELocationPagerAdapter(
                    getSupportFragmentManager());
            if (mSavedCharacter != null && mSavedCharacter.getDescription() != null) {
                Location location = mSavedCharacter.getDescription().getLocation();
                if (location != null) {
                    User user = new User();
                    user.setLocation(location);
                    locationPagerAdapter.addData(user);
                }
            }
            locationsPager.setAdapter(locationPagerAdapter);
            locationsPager.setLoadMoreListener(this);
        }
    }

    private void setupCharacteristicsPager() {
        if (characteristicPager != null) {
            characteristicAdapter = new CharacteristicTraitsAdapter(getSupportFragmentManager());
            if (mSavedCharacter != null && mSavedCharacter.getDescription() != null) {
                RandomTraitsSet set = mSavedCharacter.getDescription().getTraits();
                if (set != null) {
                    characteristicAdapter.addData(set);
                }
            }
            characteristicPager.setLoadMoreListener(this);
            characteristicPager.setAdapter(characteristicAdapter);
            onTraitsDownloaded(TraitsSet.FILE);
        }
    }

    private void setupPhotosPager() {
        photosPagerAdapter = new PhotosPagerAdapter(getSupportFragmentManager());
        if (photosPagerAdapter != null) {
            photosPagerAdapter.addData(getExisitngPortrats());
        }
        photosPager.setLoadMoreListener(this);
        photosPager.setTag(R.id.tag_search, characterSettings);
        photosPager.setAdapter(photosPagerAdapter);
    }

    private List<OnlinePhotoUrl> getExisitngPortrats() {
        if (mSavedCharacter != null) {
            List<Portrait> portraits = mSavedCharacter.getPortraits();
            if (portraits != null && portraits.size() > 0) {
                List<OnlinePhotoUrl> list = new ArrayList<OnlinePhotoUrl>();
                for (Portrait portrait : mSavedCharacter.getPortraits()) {
                    if (portrait != null && portrait.getUrl() != null) {
                        OnlinePhotoUrlImpl opul = new OnlinePhotoUrlImpl(portrait.getUrl());
                        list.add(opul);
                    }
                }
                return list;
            }
        }
        return Collections.emptyList();
    }

    private void setupRandomUserMEQuery() {
        queryOptions = new RandomUserMEQuery.Options();
        query = queryOptions.build();
        executeRandomUserMeQuery(RandomUserMe.All);
    }

    private void setupNamesPager() {
        namesPagerAdapter = new RandomUserMENamePagerAdapter(getSupportFragmentManager());
        if (mSavedCharacter != null && mSavedCharacter.getName() != null) {
            User user = new User();
            Name name = mSavedCharacter.getNameObject();
            if (user != null && name != null) {
                user.setName(name);
                namesPagerAdapter.addData(user);
            }
        }
        namesPager.setLoadMoreListener(this);
        namesPager.setAdapter(namesPagerAdapter);
    }

    @Override
    public void onLoadMore(ViewPager pager) {
        int id = pager.getId();
        if (id == R.id.names_pager) {
            executeRandomUserMeQuery(RandomUserMe.Names);
        } else if (id == R.id.location_pager) {
            executeRandomUserMeQuery(RandomUserMe.Location);
        } else if (id == R.id.characteristic_pager) {
            onTraitsDownloaded(TraitsSet.FILE);
        } else if (id == R.id.photos_pager) {
            loadMorePhotos(pager);
        }
    }

    private void loadMorePhotos(ViewPager pager) {
        CharacterSettings settings = (CharacterSettings) pager.getTag(R.id.tag_search);
        characterSettings = settings;
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
                imageSearch = googleSearchOptions.build();
            }
            queryForImages(imageSearch);
        }
    }

    private void queryForImages(GImageSearch search) {
        QueryGImagesAsyn queryGImagesAsyn = new QueryGImagesAsyn(this, this);
        queryGImagesAsyn.executeCrossPlatform(search);
    }

    public void executeRandomUserMeQuery(RandomUserMe postPart) {
        Gender gender = GenderTransformer.toRandomUserMe(characterSettings.getGender());
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
    public int onRandomUsersRetreived(RandomUserMEQuery query, List<User> users) {
        namesPagerAdapter.addData(users);
        locationPagerAdapter.addData(users);
        return 0;
    }

    @Override
    public int onRandomUsersLocations(RandomUserMEQuery query, List<User> users) {
        locationPagerAdapter.addData(users);
        return 0;
    }

    @Override
    public int onRandomUsersNames(RandomUserMEQuery query, List<User> users) {
        namesPagerAdapter.addData(users);
        return 0;
    }

    @Override
    public int onRandomUsersPortraits(RandomUserMEQuery query, List<OnlinePhotoUrl> userPhotos) {
        photosPagerAdapter.addData(userPhotos);
        return 0;
    }

    private boolean isModernSearch() {
        CharacterSettings query = (CharacterSettings) photosPager.getTag(R.id.tag_search);
        return query.isModern();
    }

    public boolean onTraitsDownloaded(String fileName) {
        File traitsFile = Storage.getStorageFile(this, fileName);
        String filesize = FileUtils.byteCountToDisplaySize(traitsFile.length());
        log.d("onTraitsDownloaded size %s ", filesize);
        try {
            RandomTraitsSetProvider rtsp = RandomTraitsSetProvider.from(this);
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
    @OnClick(R.id.fab)
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.action_settings:
                handleSettingsButton();
                break;
            case R.id.fab:
                //TODO Save the set as a character properties set
                saveCharacter();
                onBackPressed();
                break;
        }
    }

    private void saveCharacter() {
        CharacterDescription description = new CharacterDescription();
        addName(description);
        addLocation(description);
        addTraits(description);
        addPhoto(description);
        mSavedCharacter.setDescription(description);
        setResult(RESULT_OK, mSavedCharacter);
    }

    private void addName(CharacterDescription description) {
        int position = namesPager.getCurrentItem();
        User user = namesPagerAdapter.getItemObject(position);
        if (user != null && user.getName() != null) {
            description.setName(user.getName());
        }
    }

    private void addPhoto(CharacterDescription description) {
        int photoPosition = photosPager.getCurrentItem();
        OnlinePhotoUrl o = photosPagerAdapter.getItemObject(photoPosition);
        if (o != null) {
            description.addPortrait(o.getUrl());
        }
    }

    private void addTraits(CharacterDescription description) {
        int traitSetPosition = characteristicPager.getCurrentItem();
        RandomTraitsSet traits = characteristicAdapter.getItemObject(traitSetPosition);
        description.setTraits(traits);
        if (mSavedCharacter == null) {
            mSavedCharacter = CthulhuCharacter.forEdition(CthulhuEdition.CoC5);
        }
    }

    private void addLocation(CharacterDescription description) {
        int locationPosition = locationsPager.getCurrentItem();
        User userPosition = locationPagerAdapter.getItemObject(locationPosition);
        if (userPosition != null) {
            Location location = userPosition.getLocation();
            description.setLocation(location);
        }
    }

    public void handleSettingsButton() {
        if (randomCharSettingsDialog == null) {
            randomCharSettingsDialog = RandomCharSettingsDialog.newInstance(characterSettings);
        }
        FragmentManager fm = getSupportFragmentManager();
        randomCharSettingsDialog.show(fm, randomCharSettingsDialog.TAG);
    }

    @Override
    public void onOpenSettings(CharacterSettings search) {
        handleSettingsButton();
    }


    @Override
    public void onSettingsChanged(CharacterSettings characterSettings, boolean apply) {
        photosPager.setTag(R.id.tag_search, characterSettings);
        photosPager.removeAllViews();
        photosPagerAdapter = new PhotosPagerAdapter(getSupportFragmentManager());
        photosPager.setAdapter(photosPagerAdapter);
        onLoadMore(photosPager);
        namesPager.removeAllViews();
        namesPagerAdapter = new RandomUserMENamePagerAdapter(getSupportFragmentManager());
        namesPager.setAdapter(namesPagerAdapter);
        onLoadMore(namesPager);
    }


    @Override
    public boolean onGoogleImageObjectsDownloaded(GImageSearch search, List<OnlinePhotoUrl> results) {
        if (results != null && photosPagerAdapter != null) {
            photosPagerAdapter.addData(results);
        }
        return false;
    }

    @Override
    protected void onInstanceArgumentRead(SavedCharacter arg) {
        mSavedCharacter = arg;
    }
}
