package com.bustiblelemons.cthulhator.character.skills.ui;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.bustiblelemons.cthulhator.R;
import com.bustiblelemons.cthulhator.character.creation.ui.AbsCharacterCreationActivity;
import com.bustiblelemons.cthulhator.character.persistance.CharacterWrapper;
import com.bustiblelemons.cthulhator.character.skills.logic.OnSaveSkills;
import com.bustiblelemons.cthulhator.character.skills.logic.OnSkillPointsPoolChanged;
import com.bustiblelemons.cthulhator.character.skills.model.SkillsPackage;
import com.bustiblelemons.cthulhator.system.properties.CharacterProperty;

import java.util.Collection;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import butterknife.Optional;

/**
 * Created by bhm on 31.08.14.
 */
public class SkillsChooserActivity extends AbsCharacterCreationActivity
        implements OnSkillPointsPoolChanged,
                   OnSaveSkills {

    public static final int REQUEST_CODE = 6;
    @InjectView(R.id.header)
    Toolbar mToolbar;
    private CharacterWrapper   mSavedCharacter;
    private SkillsListFragment mSkillEditorFragment;
    private String mPoolPrefix = "";
    private int mPointsAvailable;

    @Optional
    @InjectView(android.R.id.list)
    RecyclerView mRecyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_skill_chooser);
        ButterKnife.inject(this);
        if (mRecyclerView != null) {
            mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        }
        if (mToolbar != null) {
            setSupportActionBar(mToolbar);
        }
        mSavedCharacter = getInstanceArgument();
        attachSkillEditor();
    }

    private void attachSkillEditor() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        SkillsPackage skillsPackage = getSkillsPackage();
        mSkillEditorFragment = SkillsListFragment.newInstance(skillsPackage);
        transaction.replace(R.id.skill_editor_frame, mSkillEditorFragment, SkillsListFragment.TAG);
        transaction.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right,
                R.anim.slide_in_left, R.anim.slide_out_right);
        transaction.commit();
    }

    private SkillsPackage getSkillsPackage() {
        SkillsPackage skillsPackage = new SkillsPackage();
        skillsPackage.setData(mSavedCharacter.getSkills());
        skillsPackage.setHobbyPoints(mSavedCharacter.getHobbyPoints());
        skillsPackage.setCareerPoints(mSavedCharacter.getCareerPoints());
        skillsPackage.setAvailableSkillPoints(mSavedCharacter.getSkillPointsAvailable());
        return skillsPackage;
    }


    @Override
    protected void onInstanceArgumentRead(CharacterWrapper arg) {
        mSavedCharacter = arg;
    }

    @OnClick(R.id.done)
    public void onDone() {
        if (mSavedCharacter != null) {
            if (mSkillEditorFragment != null) {
                mSavedCharacter.setPropertyValues(mSkillEditorFragment.getSkills());
            }
            mSavedCharacter.setSkillPointsAvailable(mPointsAvailable);
        }
        setResult(RESULT_OK, mSavedCharacter);
        onBackPressed();
    }

    private void detachSkillEditor() {
        FragmentTransaction t = getSupportFragmentManager().beginTransaction();
        t.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right,
                R.anim.slide_in_left, R.anim.slide_out_right);
        t.detach(mSkillEditorFragment).commitAllowingStateLoss();
    }


    @Override
    public void onBackPressed() {
        overridePendingTransition(R.anim.abc_slide_in_top, R.anim.abc_slide_out_bottom);
        super.onBackPressed();
    }


    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.abc_slide_in_bottom, R.anim.abc_slide_out_bottom);
    }

    @Override
    public void onSaveSkills(Collection<CharacterProperty> skills) {
        if (mSavedCharacter != null) {
            mSavedCharacter.setPropertyValues(skills);
        }
    }

    @Override
    public void onSkillPointsPoolChanged(int pointsAvailable) {
        if (mToolbar != null) {
            mPointsAvailable = pointsAvailable;
            mToolbar.setSubtitle(mPoolPrefix + mPointsAvailable);
        }
    }
}
