package com.bustiblelemons.cthulhator.creation.characteristics.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.SparseArray;
import android.widget.Button;
import android.widget.TextView;

import com.bustiblelemons.cthulhator.R;
import com.bustiblelemons.cthulhator.activities.SkillsChooserActivity;
import com.bustiblelemons.cthulhator.creation.characteristics.logic.CharacterPropertyAdapter;
import com.bustiblelemons.cthulhator.creation.characteristics.logic.PointPoolObserver;
import com.bustiblelemons.cthulhator.creation.ui.AbsCharacterCreationActivity;
import com.bustiblelemons.cthulhator.model.CharacterProperty;
import com.bustiblelemons.cthulhator.model.CthulhuCharacter;
import com.bustiblelemons.cthulhator.model.CthulhuEdition;
import com.bustiblelemons.cthulhator.model.cache.SavedCharacter;
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
public class StatisticsCreatorActivity extends AbsCharacterCreationActivity
        implements PointPoolObserver, SkillView.SkillViewListener {

    public static final int REQUEST_CODE = 4;
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


    private PointPool pointPool = PointPool.EMPTY;
    private Set<CharacterProperty> characterProperties;
    private SavedCharacter         mSavedCharacter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onSetActionBarToClosable();
        setContentView(R.layout.activity_statistic_creator);
        ButterKnife.inject(this);
        pointPool = new PointPool();
        pointPool.register(this);
        mSavedCharacter = getInstanceArgument();
        if (mSavedCharacter == null) {
            mSavedCharacter = CthulhuCharacter.forEdition(CthulhuEdition.CoC5);
            characterProperties = mSavedCharacter.getProperties();
            onReroll(rerollButton);
        } else if (mSavedCharacter != null && mSavedCharacter.getProperties().isEmpty()) {
            characterProperties = mSavedCharacter.getProperties();
            onReroll(rerollButton);
        } else {
            characterProperties = mSavedCharacter.getProperties();
            fillPropertyViews();
        }
    }

    @OnClick(R.id.assign_skills)
    public void onOpenSkillsetEditor(Button button) {
        Intent i = new Intent(this, SkillsChooserActivity.class);
        startActivity(i);
    }


    @Override
    protected void onInstanceArgumentRead(SavedCharacter arg) {
        mSavedCharacter = arg;
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

    private void fillPropertyViews() {
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
                if (property != null) {
                    view.setSkillViewListener(this);
                    view.setMinValue(property.getMinValue());
                    view.setMaxValue(property.getMaxValue());
                    int randValue = property.getValue();
                    points += randValue;
                    view.setIntValue(randValue);
                    if (property.hasRelations()) {
                        CharacterPropertyAdapter adapter = new CharacterPropertyAdapter(this);
                        adapter.refreshData(mSavedCharacter.getRelatedProperties(property));
                        view.setAdapter(adapter);
                        idsToAdapters.put(id, adapter);
                    }
                }
            }
        }
        pointPool.setMax(points);
        pointPool.setPoints(points);
        pointPool.notifyObservers(points);
        updatePointsAvailable(points);
        log.d("Point pool %s", pointPool);
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
                if (property != null) {
                    view.setSkillViewListener(this);
                    view.setMinValue(property.getMinValue());
                    view.setMaxValue(property.getMaxValue());
                    int randValue = property.randomValue();
                    points += randValue;
                    view.setIntValue(randValue);
                    if (property.hasRelations()) {
                        CharacterPropertyAdapter adapter = new CharacterPropertyAdapter(this);
                        adapter.refreshData(mSavedCharacter.getRelatedProperties(property));
                        view.setAdapter(adapter);
                        idsToAdapters.put(id, adapter);
                    }
                }
            }
        }
        pointPool.setMax(points);
        pointPool.setPoints(points);
        pointPool.notifyObservers(points);
        updatePointsAvailable(points);
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
            increasePropertyAndUpdateView(view);
            pointPool.increase();
            return true;
        }
        return false;
    }

    @Override
    public boolean onDecreaseClicked(SkillView view) {
        if (pointPool.canDecrease() && view.canDecrease()) {
            decreasePropertyAndUpdateView(view);
            pointPool.decrease();
            return true;
        }
        return false;
    }

    private void increasePropertyAndUpdateView(SkillView view) {
        int id = view.getId();
        CharacterProperty property = idsToProperty.get(id);
        if (property != null && property.decreaseValue()) {
            updateView(view, id, property);
        }
    }

    private void decreasePropertyAndUpdateView(SkillView view) {
        int id = view.getId();
        CharacterProperty property = idsToProperty.get(id);
        if (property != null && property.increaseValue()) {
            updateView(view, id, property);
        }
    }

    private void updateView(SkillView view, int id, CharacterProperty property) {
        CharacterPropertyAdapter adapter = idsToAdapters.get(id);
        if (property != null) {
            if (adapter == null) {
                adapter = new CharacterPropertyAdapter(this);
            }
            Set<CharacterProperty> data = mSavedCharacter.getRelatedProperties(property);
            adapter.refreshData(data);
            view.setAdapter(adapter);
            idsToProperty.put(id, property);
            idsToAdapters.put(id, adapter);
        }
    }

    @Override
    public void onBackPressed() {
        setResult(RESULT_OK, mSavedCharacter);
        super.onBackPressed();
    }
}
