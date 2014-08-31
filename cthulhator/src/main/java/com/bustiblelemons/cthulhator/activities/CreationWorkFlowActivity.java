package com.bustiblelemons.cthulhator.activities;

import android.os.Bundle;
import android.view.View;

import com.bustiblelemons.cthulhator.R;

import butterknife.OnClick;

/**
 * Created by bhm on 29.08.14.
 */
public class CreationWorkFlowActivity extends AbsActivity
        implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creation_workflow);
    }


    @OnClick({R.id.add_character_details,
            R.id.add_character_history,
            R.id.add_character_statistics,
            R.id.add_character_equipement})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_character_details:
                break;
            case R.id.add_character_history:
                break;
            case R.id.add_character_statistics:
                launchStatisticsCreator();
                break;
            case R.id.add_character_equipement:
                break;
        }
    }


}
