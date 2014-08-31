package com.bustiblelemons.cthulhator.activities;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.bustiblelemons.cthulhator.R;
import com.bustiblelemons.cthulhator.adapters.SkillChanged;
import com.bustiblelemons.cthulhator.adapters.SkillsAdapter;
import com.bustiblelemons.cthulhator.model.CharacterProperty;
import com.bustiblelemons.cthulhator.model.brp.AbsBRPCharacter;

import java.util.Locale;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by bhm on 31.08.14.
 */
public class SkillsChooserActivity extends AbsActivity implements SkillChanged {

    public static final String CHARACTER = "character";

    @InjectView(android.R.id.list)
    ListView listView;
    private SkillsAdapter   skillsAdapter;
    private AbsBRPCharacter character;

    @InjectView(R.id.points_available)
    TextView pointsAvailable;
    private int    total;
    private String pointsAvailablePrefix;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onSetActionBarToClosable();
        setContentView(R.layout.activity_skill_chooser);
        ButterKnife.inject(this);
        if (hasExtra(CHARACTER)) {
            character = (AbsBRPCharacter) getExtras().getSerializable(CHARACTER);
        }
        setupSkillsList();
        setupPoints();
    }

    private void setupPoints() {
        if (character != null && pointsAvailable != null) {
            total = character.getTotalSkillPoints();
            setPointsAvailable(total);
        }
    }

    private void setPointsAvailable(int points) {
        pointsAvailablePrefix = getString(R.string.points_available);
        String val = String.format(Locale.ENGLISH, "%s %s", pointsAvailablePrefix, points);
        pointsAvailable.setText(val);
    }

    private void setupSkillsList() {
        if (character != null && listView != null) {
            skillsAdapter = new SkillsAdapter(this, this);
            skillsAdapter.addItems(character.getSkills());
            listView.setAdapter(skillsAdapter);
            listView.setClickable(false);
        }
    }

    @Override
    public void onSkillChanged(CharacterProperty name, int value, boolean up) {
        total = up ? total++ : total--;
        setPointsAvailable(total);
    }
}
