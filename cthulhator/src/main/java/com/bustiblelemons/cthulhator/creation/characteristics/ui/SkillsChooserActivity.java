package com.bustiblelemons.cthulhator.creation.characteristics.ui;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.bustiblelemons.cthulhator.R;
import com.bustiblelemons.cthulhator.adapters.SkillChanged;
import com.bustiblelemons.cthulhator.adapters.SkillsAdapter;
import com.bustiblelemons.cthulhator.creation.characteristics.logic.CharacterPropertyComparators;
import com.bustiblelemons.cthulhator.creation.characteristics.logic.CharacterPropertySortAsyn;
import com.bustiblelemons.cthulhator.creation.ui.AbsCharacterCreationActivity;
import com.bustiblelemons.cthulhator.model.CharacterProperty;
import com.bustiblelemons.cthulhator.model.cache.SavedCharacter;

import java.util.Comparator;
import java.util.Locale;
import java.util.Set;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by bhm on 31.08.14.
 */
public class SkillsChooserActivity extends AbsCharacterCreationActivity
        implements SkillChanged, CharacterPropertySortAsyn.OnPropertiesSorted {

    public static final String CHARACTER    = "character";
    public static final int    REQUEST_CODE = 6;

    @InjectView(android.R.id.list)
    ListView listView;
    @InjectView(R.id.points_available)
    TextView pointsAvailable;
    private SkillsAdapter mSkillsAdapter;
    private int            total;
    private String         pointsAvailablePrefix;
    private SavedCharacter mSavedCharacter;
    private int mCareerPoints = 0;
    private int mHobbyPoints  = 0;
    private Set<CharacterProperty> mSkills;
    private Comparator<CharacterProperty> mComparator = CharacterPropertyComparators.ALPHABETICAL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onSetActionBarToClosable();
        setContentView(R.layout.activity_skill_chooser);
        ButterKnife.inject(this);
        mSavedCharacter = getInstanceArgument();
        setupSkillsList();
        setupPoints();
    }

    @Override
    protected void onInstanceArgumentRead(SavedCharacter arg) {
        mSavedCharacter = arg;
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
        CharacterPropertySortAsyn sortAsyn = new CharacterPropertySortAsyn(this, this, mSkills);
        sortAsyn.executeCrossPlatform(mComparator);
        mSkillsAdapter = new SkillsAdapter(this, this);
        listView.setAdapter(mSkillsAdapter);
        listView.setClickable(false);
    }

    @Override
    public void onBackPressed() {
        overridePendingTransition(R.anim.abc_slide_in_top, R.anim.abc_slide_out_bottom);
        super.onBackPressed();
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
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.abc_slide_in_bottom, R.anim.abc_slide_out_bottom);
    }

    @Override
    public void onCharacterPropertiesSorted(Comparator<CharacterProperty> comparator,
                                            Set<CharacterProperty> sortedSet) {

        if (mSkillsAdapter != null) {
            mSkillsAdapter.refreshData(sortedSet);
        }
    }
}
