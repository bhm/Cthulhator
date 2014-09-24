package com.bustiblelemons.cthulhator.creation.ui;

import android.content.Intent;
import android.os.Bundle;

import com.bustiblelemons.activities.AbsArgActivity;
import com.bustiblelemons.cthulhator.creation.characteristics.ui.StatisticsCreatorActivity;
import com.bustiblelemons.cthulhator.creation.description.ui.RandomCharactersActivity;
import com.bustiblelemons.cthulhator.creation.history.ui.HistoryEditorActivity;
import com.bustiblelemons.cthulhator.model.cache.SavedCharacter;

/**
 * Created by bhm on 31.08.14.
 */
public abstract class AbsCharacterCreationActivity extends AbsArgActivity<SavedCharacter> {

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

    protected void launchCreationActivity(Intent intent, int code, SavedCharacter character) {
        Bundle opts = new Bundle();
        opts.putParcelable(AbsArgActivity.INSTANCE_ARGUMENT, character);
        intent.putExtras(opts);
        startActivityForResult(intent, code);
    }


}
