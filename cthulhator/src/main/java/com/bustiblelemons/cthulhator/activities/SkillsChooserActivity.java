package com.bustiblelemons.cthulhator.activities;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.bustiblelemons.cthulhator.R;
import com.bustiblelemons.cthulhator.adapters.SkillChanged;
import com.bustiblelemons.cthulhator.adapters.SkillsAdapter;
import com.bustiblelemons.cthulhator.creation.ui.AbsCharacterCreationActivity;
import com.bustiblelemons.cthulhator.model.CharacterProperty;
import com.bustiblelemons.cthulhator.model.cache.SavedCharacter;

import java.util.Locale;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by bhm on 31.08.14.
 */
public class SkillsChooserActivity extends AbsCharacterCreationActivity implements SkillChanged {

    public static final String CHARACTER = "character";

    @InjectView(android.R.id.list)
    ListView listView;
    @InjectView(R.id.points_available)
    TextView pointsAvailable;
    private SkillsAdapter  skillsAdapter;
    private int            total;
    private String         pointsAvailablePrefix;
    private SavedCharacter mSavedCharacter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onSetActionBarToClosable();
        setContentView(R.layout.activity_skill_chooser);
        ButterKnife.inject(this);
        setupSkillsList();
        setupPoints();
    }

    @Override
    protected void onInstanceArgumentRead(SavedCharacter arg) {
        mSavedCharacter = arg;
    }

    private void setupPoints() {
    }

    private void setPointsAvailable(int points) {
        pointsAvailablePrefix = getString(R.string.points_available);
        String val = String.format(Locale.ENGLISH, "%s %s", pointsAvailablePrefix, points);
        pointsAvailable.setText(val);
    }

    private void setupSkillsList() {
        skillsAdapter = new SkillsAdapter(this, this);
        skillsAdapter.addItems(mSavedCharacter.getTopSkills());
        listView.setAdapter(skillsAdapter);
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
}
