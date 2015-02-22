package com.bustiblelemons.cthulhator.character.characterslist.logic;

import android.view.View;
import android.widget.AdapterView;

import com.bustiblelemons.cthulhator.R;
import com.bustiblelemons.cthulhator.view.charactercard.CharacterInfo;
import com.bustiblelemons.recycler.AbsRecyclerAdapter;

/**
 * Created by bhm on 13.08.14.
 */
public class SavedCharactersAdapter extends AbsRecyclerAdapter<CharacterInfo, SavedCharacterHolder>
        implements AdapterView.OnItemClickListener {

    private OnOpenSavedCharacter mOnOpenSavedCharacter;

    public SavedCharactersAdapter() {
        super();
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.single_saved_character;
    }

    @Override
    public SavedCharacterHolder getViewHolder(View view, int viewType) {
        SavedCharacterHolder h = new SavedCharacterHolder(view)
                .withOnOpenSavedCharacter(mOnOpenSavedCharacter);
        view.setOnClickListener(h);
        return h;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        CharacterInfo i = getItem(position);
        if (mOnOpenSavedCharacter != null && i != null) {
            mOnOpenSavedCharacter.onOpenSavedCharacter(i.getId());
        }
    }

    public SavedCharactersAdapter withOpenSaveCharacter(OnOpenSavedCharacter callback) {
        mOnOpenSavedCharacter = callback;
        return this;
    }
}
