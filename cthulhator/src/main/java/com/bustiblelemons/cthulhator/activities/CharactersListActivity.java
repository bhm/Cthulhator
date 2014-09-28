package com.bustiblelemons.cthulhator.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.View;

import com.bustiblelemons.activities.AbsActionBarActivity;
import com.bustiblelemons.cthulhator.R;
import com.bustiblelemons.cthulhator.adapters.SavedCharactersAdapter;
import com.bustiblelemons.cthulhator.cache.CharacterCache;
import com.bustiblelemons.cthulhator.creation.ui.CreationWorkFlowActivity;
import com.bustiblelemons.cthulhator.model.Grouping;
import com.bustiblelemons.cthulhator.view.charactercard.CharacterInfo;
import com.bustiblelemons.views.LoadMoreListView;
import com.manuelpeinado.fadingactionbar.extras.actionbarcompat.FadingActionBarHelper;

import java.util.List;

import at.markushi.ui.CircleButton;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by bhm on 13.08.14.
 */
public class CharactersListActivity extends AbsActionBarActivity
        implements View.OnClickListener,
                   CharacterCache.OnCharactersInfoLoaded,
                   LoadMoreListView.OnLoadMoreListener,
                   SearchView.OnQueryTextListener {

    @InjectView(android.R.id.list)
    LoadMoreListView listView;
    @InjectView(R.id.add_character)
    CircleButton     addFab;
    private SavedCharactersAdapter listAdapter;
    private Grouping               grouping;
    private FadingActionBarHelper  fadingbarHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fadingbarHelper = setupFadingBar();
        setContentView(R.layout.activity_characters_list);
        ButterKnife.inject(this);
        if (listView != null) {
            listAdapter = new SavedCharactersAdapter(this);
            listView.setOnLoadMoreListener(this);
            listView.setAdapter(listAdapter);
            listView.setOnItemClickListener(listAdapter);
        }
    }

    private void loadFresh() {
        grouping = new Grouping();
        CharacterCache.loadSavedCharactersAsync(this, grouping);
    }

    private FadingActionBarHelper setupFadingBar() {
        FadingActionBarHelper helper = new FadingActionBarHelper()
                .actionBarBackground(R.drawable.actionbar_brp)
                .headerLayout(R.layout.header_characters_list)
                .headerOverlayLayout(R.layout.header_characters_list_overlay)
                .contentLayout(R.layout.activity_characters_list)
                .lightActionBar(true);
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
        CharacterCache.loadSavedCharactersAsync(this, grouping.next());
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
        }
    }

}
