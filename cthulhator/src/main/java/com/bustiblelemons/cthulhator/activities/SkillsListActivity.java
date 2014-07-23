package com.bustiblelemons.cthulhator.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;

import com.bustiblelemons.activities.BaseActionBarActivity;
import com.bustiblelemons.cthulhator.R;
import com.bustiblelemons.cthulhator.fragments.SkillsListFragment;

/**
 * Created by bhm on 20.07.14.
 */
public class SkillsListActivity extends BaseActionBarActivity {

    private SkillsListFragment skillsListFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_skill_list);
        attachSkillList();
    }

    private void attachSkillList() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (skillsListFragment == null) {
            skillsListFragment = SkillsListFragment.newInstance(SkillsListFragment.ALPHABETICAL);
        }
        transaction.replace(android.R.id.content, skillsListFragment);
    }
}
