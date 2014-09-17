package com.bustiblelemons.cthulhator.creation.characteristics.ui;

import android.os.Bundle;
import android.util.SparseArray;
import android.widget.TextView;

import com.bustiblelemons.cthulhator.R;
import com.bustiblelemons.cthulhator.activities.AbsActivity;
import com.bustiblelemons.cthulhator.creation.characteristics.logic.CharacterPropertyAdapter;
import com.bustiblelemons.cthulhator.creation.characteristics.logic.PointPoolObserver;
import com.bustiblelemons.cthulhator.model.CharacterProperty;
import com.bustiblelemons.cthulhator.model.CthulhuCharacter;
import com.bustiblelemons.cthulhator.model.CthulhuEdition;
import com.bustiblelemons.cthulhator.model.Relation;
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

    @InjectViews({R.id.edu,
            R.id.intelligence,
            R.id.pow,
            R.id.dex,
            R.id.app,
            R.id.str, R.id.con, R.id.siz})
    List<SkillView> characteristicsViewList;

    @InjectView(R.id.points_available)
    TextView pointsAvailable;

    private SparseArray<CharacterProperty>        idsToProperty = new SparseArray<CharacterProperty>();
    private SparseArray<CharacterPropertyAdapter> idsToAdapters =
            new SparseArray<CharacterPropertyAdapter>();


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
        pointPool = new PointPool();
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
                int id = view.getId();
                idsToProperty.put(id, property);
                property.getRelations();
                if (property != null) {
                    view.setSkillViewListener(this);
                    view.setMinValue(property.getMinValue());
                    view.setMaxValue(property.getMaxValue());
                    int randValue = property.randomValue();
                    points += randValue;
                    view.setIntValue(randValue);
                    log.d("Property has relations %s", property.hasRelations());
                    if (property.hasRelations()) {
                        Set<Relation> relations = property.getRelations();
                        CharacterPropertyAdapter adapter = new CharacterPropertyAdapter(this);
                        adapter.refreshData(savedCharacter.getPropertiesByRelations(relations));
                        view.setAdapter(adapter);
                        idsToAdapters.put(id, adapter);
                    }
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
            updateSkillViewAdapter(view);
            pointPool.decrease();
            return true;
        }
        return false;
    }

    private void updateSkillViewAdapter(SkillView view) {
        int id = view.getId();
        CharacterProperty property = idsToProperty.get(id);
        CharacterPropertyAdapter adapter = idsToAdapters.get(id);
        if (property != null) {
            if (adapter == null) {
                adapter = new CharacterPropertyAdapter(this);
            }
            Set<Relation> relations = property.getRelations();
            Set<CharacterProperty> data = savedCharacter.getPropertiesByRelations(relations);
            adapter.refreshData(data);
            view.setAdapter(adapter);
            idsToProperty.put(id, property);
            idsToAdapters.put(id, adapter);
        }
    }
}
