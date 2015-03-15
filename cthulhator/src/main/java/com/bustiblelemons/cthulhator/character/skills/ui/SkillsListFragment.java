package com.bustiblelemons.cthulhator.character.skills.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bustiblelemons.cthulhator.R;
import com.bustiblelemons.cthulhator.adapters.OnSkillChanged;
import com.bustiblelemons.cthulhator.character.characteristics.logic.CharacterPropertyComparators;
import com.bustiblelemons.cthulhator.character.characteristics.logic.CharacterPropertySortAsyn;
import com.bustiblelemons.cthulhator.character.skills.logic.CanModifyPointPool;
import com.bustiblelemons.cthulhator.character.skills.logic.OnSaveSkills;
import com.bustiblelemons.cthulhator.character.skills.logic.OnSkillPointsPoolChanged;
import com.bustiblelemons.cthulhator.character.skills.logic.SkillsAdapterSticky;
import com.bustiblelemons.cthulhator.character.skills.model.SkillsPackage;
import com.bustiblelemons.cthulhator.fragments.AbsFragmentWithSerializable;
import com.bustiblelemons.cthulhator.system.properties.ActionGroup;
import com.bustiblelemons.cthulhator.system.properties.CharacterProperty;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.Optional;
import se.emilsjolander.stickylistheaders.StickyListHeadersListView;

/**
 * Created by bhm on 20.07.14.
 */
public class SkillsListFragment extends AbsFragmentWithSerializable<SkillsPackage>
        implements OnSkillChanged,
                   CharacterPropertySortAsyn.OnPropertiesSorted,
                   CanModifyPointPool {

    public static final String TAG = SkillsListFragment.class.getSimpleName();
    @InjectView(android.R.id.list)
    StickyListHeadersListView listView;
    @Optional
    @InjectView(R.id.points_available)
    TextView                  mPointsAvailableView;

    private int                           mCareerPoints = 0;
    private int                           mHobbyPoints  = 0;
    private Comparator<CharacterProperty> mComparator   = CharacterPropertyComparators.ACTION_GROUP;
    private SkillsAdapterSticky      mSkillsAdapter;
    private Collection<CharacterProperty>   mSkills;
    private int                      mPointsAvailable;
    private int                      mMaxPoints;
    private OnSaveSkills             mOnSaveSkills;
    private SkillsPackage            mSkillsPackage;
    private OnSkillPointsPoolChanged mPointsPoolChanged;

    public static SkillsListFragment newInstance(SkillsPackage arg) {
        SkillsListFragment r = new SkillsListFragment();
        r.setNewInstanceArgument(arg);
        return r;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof OnSaveSkills) {
            mOnSaveSkills = (OnSaveSkills) activity;
        }
        if (activity instanceof OnSkillPointsPoolChanged) {
            mPointsPoolChanged = (OnSkillPointsPoolChanged) activity;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_skill_editor, container, false);
        ButterKnife.inject(this, rootView);
        return rootView;
    }

    public Collection<CharacterProperty> getSkills() {
        return mSkills;
    }


    @Override
    protected void onInstanceArgumentRead(SkillsPackage arg) {
        if (arg != null) {
            mSkillsPackage = arg;
            setupSkillsList();
            setupPoints();
        }
    }

    private void setupPoints() {
        mPointsAvailable = mSkillsPackage.getAvailableSkillPoints();
        mMaxPoints = mSkillsPackage.getMaxPoints();
        setPointsAvailable(mPointsAvailable);
    }

    private void setPointsAvailable(int points) {
        if (mPointsPoolChanged != null) {
            mPointsPoolChanged.onSkillPointsPoolChanged(points);
        }
    }

    private void setupSkillsList() {
        mSkills = mSkillsPackage.getData();
        CharacterPropertySortAsyn sortAsyn = new CharacterPropertySortAsyn(getContext(), this,
                mSkills);
        sortAsyn.executeCrossPlatform(mComparator);
        mSkillsAdapter = new SkillsAdapterSticky(getContext(), this, this);
        listView.setAdapter(mSkillsAdapter);
        listView.setClickable(false);
    }

    @Override
    public boolean onSkillIncreased(CharacterProperty property) {
        // uping the skill depletes the point pool
        if (property != null) {
            mPointsAvailable--;
            setPointsAvailable(mPointsAvailable);
            updatesSkillsList(property);
            return true;
        }
        return false;
    }

    @Override
    public boolean onSkillDecreased(CharacterProperty property) {
        if (property != null) {
            mPointsAvailable++;
            setPointsAvailable(mPointsAvailable);
            updatesSkillsList(property);
            return true;
        }
        return false;
    }

    private void updatesSkillsList(CharacterProperty property) {
        if (property != null) {
            mSkills.remove(property);
            mSkills.add(property);
        }
    }

    @Override
    public void onCharacterPropertiesSorted(Comparator<CharacterProperty> comparator,
                                            List<CharacterProperty> sortedSet) {

        if (mSkillsAdapter != null) {
            mSkillsAdapter.refreshData(sortedSet);
        }
    }

    @Override
    public void onCharacterPropertiesSortedByGroup(Comparator<CharacterProperty> comparator,
                                                   ActionGroup actionGroup,
                                                   List<CharacterProperty> sortedSet) {
        if (mSkillsAdapter != null) {
            mSkillsAdapter.addItems(sortedSet);
        }
    }

    @Override
    public boolean canIncreasePointPool() {
        int afterTotal = mPointsAvailable + 1;
        return afterTotal <= mMaxPoints;
    }

    @Override
    public boolean canDecreasePointPool() {
        int afterTotal = mPointsAvailable - 1;
        return afterTotal >= 0;
    }
}
