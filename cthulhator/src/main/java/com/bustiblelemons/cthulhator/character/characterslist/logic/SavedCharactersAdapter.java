package com.bustiblelemons.cthulhator.character.characterslist.logic;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;

import com.bustiblelemons.adapters.AbsListAdapter;
import com.bustiblelemons.cthulhator.view.charactercard.CharacterInfo;

/**
 * Created by bhm on 13.08.14.
 */
public class SavedCharactersAdapter extends AbsListAdapter<CharacterInfo, SavedCharacterHolder>
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
