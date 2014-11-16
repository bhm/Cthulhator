package com.bustiblelemons.cthulhator.character.characterslist.logic;

import android.content.Context;
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

    private OnOpenSavedCharacter onOpenSavedCharacter;

    public SavedCharactersAdapter(Context context) {
        super(context);
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.single_saved_character;
    }

    @Override
    public SavedCharacterHolder getViewHolder(View view) {
        return new SavedCharacterHolder(view).withOnOpenSavedCharacter(onOpenSavedCharacter);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        CharacterInfo i = getItem(position);
        if (onOpenSavedCharacter != null && i != null) {
            onOpenSavedCharacter.onOpenSavedCharacter(i.getHashCode());
        }
    }

}
