package com.bustiblelemons.cthulhator.character.skills.ui;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;

import com.bustiblelemons.cthulhator.R;
import com.bustiblelemons.cthulhator.character.characterslist.model.SavedCharacter;
import com.bustiblelemons.cthulhator.character.creation.ui.AbsCharacterCreationActivity;

import butterknife.ButterKnife;

/**
 * Created by bhm on 31.08.14.
 */
public class SkillsChooserActivity extends AbsCharacterCreationActivity {

    public static final int REQUEST_CODE = 6;
    private SavedCharacter     mSavedCharacter;
    private SkillsListFragment mSkillEditorFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_skill_chooser);
        ButterKnife.inject(this);
        mSavedCharacter = getInstanceArgument();
        attachSkillEditor();
    }

    private void attachSkillEditor() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        mSkillEditorFragment = SkillsListFragment.newInstance(mSavedCharacter);
        transaction.replace(R.id.skill_editor_frame, mSkillEditorFragment, SkillsListFragment.TAG);
        transaction.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right,
                R.anim.slide_in_left, R.anim.slide_out_right);
        transaction.commit();
    }

    private void detachSkillEditor() {
        FragmentTransaction t = getSupportFragmentManager().beginTransaction();
        t.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right,
                R.anim.slide_in_left, R.anim.slide_out_right);
        t.detach(mSkillEditorFragment).commitAllowingStateLoss();
    }

    @Override
    protected void onInstanceArgumentRead(SavedCharacter arg) {
        mSavedCharacter = arg;
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

}
