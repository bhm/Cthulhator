package com.bustiblelemons.cthulhator.activities;

import android.os.Bundle;

import com.bustiblelemons.cthulhator.R;

/**
 * Created by bhm on 31.08.14.
 */
public class StatisticsCreatorActivity extends AbsActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onSetActionBarToClosable();
        setContentView(R.layout.activity_statistic_creator);
    }
}
