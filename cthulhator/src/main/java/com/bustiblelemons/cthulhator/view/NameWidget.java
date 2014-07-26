package com.bustiblelemons.cthulhator.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.bustiblelemons.cthulhator.R;
import com.micromobs.android.floatlabel.FloatLabelEditText;

import butterknife.InjectView;

/**
 * Created by bhm on 26.07.14.
 */
public class NameWidget extends FrameLayout {
    private View rootView;
    @InjectView(R.id.name)
    FloatLabelEditText nameView;
    @InjectView(R.id.title)
    FloatLabelEditText titleView;
    private boolean hideTitle = false;
    private boolean disableInput = false;

    public NameWidget(Context context) {
        super(context);
        init(context, null);
    }

    public NameWidget(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public NameWidget(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        rootView = LayoutInflater.from(context).inflate(R.layout.name_widget, this, true);
        if (attrs != null) {
            TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.NameWidget);
            hideTitle = array.getBoolean(R.styleable.NameWidget_hideTitle, hideTitle);
            disableInput = array.getBoolean(R.styleable.NameWidget_disableInput, disableInput);
            if (disableInput) {
                disableInput();
            } else {
                enableInput();
            }
            if (hideTitle) {
                hideTitle();
            } else {
                showTitle();
            }
        }
    }

    public void enableInput() {
        enableTitleInput();
        enableNameInput();
    }

    public void enableNameInput() {
        if (nameView != null){
            nameView.setFocusable(true);
            nameView.setFocusableInTouchMode(true);
        }
    }

    public void enableTitleInput() {
        if (titleView != null) {
            titleView.setFocusable(true);
            titleView.setFocusableInTouchMode(true);
        }
    }

    public void disableInput() {
        disableTitleInput();
        disableNameInput();
    }

    public void disableTitleInput() {
        if (titleView != null) {
            titleView.setFocusable(false);
            titleView.setFocusableInTouchMode(false);
        }
    }

    public void disableNameInput() {
        if (nameView != null){
            nameView.setFocusable(false);
            nameView.setFocusableInTouchMode(false);
        }
    }

    private void showTitle() {
        if (titleView != null) {
            titleView.setVisibility(View.VISIBLE);
        }
    }

    public void hideTitle() {
        if (titleView != null) {
            titleView.setVisibility(View.GONE);
        }
    }

    public void setTitle(int resId) {
        if (nameView != null) {
            nameView.setText(resId);
        }
    }

    public void setTitle(CharSequence title) {
        if (nameView != null) {
            nameView.setText(title);
        }
    }

    public void setName(CharSequence text) {
        if (nameView != null) {
            nameView.setText(text);
        }
    }
    public void setName(int resId) {
        if (nameView != null) {
            nameView.setText(resId);
        }
    }
}
