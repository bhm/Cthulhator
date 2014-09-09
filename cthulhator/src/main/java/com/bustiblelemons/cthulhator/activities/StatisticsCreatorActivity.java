package com.bustiblelemons.cthulhator.activities;

import android.os.Bundle;

import com.bustiblelemons.cthulhator.R;
import com.bustiblelemons.cthulhator.dice.PoolPoint;
import com.bustiblelemons.views.SkillView;

import java.util.Observable;
import java.util.Observer;

import at.markushi.ui.CircleButton;
import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by bhm on 31.08.14.
 */
public class StatisticsCreatorActivity extends AbsActivity implements Observer {

    @InjectView(R.id.reroll)
    CircleButton rerollButton;

    @InjectView(R.id.intelligence)
    SkillView intView;

    private PoolPoint poolPoint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onSetActionBarToClosable();
        setContentView(R.layout.activity_statistic_creator);
        ButterKnife.inject(this);
    }

    public boolean onReroll(CircleButton button) {

        return false;
    }

    @Override
    public void update(Observable observable, Object data) {

    }
}
