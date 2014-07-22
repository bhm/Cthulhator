package com.bustiblelemons.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bustiblelemons.bustiblelibs.R;

/**
 * Created by bhm on 22.07.14.
 */
public class FloatingActionButton extends RelativeLayout {
    private ImageView image;
    private View      rootView;
    private int       defaultColor;
    private View      content;

    public FloatingActionButton(Context context) {
        super(context);
        init(context, null);
    }

    public FloatingActionButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public FloatingActionButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs);
    }


    private void init(Context context, AttributeSet attrs) {
        rootView = LayoutInflater.from(context).inflate(R.layout.floating_action_button, this, true);
        content = rootView.findViewById(android.R.id.content);
        if (attrs != null) {
            TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.FloatingActionButton);
            int anchorId = array.getResourceId(R.styleable.FloatingActionButton_anchorView, -1);
            defaultColor = getResources().getColor(R.color.red_500);
            int color = array.getColor(R.styleable.FloatingActionButton_buttonColor, defaultColor);
            GradientDrawable drawable = getGradientDrawableForGo(color, 1500, false);
            content.setBackgroundDrawable(drawable);
        }
    }

    public GradientDrawable getGradientDrawableForGo(int color, int radius, boolean gradient) {

        float hsv_start[] = new float[3];
        float hsv_stroke[] = new float[3];

        Color.RGBToHSV(Color.red(color), Color.green(color), Color.blue(color), hsv_stroke);
        Color.RGBToHSV(Color.red(color), Color.green(color), Color.blue(color), hsv_start);
        hsv_stroke[2] = 0.5f * (1f + hsv_stroke[2]);
        hsv_start[2] = 0.5f * (1f + hsv_stroke[2]);
        int lighterColor = Color.HSVToColor(hsv_start);
        int strokeColor = Color.HSVToColor(hsv_stroke);

        int end = color;
        int start = color;
        if (gradient) {
            start = lighterColor;
        }

        GradientDrawable gd = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, new int[]{start, end});
        gd.setCornerRadius(radius);
        gd.setStroke(2, strokeColor);
        return gd;
    }

    public GradientDrawable getGradientDrawable(Context context, AttributeSet set) {
        boolean gradient = false;
        int _color = -1;
        int radius = 50;
        return getGradientDrawable(_color, radius, gradient);
    }

    public GradientDrawable getGradientDrawable(int color, int radius, boolean gradient) {

        float hsvStart[] = new float[3];
        float hsvStroke[] = new float[3];

        Color.RGBToHSV(Color.red(color), Color.green(color), Color.blue(color), hsvStroke);
        Color.RGBToHSV(Color.red(color), Color.green(color), Color.blue(color), hsvStart);
        hsvStroke[2] = 0.5f * (1f + hsvStroke[2]);
        hsvStart[2] = 0.5f * (1f + hsvStroke[2]);
        int lighterColor = Color.HSVToColor(hsvStart);
        int strokeColor = Color.HSVToColor(hsvStroke);

        int end = color;
        int start = color;
        if (gradient) {
            start = lighterColor;
        }

        GradientDrawable gd = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, new int[]{start, end});
        gd.setCornerRadius(radius);
        gd.setStroke(2, strokeColor);
        return gd;
    }
}
