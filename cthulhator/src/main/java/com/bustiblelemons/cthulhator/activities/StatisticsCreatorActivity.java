package com.bustiblelemons.cthulhator.activities;

import android.os.Bundle;
import android.widget.TextView;

import com.bustiblelemons.cthulhator.R;
import com.bustiblelemons.cthulhator.model.CthulhuEdition;
import com.bustiblelemons.cthulhator.model.dice.PointPool;
import com.bustiblelemons.cthulhator.model.dice.PoitPoolFactory;
import com.bustiblelemons.views.SkillView;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

import at.markushi.ui.CircleButton;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.InjectViews;
import butterknife.OnClick;

/**
 * Created by bhm on 31.08.14.
 */
public class StatisticsCreatorActivity extends AbsActivity implements Observer {

    @InjectView(R.id.reroll)
    CircleButton rerollButton;

    @InjectViews({R.id.edu, R.id.intelligence, R.id.pow, R.id.str, R.id.con, R.id.dex})
    List<SkillView> characteristicsViewList;

    @InjectView(R.id.points_available)
    TextView pointsAvailable;

    private PointPool pointPool;
    private CthulhuEdition edition = CthulhuEdition.CoC5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onSetActionBarToClosable();
        setContentView(R.layout.activity_statistic_creator);
        ButterKnife.inject(this);
        onReroll(rerollButton);
    }

    @OnClick(R.id.reroll)
    public void onReroll(CircleButton button) {
        if (edition != null) {
            pointPool = PoitPoolFactory.randomPoolFromEdition(edition);
            int available = pointPool.getPoints();
            String format = getString(R.string.points_available_format, available);
            log.d("onReroll %s", pointPool);
            pointsAvailable.setText(format);
        }
    }

    @Override
    public void update(Observable observable, Object data) {

    }
}
