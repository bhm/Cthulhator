package com.bustiblelemons.cthulhator.view.charactercard;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.graphics.Palette;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bustiblelemons.cthulhator.R;
import com.bustiblelemons.views.loadingimage.RemoteImage;

import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by bhm on 21.09.14.
 */
public class CharacterCardView extends RelativeLayout implements
                                                      View.OnClickListener,
                                                      RemoteImage.PaletteColorsGeneratedSelective {

    @InjectView(R.id.name)
    TextView    mMainInfoView;
    @InjectView(R.id.main_info)
    TextView    mShortInfoView;
    @InjectView(R.id.extra_info)
    TextView    mExtraInfoView;
    @InjectView(android.R.id.icon)
    RemoteImage remoteImage;
    @InjectView(R.id.menu)
    ImageButton menuButton;
    private View rootView;
    private boolean mShowMenu       = true;
    private int     mNoImageRes     = R.drawable.lemons;
    private int     mMainInfoColor  = R.color.white_87;
    private int     mShortInfoColor = mMainInfoColor;
    private int     mExtraInfoColor = R.color.black_87;
    private OnMainInfoClick           onMainInfoClick;
    private OnExtraInfoClick          onExtraInfoClick;
    private OnShortInfoClick          onShortInfoClick;
    private OnCharacterCardImageClick onCharacterCardImageClick;
    private OnCharacterCardViewClick  onCharacterCardViewClick;
    private String                    mMainText;
    private String                    mShortText;
    private String                    mExtraText;
    private View                      mMainInfoBackground;
    private int     mMenuIcon         = R.drawable.ic_overflow_dots;
    private boolean mUsePaletteColors = true;

    public CharacterCardView(Context context) {
        super(context);
        init(context, null);
    }

    public CharacterCardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public CharacterCardView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs);
    }

    public void setOnMainInfoClick(OnMainInfoClick onMainInfoClick) {
        this.onMainInfoClick = onMainInfoClick;
    }

    public void setOnExtraInfoClick(OnExtraInfoClick onExtraInfoClick) {
        this.onExtraInfoClick = onExtraInfoClick;
    }

    public void setOnShortInfoClick(OnShortInfoClick onShortInfoClick) {
        this.onShortInfoClick = onShortInfoClick;
    }

    public void setOnCharacterCardImageClick(OnCharacterCardImageClick onCharacterCardImageClick) {
        this.onCharacterCardImageClick = onCharacterCardImageClick;
    }

    private void init(Context context, AttributeSet attrs) {
        rootView = LayoutInflater.from(context).inflate(R.layout.character_card, this, false);
        mMainInfoBackground = rootView.findViewById(android.R.id.background);
        mMainInfoView = (TextView) rootView.findViewById(R.id.main_info);
        mShortInfoView = (TextView) rootView.findViewById(R.id.short_info);
        mExtraInfoView = (TextView) rootView.findViewById(R.id.extra_info_text);
        remoteImage = (RemoteImage) rootView.findViewById(android.R.id.icon);
        menuButton = (ImageButton) rootView.findViewById(R.id.menu);
        setOnClickListeners(this, mMainInfoView, mShortInfoView, mExtraInfoView, remoteImage);
        if (attrs != null) {
            TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.CharacterCardView);
            mMainInfoColor = array.getColor(R.styleable.CharacterCardView_mainInfoColor,
                    mMainInfoColor);
            mShortInfoColor = array.getColor(R.styleable.CharacterCardView_shortInfoColor,
                    mShortInfoColor);
            mExtraInfoColor = array.getColor(R.styleable.CharacterCardView_extraInfoColor,
                    mExtraInfoColor);
            mUsePaletteColors = array.getBoolean(R.styleable.CharacterCardView_usePaletteColors,
                    mUsePaletteColors);
            if (mUsePaletteColors && remoteImage != null) {
                remoteImage.setPaletteColorsGeneratedSelective(this);
            }
            mShowMenu = array.getBoolean(R.styleable.CharacterCardView_show_menu, mShowMenu);
            mMenuIcon = array.getResourceId(R.styleable.CharacterCardView_menuIcon, mMenuIcon);
            menuButton.setImageResource(mMenuIcon);
            mNoImageRes = array.getResourceId(R.styleable.CharacterCardView_no_image,
                    R.drawable.lemons);
            remoteImage.setImageDrawable(mNoImageRes);
            menuButton.setVisibility(mShowMenu ? VISIBLE : INVISIBLE);
            setNameColor(mMainInfoColor);
            setShortInfoColor(mShortInfoColor);
            setExtraInfoColor(mExtraInfoColor);
            mMainText = array.getString(R.styleable.CharacterCardView_mainText);
            setMainText(mMainText);
            mShortText = array.getString(R.styleable.CharacterCardView_shortText);
            setShortText(mShortText);
            mExtraText = array.getString(R.styleable.CharacterCardView_extraText);
            setExtraText(mExtraText);
            array.recycle();
        }
        addView(rootView);
    }

    public void setMainText(String text) {
        if (mMainInfoView != null) {
            mMainInfoView.setText(text);
        }
    }

    private void setOnClickListeners(OnClickListener listener, View... views) {
        if (listener == null) {
            return;
        }
        for (View view : views) {
            if (view != null) {
                view.setOnClickListener(listener);
            }
        }
    }

    private void setTextColor(int color) {
        setNameColor(color);
        setShortInfoColor(color);
        setExtraInfoColor(color);
    }

    private void setExtraInfoColor(int color) {
        if (mExtraInfoView != null) {
            mExtraInfoView.setTextColor(color);
        }
    }

    private void setShortInfoColor(int color) {
        if (mShortInfoView != null) {
            mShortInfoView.setTextColor(color);
        }
    }

    private void setNameColor(int color) {
        if (mMainInfoView != null) {
            mMainInfoView.setTextColor(color);
        }
    }

    public void setCardInfo(CharacterInfo provider) {
        if (provider == null) {
            return;
        }
        setMainText(provider.getName());
        setShortText(provider.getMainInfo());
        setExtraText(provider.getExtraInfo());
        if (remoteImage != null && provider.getPortraitUrl() != null) {
            remoteImage.forceLoadFrom(provider.getPortraitUrl());
        }
    }

    private void setExtraText(String text) {
        if (mExtraInfoView != null) {
            mExtraInfoView.setText(text);
        }
    }

    private void setShortText(String text) {
        if (mShortInfoView != null) {
            mShortInfoView.setText(text);
        }
    }

    @OnClick(R.id.menu)
    public void onExpandMenu(ImageButton button) {
        //TODO expand the menu onto the whole card. Use git library
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.name:
                if (onMainInfoClick != null) {
                    onMainInfoClick.onMainInfoClick(this);
                }
                if (onCharacterCardViewClick != null) {
                    onCharacterCardViewClick.onMainInfoClick(this);
                }
                break;
            case R.id.short_info:
                if (onShortInfoClick != null) {
                    onShortInfoClick.onShortInfoClick(this);
                }
                if (onCharacterCardViewClick != null) {
                    onCharacterCardViewClick.onShortInfoClick(this);
                }
                break;
            case R.id.extra_info_text:
                if (onExtraInfoClick != null) {
                    onExtraInfoClick.onExtraInfoClick(this);
                }
                if (onCharacterCardViewClick != null) {
                    onCharacterCardViewClick.onExtraInfoClick(this);
                }
                break;
            case android.R.id.icon:
                if (onCharacterCardImageClick != null) {
                    onCharacterCardImageClick.onImageClick(this);
                }
                if (onCharacterCardViewClick != null) {
                    onCharacterCardViewClick.onImageClick(this);
                }
                break;
            default:
                break;
        }
    }

    public void setOnCharacterCardViewClick(OnCharacterCardViewClick onCharacterCardViewClick) {
        this.onCharacterCardViewClick = onCharacterCardViewClick;
    }

    @Override
    public void onPaletteDarkColorsGenerated(RemoteImage loadingImage, Palette.Swatch darkVibrant,
                                             Palette.Swatch darkMuted) {
        if (darkMuted != null && mMainInfoBackground != null) {
            int backgroundColor = darkMuted.getRgb();
            mMainInfoBackground.setBackgroundColor(backgroundColor);
        }
    }

    @Override
    public void onPaletteLightColorsGenerated(RemoteImage loadingImage, Palette.Swatch vibrant,
                                              Palette.Swatch muted) {
        if (vibrant != null && mMainInfoView != null) {
            mMainInfoView.setTextColor(vibrant.getTitleTextColor());
        }
        if (vibrant != null && mShortInfoView != null) {
            mShortInfoView.setTextColor(vibrant.getBodyTextColor());
        }
    }

    public interface OnMenuItemSelected {
        void onMenuItemSelected(MenuItem item);
    }

    public interface OnCharacterCardViewClick extends
                                              OnMainInfoClick,
                                              OnExtraInfoClick,
                                              OnCharacterCardImageClick,
                                              OnShortInfoClick {
    }

    public interface OnMainInfoClick {
        void onMainInfoClick(CharacterCardView view);
    }

    public interface OnShortInfoClick {
        void onShortInfoClick(CharacterCardView view);
    }

    public interface OnExtraInfoClick {
        void onExtraInfoClick(CharacterCardView view);
    }

    public interface OnCharacterCardImageClick {
        void onImageClick(CharacterCardView view);
    }
}
