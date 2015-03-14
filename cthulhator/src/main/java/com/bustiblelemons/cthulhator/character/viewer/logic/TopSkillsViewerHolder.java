package com.bustiblelemons.cthulhator.character.viewer.logic;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.bustiblelemons.cthulhator.character.persistance.TopSkillsRetriever;
import com.bustiblelemons.cthulhator.character.viewer.CharacterViewerCard;
import com.bustiblelemons.cthulhator.system.properties.CharacterProperty;
import com.bustiblelemons.recycler.AbsRecyclerHolder;

import java.util.List;

/**
 * Created by hiv on 22.02.15.
 */
public class TopSkillsViewerHolder extends AbsRecyclerHolder<CharacterViewerCard> {

    private final RecyclerView               mRecycler;
    private       RecyclerView.LayoutManager mLayoutManager;
    private       TopSkillsRetriever         mRetreiver;
    private       TopRkillsAdapter           mRecyclerAdapter;

    public TopSkillsViewerHolder(View view) {
        super(view);
        mRecycler = (RecyclerView) view.findViewById(android.R.id.list);
        mLayoutManager = new LinearLayoutManager(view.getContext());
        mRecycler.setLayoutManager(mLayoutManager);
    }

    public TopSkillsViewerHolder withPropertyValueRetriever(TopSkillsRetriever retreiver) {
        mRetreiver = retreiver;
        return this;
    }

    @Override
    public void onBindData(CharacterViewerCard item) {
        if (mRetreiver != null && mRecycler != null) {
            List<CharacterProperty> topSkills = mRetreiver.getTopSkills(5);
            if (mRecyclerAdapter == null) {
                mRecyclerAdapter = new TopRkillsAdapter();
            }
            mRecycler.setAdapter(mRecyclerAdapter);
            mRecyclerAdapter.refreshData(topSkills);
        }
    }
}
