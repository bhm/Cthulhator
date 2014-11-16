package com.bustiblelemons.cthulhator.character.characterslist.logic;

import android.view.View;

import com.bustiblelemons.cthulhator.R;
import com.bustiblelemons.cthulhator.view.charactercard.CharacterCardView;
import com.bustiblelemons.cthulhator.view.charactercard.CharacterInfo;
import com.bustiblelemons.recycler.AbsRecyclerHolder;
import com.bustiblelemons.recycler.LayoutResId;

import butterknife.InjectView;

/**
 * Created by bhm on 13.08.14.
 */
@LayoutResId(R.layout.single_saved_character)
public class SavedCharacterHolder extends AbsRecyclerHolder<CharacterInfo> {

    @InjectView(R.id.card)
    CharacterCardView cardView;

    private OnOpenSavedCharacter mOnOpneSavedCharcter;

    public SavedCharacterHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void onBindData(CharacterInfo item) {
        if (cardView != null && item != null) {
            cardView.setCardInfo(item);
        }
    }

    public SavedCharacterHolder withOnOpenSavedCharacter(OnOpenSavedCharacter onOpenSavedCharacter) {
        mOnOpneSavedCharcter = onOpenSavedCharacter;
        return this;
    }
}
