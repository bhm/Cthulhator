package com.bustiblelemons.cthulhator.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.bustiblelemons.BaseFragment;
import com.bustiblelemons.cthulhator.R;
import com.bustiblelemons.cthulhator.adapters.BRPSkillsAdapter;

/**
 * Created by bhm on 20.07.14.
 */
public class SkillsListFragment extends BaseFragment {
    public static final  int    ALPHABETICAL = 128;
    public static final  int    VALUE        = 64;
    private static final String ORDER        = "order";

    private ListView         list;
    private BRPSkillsAdapter listAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_skills_list, container, false);
        list = (ListView) rootView.findViewById(android.R.id.list);
        listAdapter = new BRPSkillsAdapter(getContext());
        list.setAdapter(listAdapter);
        list.setOnItemClickListener(listAdapter);
        return rootView;
    }

    public static SkillsListFragment newInstance(int order) {
        SkillsListFragment r = new SkillsListFragment();
        Bundle args = new Bundle();
        args.putInt(ORDER, order);
        r.setArguments(args);
        return r;
    }
}
