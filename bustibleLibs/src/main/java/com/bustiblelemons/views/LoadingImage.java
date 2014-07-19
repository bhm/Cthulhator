package com.bustiblelemons.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.bustiblelemons.bustiblelibs.R;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

/**
 * Created 5 Nov 2013
 */
public class LoadingImage extends RelativeLayout implements ImageLoadingListener {

    private View        rootView;
    private String      addresToLoad;
    private String      failbackAddress;
    private ProgressBar progress;
    private ImageView   image;
    private boolean     showProgress;
    private int noImageRes = R.drawable.lemons;
    private Animation animationIn;
    private Animation animationOut;
    private boolean   useAnimations;

    public LoadingImage(Context context) {
        super(context);
        init(context, null);
    }

    public LoadingImage(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public LoadingImage(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        rootView = LayoutInflater.from(context).inflate(R.layout.loading_image, this, true);
        image = (ImageView) rootView.findViewById(R.id.___image);
        progress = (ProgressBar) rootView.findViewById(R.id.___progress);
        if (attrs != null) {
            TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.LoadingImage);
            noImageRes = array.getResourceId(R.styleable.LoadingImage_no_image, R.drawable.lemons);
            showProgress = array.getBoolean(R.styleable.LoadingImage_show_progressbar, false);
            if (showProgress) {
                progress.setVisibility(View.VISIBLE);
            }
            setupAnimations(array);
        }
    }

    private void setupAnimations(TypedArray array) {
        useAnimations = array.getBoolean(R.styleable.LoadingImage_use_animations, false);
        if (useAnimations) {
            int _in = array.getResourceId(R.styleable.LoadingImage_animationIn, R.anim.abc_fade_in);
            setAnimationIn(_in);
            int _out = array.getResourceId(R.styleable.LoadingImage_animationOut, R.anim.abc_fade_out);
            setAnimationOut(_out);
        }
    }

    private void setAnimationOut(int resId) {
        this.animationOut = getAnimation(resId);
    }

    private void setAnimationIn(int resId) {
        this.animationIn = getAnimation(resId);
    }

    private Animation getAnimation(int resId) {
        return resId > 0 ? AnimationUtils.loadAnimation(getContext(), resId) : null;
    }

    public void loadFrom(String url) {
        loadFrom(url, null);
    }

    public void loadFrom(String url, String fallbackUrl) {
        this.failbackAddress = fallbackUrl;
        this.addresToLoad = !TextUtils.isEmpty(url) ? url : fallbackUrl;
        if (!isSameUrl(addresToLoad)) {
            rLoadUrl(addresToLoad);
        }
    }

    public void loadFrom(String url, boolean useAnimations) {
        this.useAnimations = useAnimations;
        loadFrom(url, null);
    }

    public void loadFrom(String url, String fallbackUrl, boolean useAnimations) {
        this.useAnimations = useAnimations;
        loadFrom(url, fallbackUrl);
    }

    public void loadFrom(String url, String fallbackUrl, int animIn, int animOut) {
        setAnimationIn(animIn);
        setAnimationOut(animOut);
        loadFrom(url, fallbackUrl);
    }

    private void rLoadUrl(String url) {
        if (ImageLoader.getInstance().isInited()) {
            ImageLoader.getInstance().loadImage(url, this);
        }
    }

    private boolean isSameUrl(String url) {
        String _tagUrl = (String) getTag(R.id.___tag_url);
        return _tagUrl != null ? _tagUrl.equals(url) : false;
    }

    @Override
    public void onLoadingStarted(String imageUri, View view) {
        showProgressBar();
    }

    private void showProgressBar() {
        if (showProgress) {
            progress.setVisibility(VISIBLE);
        }
    }

    @Override
    public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
        if (failbackAddress != null) {
            rLoadUrl(failbackAddress);
        }
        hideProgressbar();
    }

    private void hideProgressbar() {
        if (showProgress) {
            progress.setVisibility(GONE);
        }
    }

    @Override
    public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
        hideProgressbar();
        setTag(R.id.___tag_url, imageUri);
        loadBitmap(loadedImage);
    }

    private void loadBitmap(Bitmap loadedImage) {
        if (useAnimations && animationIn != null) {
            image.startAnimation(animationIn);
        }
        image.setImageBitmap(loadedImage);
        if (useAnimations && animationOut != null) {
            image.startAnimation(animationOut);
        }
    }

    @Override
    public void onLoadingCancelled(String imageUri, View view) {
        hideProgressbar();
        image.setImageResource(noImageRes);
    }
}
