package com.bustiblelemons.cthulhator.activities;

import android.content.Intent;

import com.bustiblelemons.activities.BaseActionBarActivity;
import com.bustiblelemons.cthulhator.creation.ui.CreationWorkFlowActivity;

/**
 * Created by bhm on 31.08.14.
 */
public abstract class AbsActivity extends BaseActionBarActivity {
    protected void launchStatisticsCreator() {
        Intent i = new Intent(this, StatisticsCreatorActivity.class);
        startActivity(i);
    }

    protected void launchRandomCharacter() {
        Intent i = new Intent(this, RandomCharactersActivity.class);
        startActivity(i);
    }

    protected void launchCreationWorkflow() {
        Intent i = new Intent(this, CreationWorkFlowActivity.class);
        startActivity(i);
    }

}
