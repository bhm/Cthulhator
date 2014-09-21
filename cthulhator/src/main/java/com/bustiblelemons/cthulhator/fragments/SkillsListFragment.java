package com.bustiblelemons.cthulhator.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.bustiblelemons.cthulhator.R;
import com.bustiblelemons.fragments.AbsFragment;

/**
 * Created by bhm on 20.07.14.
 */
public class SkillsListFragment extends AbsFragment {
    public static final  int    ALPHABETICAL = 128;
    public static final  int    VALUE        = 64;
    private static final String ORDER        = "order";

    private ListView list;

    public static SkillsListFragment newInstance(int order) {
        SkillsListFragment r = new SkillsListFragment();
        Bundle args = new Bundle();
        args.putInt(ORDER, order);
        r.setArguments(args);
        return r;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_skills_list, container, false);
        list = (ListView) rootView.findViewById(android.R.id.list);
        return rootView;
    }
}
