package com.bustiblelemons.views.card;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bustiblelemons.bustiblelibs.R;

import butterknife.InjectView;
import butterknife.Optional;

/**
 * Created by hiv on 27.10.14.
 */
public class CardView extends FrameLayout {
    @Optional
    @InjectView(android.R.id.title)
    TextView  mTitleView;
    @Optional
    @InjectView(android.R.id.hint)
    TextView  mHintView;
    @Optional
    @InjectView(android.R.id.custom)
    TextView  mDescriptionView;
    @Optional
    @InjectView(android.R.id.icon)
    ImageView mImageView;
    private View     rootView;
    private String   mDescription;
    private String   mTitle;
    private String   mHint;
    private Drawable mIconDrawable;
    private int mTitleStyleRes        = -1;
    private int mDescriptioneStyleRes = -1;
    private int mHintResStyle         = -1;

    public CardView(Context context) {
        super(context);
        init(context, null);
    }

    public CardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public CardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

//    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
//    public CardView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
//        super(context, attrs, defStyleAttr, defStyleRes);
//        init(context, attrs);
//    }

    private void init(Context context, AttributeSet attrs) {
        rootView = LayoutInflater.from(context).inflate(R.layout.card_view, this, true);
        mTitleView = (TextView) rootView.findViewById(android.R.id.title);
        mHintView = (TextView) rootView.findViewById(android.R.id.hint);
        mDescriptionView = (TextView) rootView.findViewById(android.R.id.custom);
        mImageView = (ImageView) rootView.findViewById(android.R.id.icon);
        if (attrs == null) {

        } else {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CardView);
            mTitle = a.getString(R.styleable.CardView_card_title);
            mHint = a.getString(R.styleable.CardView_card_hint);
            mDescription = a.getString(R.styleable.CardView_card_description);
            mIconDrawable = a.getDrawable(R.styleable.CardView_card_image);

            mTitleStyleRes = a.getResourceId(R.styleable.CardView_card_title_style, -1);
            mHintResStyle = a.getResourceId(R.styleable.CardView_card_hint_style, -1);
            mDescriptioneStyleRes = a.getResourceId(R.styleable.CardView_card_description_style, -1);
            a.recycle();
        }
        setupTitle(context);
        setupHint(context);
        setupDescription(context);
        if (mImageView != null) {
            mImageView.setImageDrawable(mIconDrawable);
        }
    }

    private void setupDescription(Context context) {
        if (mDescriptionView != null) {
            mDescriptionView.setText(mDescription);
            if (mDescriptioneStyleRes > 0) {
                mDescriptionView.setTextAppearance(context, mDescriptioneStyleRes);
            }
        }
    }

    private void setupHint(Context context) {
        if (mHintView != null) {
            mHintView.setText(mHint);
            if (mHintResStyle > 0) {
                mHintView.setTextAppearance(context, mHintResStyle);
            }
        }
    }

    private void setupTitle(Context context) {
        if (mTitleView != null) {
            mTitleView.setText(mTitle);
            if (mTitleStyleRes > 0) {
                mTitleView.setTextAppearance(context, mTitleStyleRes);
            }
        }
    }

}
