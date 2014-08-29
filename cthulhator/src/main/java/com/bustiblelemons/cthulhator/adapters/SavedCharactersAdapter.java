package com.bustiblelemons.cthulhator.adapters;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;

import com.bustiblelemons.adapters.AbsListAdapter;
import com.bustiblelemons.cthulhator.holders.SavedCharacterHolder;
import com.bustiblelemons.cthulhator.model.cache.SavedCharacter;

/**
 * Created by bhm on 13.08.14.
 */
public class SavedCharactersAdapter extends AbsListAdapter<SavedCharacter, SavedCharacterHolder>
        implements AdapterView.OnItemClickListener {
    public SavedCharactersAdapter(Context context) {
        super(context);
    }

    @Override
    protected SavedCharacterHolder getViewHolder(int position) {
        return new SavedCharacterHolder(getContext());
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}
