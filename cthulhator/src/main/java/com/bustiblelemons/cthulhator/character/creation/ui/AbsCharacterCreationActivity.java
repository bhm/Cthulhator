package com.bustiblelemons.cthulhator.character.creation.ui;

import android.content.Intent;
import android.os.Bundle;

import com.bustiblelemons.activities.AbsArgActivity;
import com.bustiblelemons.cthulhator.R;
import com.bustiblelemons.cthulhator.character.characteristics.ui.StatisticsCreatorActivity;
import com.bustiblelemons.cthulhator.character.characterslist.model.SavedCharacter;
import com.bustiblelemons.cthulhator.character.description.ui.RandomCharactersActivity;
import com.bustiblelemons.cthulhator.character.history.ui.HistoryEditorActivity;
import com.bustiblelemons.cthulhator.character.skills.ui.SkillsChooserActivity;

/**
 * Created by bhm on 31.08.14.
 */
public abstract class AbsCharacterCreationActivity extends AbsArgActivity<SavedCharacter> {

    @Override
    public int getBackResIconId() {
        return R.drawable.ic_action_navigation_back;
    }

    protected void launchStatisticsCreator(SavedCharacter character) {
        Intent i = new Intent(this, StatisticsCreatorActivity.class);
        launchCreationActivity(i, StatisticsCreatorActivity.REQUEST_CODE, character);
    }

    protected void launchHistoryEditor(SavedCharacter character) {
        Intent i = new Intent(this, HistoryEditorActivity.class);
        launchCreationActivity(i, HistoryEditorActivity.REQUEST_CODE, character);
    }

    protected void launchRandomCharacter(SavedCharacter character) {
        Intent i = new Intent(this, RandomCharactersActivity.class);
        launchCreationActivity(i, RandomCharactersActivity.REQUEST_CODE, character);
    }

    protected void launchSkillsetEditor(SavedCharacter character) {
        Intent i = new Intent(this, SkillsChooserActivity.class);
        launchCreationActivity(i, SkillsChooserActivity.REQUEST_CODE, character);
        overridePendingTransition(R.anim.abc_slide_in_bottom, R.anim.abc_slide_out_bottom);
    }

    protected void launchCreationActivity(Intent intent, int code, SavedCharacter character) {
        Bundle opts = new Bundle();
        opts.putParcelable(AbsArgActivity.INSTANCE_ARGUMENT, character);
        intent.putExtras(opts);
        startActivityForResult(intent, code);
    }
}
