package com.bustiblelemons.cthulhator.character.description.ui;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.bustiblelemons.cthulhator.R;
import com.bustiblelemons.cthulhator.adapters.PhotosPagerAdapter;
import com.bustiblelemons.cthulhator.async.QueryGImagesAsyn;
import com.bustiblelemons.cthulhator.async.ReceiveGoogleImages;
import com.bustiblelemons.cthulhator.character.creation.ui.AbsCharacterCreationActivity;
import com.bustiblelemons.cthulhator.character.description.logic.CharacteristicTraitsAdapter;
import com.bustiblelemons.cthulhator.character.description.logic.RandomUserMELocationPagerAdapter;
import com.bustiblelemons.cthulhator.character.description.logic.RandomUserMENamePagerAdapter;
import com.bustiblelemons.cthulhator.character.description.model.CharacterDescription;
import com.bustiblelemons.cthulhator.character.persistance.CharacterWrappper;
import com.bustiblelemons.cthulhator.character.portrait.logic.OnBroadcastOnlineSearchSettings;
import com.bustiblelemons.cthulhator.character.portrait.logic.OnOpenSearchSettings;
import com.bustiblelemons.cthulhator.character.portrait.model.Portrait;
import com.bustiblelemons.cthulhator.character.portrait.ui.PortraitsSettingsFragment;
import com.bustiblelemons.cthulhator.character.portrait.ui.RandomCharSettingsDialog;
import com.bustiblelemons.cthulhator.settings.Settings;
import com.bustiblelemons.cthulhator.settings.character.CharacterSettings;
import com.bustiblelemons.cthulhator.system.CthulhuCharacter;
import com.bustiblelemons.cthulhator.system.edition.GameEdition;
import com.bustiblelemons.google.apis.GenderTransformer;
import com.bustiblelemons.google.apis.search.params.GImageSearch;
import com.bustiblelemons.google.apis.search.params.GoogleImageSearch;
import com.bustiblelemons.model.Gender;
import com.bustiblelemons.model.OnlinePhotoUrl;
import com.bustiblelemons.model.OnlinePhotoUrlImpl;
import com.bustiblelemons.randomuserdotme.logic.OnRandomUsersRetreived;
import com.bustiblelemons.randomuserdotme.logic.RandomUserDotMeAsyn;
import com.bustiblelemons.randomuserdotme.logic.RandomUserDotMePortraitsAsyn;
import com.bustiblelemons.randomuserdotme.logic.RandomUserMEQuery;
import com.bustiblelemons.randomuserdotme.model.Location;
import com.bustiblelemons.randomuserdotme.model.Name;
import com.bustiblelemons.randomuserdotme.model.RandomUserMe;
import com.bustiblelemons.randomuserdotme.model.User;
import com.bustiblelemons.storage.Storage;
import com.bustiblelemons.views.LoadMoreViewPager;
import com.manuelpeinado.fadingactionbar.extras.actionbarcompat.FadingActionBarHelper;
import com.viewpagerindicator.CirclePageIndicator;

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
import io.github.scottmaclure.character.traits.providers.RandomTraitsSetProvider;

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
    LoadMoreViewPager   photosPager;
    @InjectView(R.id.photos_indicator)
    CirclePageIndicator photosIndicator;
    @InjectView(R.id.names_pager)
    LoadMoreViewPager   namesPager;
    @InjectView(R.id.names_indicator)
    CirclePageIndicator namesIndicator;
    @InjectView(R.id.characteristic_pager)
    LoadMoreViewPager   characteristicPager;
    @InjectView(R.id.characteristic_indicator)
    CirclePageIndicator characteristicIndicator;
    @InjectView(R.id.location_pager)
    LoadMoreViewPager   locationsPager;
    @InjectView(R.id.location_indicator)
    CirclePageIndicator locationIndicator;
    @InjectView(R.id.fab)
    CircleButton        settingsFab;

    private PhotosPagerAdapter               photosPagerAdapter;
    private RandomUserMENamePagerAdapter     namesPagerAdapter;
    private RandomUserMELocationPagerAdapter locationPagerAdapter;
    private RandomUserMEQuery                query;
    private GImageSearch                     imageSearch;
    private GoogleImageSearch.Options        googleSearchOptions;
    private RandomUserMEQuery.Options        queryOptions;
    private CharacteristicTraitsAdapter      characteristicAdapter;
    private RandomCharSettingsDialog         randomCharSettingsDialog;
    private PortraitsSettingsFragment        mPortraitSettingsFragment;
    private CharacterSettings                mCharacterSettings;
    private CharacterWrappper                mSavedCharacter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FadingActionBarHelper helper = setupFadingBar();
        setContentView(helper.createView(this));
        mCharacterSettings = Settings.getLastPortraitSettings(this);
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
        return helper;
    }

    private void attachPortraitSettings() {
        if (mPortraitSettingsFragment == null) {
            mPortraitSettingsFragment = PortraitsSettingsFragment.newInstance(mCharacterSettings);
            mPortraitSettingsFragment.setFoldedOnly(true);
            mPortraitSettingsFragment.setOnOpenSearchSettings(this);
        }
        addFragment(R.id.bottom_card, mPortraitSettingsFragment);
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
            if (locationIndicator != null) {
                locationIndicator.setViewPager(locationsPager);
            }
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
            characteristicPager.setAdapter(characteristicAdapter);
            characteristicPager.setLoadMoreListener(this);
            if (characteristicIndicator != null) {
                characteristicIndicator.setViewPager(characteristicPager);
            }
            onTraitsDownloaded(TraitsSet.FILE);
        }
    }

    private void setupPhotosPager() {
        photosPagerAdapter = new PhotosPagerAdapter(getSupportFragmentManager());
        if (photosPagerAdapter != null) {
            photosPagerAdapter.addData(getExisitngPortrats());
        }
        photosPager.setLoadMoreListener(this);
        photosPager.setTag(R.id.tag_search, mCharacterSettings);
        photosPager.setAdapter(photosPagerAdapter);
        if (photosIndicator != null) {
            photosIndicator.setViewPager(photosPager);
        }
        onLoadMore(photosPager);
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
        if (namesIndicator != null) {
            namesIndicator.setViewPager(namesPager);
        }
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
        Gender gender = GenderTransformer.toRandomUserMe(mCharacterSettings.getGender());
        if (queryOptions == null) {
            queryOptions = new RandomUserMEQuery.Options();
        }
        if (query == null) {
            query = queryOptions.build();
        }
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
            saveCharacter();
            onBackPressed();
            break;
        }
    }

    private void saveCharacter() {
        CharacterDescription description = new CharacterDescription();
        if (mSavedCharacter == null) {
            mSavedCharacter = CthulhuCharacter.forEdition(GameEdition.CoC5);
        }
        description = addName(description);
        description = addLocation(description);
        description = addTraits(description);
        description = addPhoto(description);
        mSavedCharacter.setDescription(description);
        setResult(RESULT_OK, mSavedCharacter);
    }

    private CharacterDescription addName(CharacterDescription description) {
        int position = namesPager.getCurrentItem();
        User user = namesPagerAdapter.getItemObject(position);
        if (user != null && user.getName() != null) {
            description.setName(user.getName());
        }
        return description;
    }

    private CharacterDescription addPhoto(CharacterDescription description) {
        int photoPosition = photosPager.getCurrentItem();
        OnlinePhotoUrl o = photosPagerAdapter.getItemObject(photoPosition);
        if (o != null) {
            description.addPortrait(o.getUrl());
        }
        return description;
    }

    private CharacterDescription addTraits(CharacterDescription description) {
        int traitSetPosition = characteristicPager.getCurrentItem();
        RandomTraitsSet traits = characteristicAdapter.getItemObject(traitSetPosition);
        description.setTraits(traits);
        return description;
    }

    private CharacterDescription addLocation(CharacterDescription description) {
        int locationPosition = locationsPager.getCurrentItem();
        User user = locationPagerAdapter.getItemObject(locationPosition);
        if (user != null) {
            Location location = user.getLocation();
            description.setLocation(location);
        }
        return description;
    }

    public void handleSettingsButton() {
        if (randomCharSettingsDialog == null) {
            randomCharSettingsDialog = RandomCharSettingsDialog.newInstance(mCharacterSettings);
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
        updatePhotos(characterSettings);
        updateNames();
        if (mPortraitSettingsFragment != null && mPortraitSettingsFragment.isVisible()) {
            mPortraitSettingsFragment.updateInstanceArgument(characterSettings);
        }
    }

    private void updatePhotos(CharacterSettings characterSettings) {
        photosPager.setTag(R.id.tag_search, characterSettings);
        photosPager.removeAllViews();
        photosPagerAdapter = new PhotosPagerAdapter(getSupportFragmentManager());
        photosPager.setAdapter(photosPagerAdapter);
        onLoadMore(photosPager);
    }

    private void updateNames() {
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
    protected void onInstanceArgumentRead(CharacterWrappper arg) {
        mSavedCharacter = arg;
    }
}
