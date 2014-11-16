package com.bustiblelemons.cthulhator.character.characterslist.logic;

import android.content.Context;
import android.support.v7.widget.PopupMenu;
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
public class SavedCharacterHolder extends AbsRecyclerHolder<CharacterInfo>
        implements CharacterCardView.OnMenuClicked, View.OnClickListener {

    @InjectView(R.id.card)
    CharacterCardView cardView;

    private OnOpenSavedCharacter              mOnOpneSavedCharcter;
    private PopupMenu.OnMenuItemClickListener mMenuClickListener;
    private PopupMenu                         mPopupMenu;
    private int                               mId;

    public SavedCharacterHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void onBindData(CharacterInfo item) {
        if (cardView != null && item != null) {
            cardView.setCardInfo(item);
        }
        cardView.setMenuClickListener(this);
        mId = item.getId();
    }

    public SavedCharacterHolder withOnOpenSavedCharacter(OnOpenSavedCharacter onOpenSavedCharacter) {
        mOnOpneSavedCharcter = onOpenSavedCharacter;
        return this;
    }

    @Override
    public void onMeneButtonClicked(CharacterCardView cardView, View menuButton) {
        Context context = cardView.getContext();
        if (mPopupMenu == null) {
            mPopupMenu = new PopupMenu(context, menuButton);
            mPopupMenu.setOnMenuItemClickListener(mMenuClickListener);
            mPopupMenu.inflate(R.menu.saved_character);
        }
        mPopupMenu.show();
    }

    @Override
    public void onClick(View v) {
        if (mOnOpneSavedCharcter != null) {
            mOnOpneSavedCharcter.onOpenSavedCharacter(mId);
        }
    }
}
