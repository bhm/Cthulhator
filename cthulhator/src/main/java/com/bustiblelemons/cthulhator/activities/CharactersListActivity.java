package com.bustiblelemons.cthulhator.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.View;

import com.bustiblelemons.activities.BaseActionBarActivity;
import com.bustiblelemons.cthulhator.R;
import com.bustiblelemons.cthulhator.adapters.SavedCharactersAdapter;
import com.bustiblelemons.cthulhator.async.SavedCharactersCallBack;
import com.bustiblelemons.cthulhator.cache.CharacterCache;
import com.bustiblelemons.cthulhator.model.Grouping;
import com.bustiblelemons.cthulhator.model.cache.SavedCharacter;
import com.bustiblelemons.views.LoadMoreListView;
import com.manuelpeinado.fadingactionbar.extras.actionbarcompat.FadingActionBarHelper;

import java.util.List;

import at.markushi.ui.CircleButton;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import io.github.scottmaclure.character.traits.network.api.asyn.AsyncInfo;

/**
 * Created by bhm on 13.08.14.
 */
public class CharactersListActivity extends BaseActionBarActivity
        implements View.OnClickListener,
                   SavedCharactersCallBack,
                   LoadMoreListView.OnLoadMoreListener,
                   SearchView.OnQueryTextListener {

    @InjectView(android.R.id.list)
    LoadMoreListView listView;
    private SavedCharactersAdapter listAdapter;

    @InjectView(R.id.search)
    SearchView searchView;

    @InjectView(R.id.add_character)
    CircleButton addFab;

    private Grouping grouping;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupFadingBar();
        ButterKnife.inject(this);
        if (listView != null) {
            listAdapter = new SavedCharactersAdapter(this);
            listView.setOnLoadMoreListener(this);
            listView.setAdapter(listAdapter);
            listView.setOnItemClickListener(listAdapter);
        }

        if (searchView != null) {
            searchView.setOnQueryTextListener(this);
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
        setContentView(helper.createView(this));
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
                launchRandomCharacter();
                break;
        }
    }

    private void launchRandomCharacter() {
        Intent i = new Intent(this, RandomCharactersActivity.class);
        startActivity(i);
    }

    @Override
    public void onAsynTaskProgress(AsyncInfo<Grouping, List<SavedCharacter>> info, Grouping param,
                                   List<SavedCharacter> result) {
        if (result != null && listAdapter != null) {
            listAdapter.addItems(result);
        }
    }

    @Override
    public void onAsynTaskFinish(AsyncInfo<Grouping, List<SavedCharacter>> info,
                                 List<SavedCharacter> result) {

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
}
