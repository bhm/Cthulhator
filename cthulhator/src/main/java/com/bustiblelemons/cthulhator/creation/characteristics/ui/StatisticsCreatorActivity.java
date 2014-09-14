package com.bustiblelemons.cthulhator.creation.characteristics.ui;

import android.os.Bundle;
import android.widget.TextView;

import com.bustiblelemons.cthulhator.R;
import com.bustiblelemons.cthulhator.activities.AbsActivity;
import com.bustiblelemons.cthulhator.creation.characteristics.logic.PointPoolObserver;
import com.bustiblelemons.cthulhator.model.CharacterProperty;
import com.bustiblelemons.cthulhator.model.CthulhuEdition;
import com.bustiblelemons.cthulhator.model.dice.PointPool;
import com.bustiblelemons.cthulhator.model.dice.PoitPoolFactory;
import com.bustiblelemons.views.SkillView;

import java.util.List;

import at.markushi.ui.CircleButton;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.InjectViews;
import butterknife.OnClick;

/**
 * Created by bhm on 31.08.14.
 */
public class StatisticsCreatorActivity extends AbsActivity implements PointPoolObserver {

    @InjectView(R.id.reroll)
    CircleButton rerollButton;

    @InjectViews({R.id.edu, R.id.intelligence, R.id.pow, R.id.str, R.id.con, R.id.dex})
    List<SkillView> characteristicsViewList;

    @InjectView(R.id.points_available)
    TextView pointsAvailable;

    private PointPool pointPool;
    private CthulhuEdition          edition             = CthulhuEdition.CoC5;
    private List<CharacterProperty> characterProperties = edition.getCharacteristics();

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
            pointPool = PoitPoolFactory.randomPoolFromCharacterPropertyList(characterProperties);
            int available = pointPool.getPoints();
            updatePointsAvailable(available);
        }
        distributePoints();
    }

    private void updatePointsAvailable(int available) {
        if (pointsAvailable != null) {
            String format = getString(R.string.points_available_format, available);
            log.d("onReroll %s", pointPool);
            pointsAvailable.setText(format);
        }
    }

    private void distributePoints() {
        if (characteristicsViewList == null) {
            return;
        }
        while (pointPool.hasPoints()) {
            for (SkillView view : characteristicsViewList) {
                if (view != null && view.getTag() != null) {
                    String tag = (String) view.getTag();
                    CharacterProperty property = getProperty(tag);
                    int randValue = 1;
                    if (property != null) {
                        int min = property.getMinValue();
                        int max = property.getMaxValue();
                        randValue = pointPool.decreaseByRandom(min, max);
                    } else {
                        randValue = pointPool.decreaseByRandom();
                    }
                    view.setIntValue(randValue);
                }
            }
        }
    }

    private CharacterProperty getProperty(String name) {
        for (CharacterProperty property : characterProperties) {
            if (property.getName() != null && property.getName().equals(name)) {
                return property;
            }
        }
        return null;
    }

    @Override
    public void update() {

    }

    @Override
    public void update(Integer d) {
        if (d != null) {
            updatePointsAvailable(d.intValue());
        }
    }
}
