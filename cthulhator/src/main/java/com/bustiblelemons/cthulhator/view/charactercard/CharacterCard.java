package com.bustiblelemons.cthulhator.view.charactercard;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bustiblelemons.cthulhator.R;
import com.bustiblelemons.views.LoadingImage;

import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by bhm on 21.09.14.
 */
public class CharacterCard extends RelativeLayout {

    @InjectView(R.id.name)
    TextView     nameView;
    @InjectView(R.id.main_info)
    TextView     mainInfoView;
    @InjectView(R.id.extra_info)
    TextView     extraInfoView;
    @InjectView(android.R.id.icon)
    LoadingImage loadingImage;
    @InjectView(R.id.menu)
    ImageButton  menuButton;
    private View rootView;
    private boolean mShowMenu = true;

    public CharacterCard(Context context) {
        super(context);
        init(context, null);
    }

    public CharacterCard(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public CharacterCard(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        rootView = LayoutInflater.from(context).inflate(R.layout.character_card, this, false);
        nameView = (TextView) rootView.findViewById(R.id.name);
        mainInfoView = (TextView) rootView.findViewById(R.id.short_info);
        extraInfoView = (TextView) rootView.findViewById(R.id.top_characteristics);
        loadingImage = (LoadingImage) rootView.findViewById(android.R.id.icon);
        menuButton = (ImageButton) rootView.findViewById(R.id.menu);
        if (attrs != null) {
            TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.CharacterCard);
            mShowMenu = array.getBoolean(R.styleable.CharacterCard_show_menu, mShowMenu);
            menuButton.setVisibility(mShowMenu ? VISIBLE : INVISIBLE);
            array.recycle();
        }
        addView(rootView);
    }

    public void setCardInfo(CharacterInfo provider) {
        if (provider == null) {
            return;
        }
        if (nameView != null) {
            nameView.setText(provider.getName());
        }
        if (mainInfoView != null) {
            mainInfoView.setText(provider.getMainInfo());
        }
        if (extraInfoView != null) {
            extraInfoView.setText(provider.getExtraInfo());
        }
        if (loadingImage != null) {
            loadingImage.loadFrom(provider.getPortraitUrl());
        }
    }

    @OnClick(R.id.menu)
    public void onExpandMenu(ImageButton button) {
        //TODO expand the menu onto the whole card. Use git library
    }

    public interface OnMenuItemSelected {
        void onMenuItemSelected(MenuItem item);
    }
}
