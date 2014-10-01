package com.bustiblelemons.cthulhator.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bustiblelemons.cthulhator.R;
import com.bustiblelemons.cthulhator.adapters.SkillChanged;
import com.bustiblelemons.cthulhator.creation.characteristics.logic.CharacterPropertyComparators;
import com.bustiblelemons.cthulhator.creation.characteristics.logic.CharacterPropertySortAsyn;
import com.bustiblelemons.cthulhator.creation.characteristics.logic.SkillsAdapterSticky;
import com.bustiblelemons.cthulhator.model.ActionGroup;
import com.bustiblelemons.cthulhator.model.CharacterProperty;
import com.bustiblelemons.cthulhator.model.cache.SavedCharacter;

import java.util.Comparator;
import java.util.Locale;
import java.util.Set;

import butterknife.ButterKnife;
import butterknife.InjectView;
import se.emilsjolander.stickylistheaders.StickyListHeadersListView;

/**
 * Created by bhm on 20.07.14.
 */
public class SkillsListFragment extends AbsArgFragment<SavedCharacter>
        implements SkillChanged, CharacterPropertySortAsyn.OnPropertiesSorted {

    public static final String TAG = SkillsListFragment.class.getSimpleName();
    @InjectView(android.R.id.list)
    StickyListHeadersListView listView;
    @InjectView(R.id.points_available)
    TextView                  pointsAvailable;
    //    private SkillsAdapter       mSkillsAdapter;
    private SkillsAdapterSticky mSkillsAdapterSticky;
    private int                 total;
    private String              pointsAvailablePrefix;
    private SavedCharacter      mSavedCharacter;
    private int mCareerPoints = 0;
    private int mHobbyPoints  = 0;
    private Set<CharacterProperty> mSkills;
    private Comparator<CharacterProperty> mComparator = CharacterPropertyComparators.ALPHABETICAL;

    public static SkillsListFragment newInstance(SavedCharacter arg) {
        SkillsListFragment r = new SkillsListFragment();
        r.setNewInstanceArgument(arg);
        return r;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_skill_chooser, container, false);
        ButterKnife.inject(this, rootView);
        return rootView;
    }

    @Override
    protected void onInstanceArgumentRead(SavedCharacter arg) {
        mSavedCharacter = arg;
        setupSkillsList();
        setupPoints();
    }

    private void setupPoints() {
        mCareerPoints = mSavedCharacter.getCareerPoints();
        mHobbyPoints = mSavedCharacter.getHobbyPoints();
    }

    private void setPointsAvailable(int points) {
        pointsAvailablePrefix = getString(R.string.points_available);
        String val = String.format(Locale.ENGLISH, "%s %s", pointsAvailablePrefix, points);
        pointsAvailable.setText(val);
    }

    private void setupSkillsList() {
        mSkills = mSavedCharacter.getSkills();
        CharacterPropertySortAsyn sortAsyn = new CharacterPropertySortAsyn(getContext(), this,
                mSkills);
        sortAsyn.executeCrossPlatform(mComparator);
        mSkillsAdapterSticky = new SkillsAdapterSticky(getContext(), this);
        listView.setAdapter(mSkillsAdapterSticky);
        listView.setClickable(false);
    }

    @Override
    public boolean onSkillChanged(CharacterProperty name, int value, boolean up) {
        int afterTotal = up ? total + 1 : total - 1;
        if (afterTotal >= 0) {
            setPointsAvailable(total);
            return true;
        }
        return false;
    }

    @Override
    public void onCharacterPropertiesSorted(Comparator<CharacterProperty> comparator,
                                            Set<CharacterProperty> sortedSet) {

        if (mSkillsAdapterSticky != null) {
            mSkillsAdapterSticky.refreshData(sortedSet);
        }
    }

    @Override
    public void onCharacterPropertiesSortedByGroup(Comparator<CharacterProperty> comparator,
                                                   ActionGroup header,
                                                   Set<CharacterProperty> sortedSet) {
        if (mSkillsAdapterSticky != null) {
            mSkillsAdapterSticky.addItems(sortedSet);
        }
    }
}
