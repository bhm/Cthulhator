package com.bustiblelemons.cthulhator.holders;

import android.content.Context;
import android.view.View;

import com.bustiblelemons.cthulhator.R;
import com.bustiblelemons.cthulhator.view.charactercard.CharacterCardView;
import com.bustiblelemons.cthulhator.view.charactercard.CharacterInfo;
import com.bustiblelemons.holders.impl.ViewHolder;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by bhm on 13.08.14.
 */
public class SavedCharacterHolder implements ViewHolder<CharacterInfo> {

    @InjectView(R.id.card)
    CharacterCardView cardView;
    private Context context;

    public SavedCharacterHolder(Context context) {
        this.context = context;
    }

    @Override
    public void create(View convertView) {
        ButterKnife.inject(this, convertView);
    }

    @Override
    public void bindValues(CharacterInfo item, int position) {
        if (cardView != null && item != null) {
            cardView.setCardInfo(item);
        }
    }

    @Override
    public int getLayoutId(int position) {
        return R.layout.single_saved_character;
    }
}
