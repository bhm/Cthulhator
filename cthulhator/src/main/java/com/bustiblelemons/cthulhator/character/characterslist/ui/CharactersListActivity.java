package com.bustiblelemons.cthulhator.character.characterslist.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;

import com.bustiblelemons.activities.AbsActionBarActivity;
import com.bustiblelemons.cthulhator.R;
import com.bustiblelemons.cthulhator.activities.CharacterViewerActivity;
import com.bustiblelemons.cthulhator.character.characterslist.logic.CharacterCache;
import com.bustiblelemons.cthulhator.character.characterslist.logic.OnOpenSavedCharacter;
import com.bustiblelemons.cthulhator.character.characterslist.logic.SavedCharactersAdapter;
import com.bustiblelemons.cthulhator.character.characterslist.model.SavedCharacter;
import com.bustiblelemons.cthulhator.character.creation.ui.CreationWorkFlowActivity;
import com.bustiblelemons.cthulhator.system.Grouping;
import com.bustiblelemons.cthulhator.view.charactercard.CharacterInfo;
import com.bustiblelemons.views.LoadMoreListView;
import com.manuelpeinado.fadingactionbar.extras.actionbarcompat.FadingActionBarHelper;

import java.util.List;

import at.markushi.ui.CircleButton;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import butterknife.Optional;

/**
 * Created by bhm on 13.08.14.
 */
public class CharactersListActivity extends AbsActionBarActivity
        implements View.OnClickListener,
                   CharacterCache.OnCharactersInfoLoaded,
                   LoadMoreListView.OnLoadMoreListener,
                   SearchView.OnQueryTextListener,
                   OnOpenSavedCharacter,
                   CharacterCache.OnRetreiveCharacter {

    @InjectView(R.id.list)
    LoadMoreListView listView;
    @InjectView(R.id.add_character)
    CircleButton     addFab;
    @Optional
    @InjectView(android.R.id.progress)
    ProgressBar      progressBar;

    private SavedCharactersAdapter listAdapter;
    private Grouping               grouping;
    private Toolbar mToolbar;

    @Override
    public int getBackResIconId() {
        return R.drawable.ic_action_navigation_back;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_characters_list);
        mToolbar = (Toolbar) findViewById(R.id.header);
        if (mToolbar != null) {
            mToolbar.setNavigationOnClickListener(this);
            setSupportActionBar(mToolbar);
        }
        ButterKnife.inject(this);
        if (listView != null) {
            listView.setOnLoadMoreListener(this);
            listView.setAdapter(listAdapter);
            listView.setOnItemClickListener(listAdapter);
        }
    }

    private void loadFresh() {
        if (listAdapter != null) {
            listAdapter.removeAll();
        }
        showProgressBar();
        grouping = new Grouping();
        CharacterCache.loadSavedCharactersAsync(this, grouping);
    }

    private void showProgressBar() {
        if (progressBar != null) {
            progressBar.setVisibility(View.VISIBLE);
        }
    }

    private FadingActionBarHelper setupFadingBar() {
        FadingActionBarHelper helper = new FadingActionBarHelper()
                .actionBarBackground(R.drawable.actionbar_brp)
                .headerLayout(R.layout.header_characters_list)
                .headerOverlayLayout(R.layout.header_characters_list_overlay)
                .contentLayout(R.layout.activity_characters_list)
                .parallax(false)
                .lightActionBar(false);
        return helper;
    }

    @Override
    protected void onStart() {
        super.onStart();
        loadFresh();
    }

    @OnClick(R.id.add_character)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        case R.id.add_character:
            launchCreationWorkflow();
            break;
        }
    }

    protected void launchCreationWorkflow() {
        Intent i = new Intent(this, CreationWorkFlowActivity.class);
        startActivity(i);
    }

    @Override
    public void onLoadMore() {
//        CharacterCache.loadSavedCharactersAsync(this, grouping.next());
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        return false;
    }

    @Override
    public void onCharactersInfoLoaded(Grouping grouping, List<CharacterInfo> characters) {
        if (characters != null) {
            if (listAdapter == null) {
                listAdapter = new SavedCharactersAdapter(this);
                listView.setOnItemClickListener(listAdapter);
                listView.setAdapter(listAdapter);
            }
            listAdapter.refreshData(characters);
            hideProgressbar();
            showListView();
//            listAdapter.addItems(characters);
        }
    }

    private void showListView() {
        if (listView != null) {
            listView.setVisibility(View.VISIBLE);
        }
    }

    private void hideProgressbar() {
        if (progressBar != null) {
            progressBar.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onOpenSavedCharacter(int characterHashCode) {
        CharacterCache.getSavedCharacterByHashCode(this, characterHashCode);
    }

    @Override
    public void onRetreiveCharacter(SavedCharacter savedCharacter, int hashCode) {
        if (savedCharacter != null) {
            Intent i = new Intent(this, CharacterViewerActivity.class);
            i.putExtras(CharacterViewerActivity.getArguments(savedCharacter));
            startActivity(i);
        }
    }
}
