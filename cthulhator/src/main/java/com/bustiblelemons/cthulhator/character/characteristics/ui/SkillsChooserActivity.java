package com.bustiblelemons.cthulhator.character.characteristics.ui;

import android.os.Bundle;
import android.widget.TextView;

import com.bustiblelemons.cthulhator.R;
import com.bustiblelemons.cthulhator.adapters.SkillChanged;
import com.bustiblelemons.cthulhator.character.characteristics.logic.CharacterPropertyComparators;
import com.bustiblelemons.cthulhator.character.characteristics.logic.CharacterPropertySortAsyn;
import com.bustiblelemons.cthulhator.character.characteristics.logic.SkillsAdapterSticky;
import com.bustiblelemons.cthulhator.character.characterslist.model.SavedCharacter;
import com.bustiblelemons.cthulhator.character.creation.ui.AbsCharacterCreationActivity;
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
 * Created by bhm on 31.08.14.
 */
public class SkillsChooserActivity extends AbsCharacterCreationActivity
        implements SkillChanged, CharacterPropertySortAsyn.OnPropertiesSorted {

    public static final String CHARACTER    = "character";
    public static final int    REQUEST_CODE = 6;

    @InjectView(android.R.id.list)
    StickyListHeadersListView listView;
    @InjectView(R.id.points_available)
    TextView                  pointsAvailable;
    //    private SkillsAdapter       mSkillsAdapter;
    private SkillsAdapterSticky    mSkillsAdapterSticky;
    private int                    total;
    private String                 pointsAvailablePrefix;
    private SavedCharacter         mSavedCharacter;
    private Set<CharacterProperty> mSkills;
    private Comparator<CharacterProperty> mComparator = CharacterPropertyComparators.VALUE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onSetActionBarToClosable();
        setContentView(R.layout.activity_skill_chooser);
        ButterKnife.inject(this);
        mSavedCharacter = getInstanceArgument();
        setupSkillsList();
    }

    @Override
    protected void onInstanceArgumentRead(SavedCharacter arg) {
        mSavedCharacter = arg;
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
        mSkillsAdapterSticky = new SkillsAdapterSticky(this, this);
        listView.setAdapter(mSkillsAdapterSticky);
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
            mSkillsAdapterSticky.removeAll();
            mSkillsAdapterSticky.addItems(sortedSet);
        }
    }
}
