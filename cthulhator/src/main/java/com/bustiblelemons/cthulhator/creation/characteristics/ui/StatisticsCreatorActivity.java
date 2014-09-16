package com.bustiblelemons.cthulhator.creation.characteristics.ui;

import android.os.Bundle;
import android.widget.TextView;

import com.bustiblelemons.cthulhator.R;
import com.bustiblelemons.cthulhator.activities.AbsActivity;
import com.bustiblelemons.cthulhator.creation.characteristics.logic.PointPoolObserver;
import com.bustiblelemons.cthulhator.model.CharacterProperty;
import com.bustiblelemons.cthulhator.model.CthulhuCharacter;
import com.bustiblelemons.cthulhator.model.CthulhuEdition;
import com.bustiblelemons.cthulhator.model.dice.PointPool;
import com.bustiblelemons.views.SkillView;

import java.util.List;
import java.util.Set;

import at.markushi.ui.CircleButton;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.InjectViews;
import butterknife.OnClick;

/**
 * Created by bhm on 31.08.14.
 */
public class StatisticsCreatorActivity extends AbsActivity
        implements PointPoolObserver, SkillView.SkillViewListener {

    @InjectView(R.id.reroll)
    CircleButton rerollButton;

    @InjectViews({R.id.edu, R.id.intelligence, R.id.pow, R.id.str, R.id.con, R.id.dex, R.id.app})
    List<SkillView> characteristicsViewList;

    @InjectView(R.id.points_available)
    TextView pointsAvailable;

    private PointPool              pointPool           = PointPool.EMPTY;
    private CthulhuEdition         edition             = CthulhuEdition.CoC5;
    private CthulhuCharacter       savedCharacter      = CthulhuCharacter.forEdition(edition);
    private Set<CharacterProperty> characterProperties = savedCharacter.getStatistics();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onSetActionBarToClosable();
        setContentView(R.layout.activity_statistic_creator);
        ButterKnife.inject(this);
        pointPool.register(this);
        onReroll(rerollButton);
    }

    @OnClick(R.id.reroll)
    public void onReroll(CircleButton button) {
        distributePoints();
    }

    private void updatePointsAvailable(int available) {
        if (pointsAvailable != null) {
            String format = getString(R.string.points_available_format, available);
            pointsAvailable.setText(format);
        }
    }

    private void distributePoints() {
        if (characteristicsViewList == null) {
            return;
        }
        int points = 0;
        for (SkillView view : characteristicsViewList) {
            if (view != null && view.getTag() != null) {
                String tag = (String) view.getTag();
                CharacterProperty property = getProperty(tag);
                property.getRelations();
                if (property != null) {
                    view.setSkillViewListener(this);
                    view.setMinValue(property.getMinValue());
                    view.setMaxValue(property.getMaxValue());
                    int randValue = property.randomValue();
                    points += randValue;
                    view.setIntValue(randValue);
                }
            }
        }
        pointPool.setMax(points);
        pointPool.setPoints(points);
        pointPool.notifyObservers(points);
        log.d("Point pool %s", pointPool);
    }

    private CharacterProperty getProperty(String name) {
        for (CharacterProperty property : characterProperties) {
            if (property.getName() != null && property.getName().equals(name)) {
                log.d("%s vs %s", name, property.getName());
                return property;
            }
        }
        return null;
    }

    @Override
    public void update() {
        updatePointsAvailable(pointPool.getPoints());
    }

    @Override
    public void update(Integer d) {
        if (d != null) {
            updatePointsAvailable(d.intValue());
        }
    }

    @Override
    public void onSkillValueClick(SkillView view) {

    }

    @Override
    public void onSkillTitleClick(SkillView view) {

    }

    @Override
    public boolean onIncreaseClicked(SkillView view) {
        if (pointPool.canIncrease() && view.canIncrease()) {
            pointPool.increase();
            return true;
        }
        return false;
    }

    @Override
    public boolean onDecreaseClicked(SkillView view) {
        if (pointPool.canDecrease() && view.canDecrease()) {
            pointPool.decrease();
            return true;
        }
        return false;
    }
}
