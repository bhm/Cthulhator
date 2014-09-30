package com.bustiblelemons.cthulhator.creation.characteristics.ui;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.util.SparseArray;
import android.view.View;

import com.bustiblelemons.cthulhator.R;
import com.bustiblelemons.cthulhator.creation.characteristics.logic.CharacterPropertyAdapter;
import com.bustiblelemons.cthulhator.creation.ui.AbsCharacterCreationActivity;
import com.bustiblelemons.cthulhator.fragments.SkillsListFragment;
import com.bustiblelemons.cthulhator.model.CharacterProperty;
import com.bustiblelemons.cthulhator.model.CthulhuCharacter;
import com.bustiblelemons.cthulhator.model.CthulhuEdition;
import com.bustiblelemons.cthulhator.model.cache.SavedCharacter;
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
        implements SkillView.SkillViewListener {

    public static final int REQUEST_CODE = 4;
    @InjectView(R.id.assign_skills)
    CircleButton rerollButton;

    @InjectViews({R.id.edu,
            R.id.intelligence,
            R.id.pow,
            R.id.dex,
            R.id.app,
            R.id.str, R.id.con, R.id.siz})
    List<SkillView> characteristicsViewList;

    private SparseArray<CharacterProperty>        idsToProperty = new SparseArray<CharacterProperty>();
    private SparseArray<CharacterPropertyAdapter> idsToAdapters = new SparseArray<CharacterPropertyAdapter>();


    private Set<CharacterProperty> characterProperties;
    private SavedCharacter         mSavedCharacter;
    private SkillsListFragment mSkillEditorFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onSetActionBarToClosable();
        setContentView(R.layout.activity_statistic_creator);
        ButterKnife.inject(this);
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
    public void onOpenSkillsetEditor(View button) {
        attachSkillEditor();
//        launchSkillsetEditor(mSavedCharacter);
    }

    private void attachSkillEditor() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        mSkillEditorFragment = SkillsListFragment.newInstance(mSavedCharacter);
        transaction.replace(R.id.skill_editor_frame, mSkillEditorFragment, SkillsListFragment.TAG);
        transaction.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right,
                R.anim.slide_in_left, R.anim.slide_out_right);
        transaction.commit();
    }


    @Override
    protected void onInstanceArgumentRead(SavedCharacter arg) {
        mSavedCharacter = arg;
    }

    @OnClick(R.id.reroll)
    public void onReroll(View button) {
        distributePoints();
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
                if (property != null) {
                    idsToProperty.put(id, property);
                    view.setSkillViewListener(this);
                    view.setMinValue(property.getMinValue());
                    view.setMaxValue(property.getMaxValue());
                    int randValue = property.getValue();
                    points += randValue;
                    view.setIntValue(randValue);
                    if (property.hasRelations()) {
                        CharacterPropertyAdapter adapter;
                        if (idsToAdapters.get(id) != null) {
                            adapter = idsToAdapters.get(id);
                        } else {
                            adapter = new CharacterPropertyAdapter(this);
                            idsToAdapters.put(id, adapter);
                        }
                        adapter.refreshData(mSavedCharacter.getRelatedProperties(property));
                        view.setAdapter(adapter);
                    }
                }
            }
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
                if (property != null) {
                    idsToProperty.put(id, property);
                    view.setSkillViewListener(this);
                    view.setMinValue(property.getMinValue());
                    view.setMaxValue(property.getMaxValue());
                    int randValue = property.randomValue();
                    points += randValue;
                    view.setIntValue(randValue);
                    if (property.hasRelations()) {
                        CharacterPropertyAdapter adapter;
                        if (idsToAdapters.get(id) != null) {
                            adapter = idsToAdapters.get(id);
                        } else {
                            adapter = new CharacterPropertyAdapter(this);
                            idsToAdapters.put(id, adapter);
                        }
                        adapter.refreshData(mSavedCharacter.getRelatedProperties(property));
                        view.setAdapter(adapter);
                        idsToAdapters.put(id, adapter);
                    }
                }
            }
        }
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
    public void onSkillValueClick(SkillView view) {

    }

    @Override
    public void onSkillTitleClick(SkillView view) {

    }

    @Override
    public boolean onIncreaseClicked(SkillView view) {
        if (view.canIncrease()) {
            increasePropertyAndUpdateView(view);
            return true;
        }
        return false;
    }

    @Override
    public boolean onDecreaseClicked(SkillView view) {
        if (view.canDecrease()) {
            decreasePropertyAndUpdateView(view);
            return true;
        }
        return false;
    }

    private void increasePropertyAndUpdateView(SkillView view) {
        int id = view.getId();
        CharacterProperty property = idsToProperty.get(id);
        if (property != null && property.increaseValue()) {
            view.setIntValue(property.getValue());
            updateView(view, id, property);
        }
    }

    private void decreasePropertyAndUpdateView(SkillView view) {
        int id = view.getId();
        CharacterProperty property = idsToProperty.get(id);
        if (property != null && property.decreaseValue()) {
            view.setIntValue(property.getValue());
            updateView(view, id, property);
        }
    }

    private void updateView(SkillView view, int id, CharacterProperty property) {
        CharacterPropertyAdapter adapter = idsToAdapters.get(id);
        if (adapter == null) {
            adapter = new CharacterPropertyAdapter(this);
        }
        Set<CharacterProperty> data = mSavedCharacter.getRelatedProperties(property);
        adapter.refreshData(data);
        view.setAdapter(adapter);
        idsToProperty.put(id, property);
        idsToAdapters.put(id, adapter);
    }

    @Override
    public void onBackPressed() {
        if (mSkillEditorFragment != null && mSkillEditorFragment.isVisible()) {
            detachSkillEditor();
        } else {
            setResult(RESULT_OK, mSavedCharacter);
            super.onBackPressed();
        }
    }

    private void detachSkillEditor() {
        FragmentTransaction t = getSupportFragmentManager().beginTransaction();
        t.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right,
                R.anim.slide_in_left, R.anim.slide_out_right);
        t.detach(mSkillEditorFragment).commitAllowingStateLoss();
    }
}
