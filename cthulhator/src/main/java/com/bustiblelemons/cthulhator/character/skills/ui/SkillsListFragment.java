package com.bustiblelemons.cthulhator.character.skills.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bustiblelemons.cthulhator.R;
import com.bustiblelemons.cthulhator.adapters.SkillChanged;
import com.bustiblelemons.cthulhator.character.characteristics.logic.CharacterPropertyComparators;
import com.bustiblelemons.cthulhator.character.characteristics.logic.CharacterPropertySortAsyn;
import com.bustiblelemons.cthulhator.character.characterslist.model.SavedCharacter;
import com.bustiblelemons.cthulhator.character.skills.logic.SkillsAdapterSticky;
import com.bustiblelemons.cthulhator.fragments.AbsArgFragment;
import com.bustiblelemons.cthulhator.system.properties.ActionGroup;
import com.bustiblelemons.cthulhator.system.properties.CharacterProperty;

import java.util.Comparator;
import java.util.List;
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
    private int                 mPointsAvailable;
    private String              pointsAvailablePrefix;
    private SavedCharacter      mSavedCharacter;
    private int mCareerPoints = 0;
    private int mHobbyPoints  = 0;
    private Set<CharacterProperty> mSkills;
    private Comparator<CharacterProperty> mComparator = CharacterPropertyComparators.ACTION_GROUP;
    private int mMaxPoints;

    public static SkillsListFragment newInstance(SavedCharacter arg) {
        SkillsListFragment r = new SkillsListFragment();
        r.setNewInstanceArgument(arg);
        return r;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_skill_editor, container, false);
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
        mPointsAvailable = mHobbyPoints + mCareerPoints;
        mMaxPoints = mPointsAvailable;
        setPointsAvailable(mMaxPoints);
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
        // uping the skill depletes the point pool
        int afterTotal = up ? mPointsAvailable - 1 : mPointsAvailable + 1;
        if (afterTotal >= 0 && afterTotal <= mMaxPoints) {
            mPointsAvailable = afterTotal;
            setPointsAvailable(mPointsAvailable);
            return true;
        }
        return false;
    }

    @Override
    public void onCharacterPropertiesSorted(Comparator<CharacterProperty> comparator,
                                            List<CharacterProperty> sortedSet) {

        if (mSkillsAdapterSticky != null) {
            mSkillsAdapterSticky.refreshData(sortedSet);
        }
    }

    @Override
    public void onCharacterPropertiesSortedByGroup(Comparator<CharacterProperty> comparator,
                                                   ActionGroup header,
                                                   List<CharacterProperty> sortedSet) {
        if (mSkillsAdapterSticky != null) {
            mSkillsAdapterSticky.addItems(sortedSet);
        }
    }
}
