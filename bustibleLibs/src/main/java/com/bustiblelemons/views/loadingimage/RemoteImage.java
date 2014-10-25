package com.bustiblelemons.views.loadingimage;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.support.v7.graphics.Palette;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.bustiblelemons.bitmaps.AsyncPaletteLoader;
import com.bustiblelemons.bustiblelibs.R;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

/**
 * Created 5 Nov 2013
 */
public class RemoteImage extends ImageView implements ImageLoadingListener,
                                                      AsyncPaletteLoader.OnPalleteGenerated {

    private static final ScaleType[] sScaleTypeArray = {
            ScaleType.MATRIX,
            ScaleType.FIT_XY,
            ScaleType.FIT_START,
            ScaleType.FIT_CENTER,
            ScaleType.FIT_END,
            ScaleType.CENTER,
            ScaleType.CENTER_CROP,
            ScaleType.CENTER_INSIDE
    };
    private String addresToLoad;
    private String failbackAddress;
    private int noImageRes = R.drawable.lemons;
    private Animation mAnimationIn;
    private boolean   useAnimations;
    private Palette   mPalette;
    private Bitmap    mBitmap;
    private Animation mAnimationOut;
    private int       animationOut;
    private int defScaleTypeIndex = 6;
    private OnPaletteGenerated              mPaletteGenerated;
    private PaletteColorsGenerated          mPaletteColorsGenerated;
    private PaletteColorsGeneratedSelective mPaletteColorsGeneratedSelective;

    public RemoteImage(Context context) {
        super(context);
        init(context, null);
    }

    public RemoteImage(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public RemoteImage(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        if (attrs != null) {
            TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.RemoteImage);
            noImageRes = array.getResourceId(R.styleable.RemoteImage_no_image, R.drawable.lemons);
            int scaleType = array.getInt(R.styleable.RemoteImage_android_scaleType, defScaleTypeIndex);
            if (scaleType >= 0) {
                setScaleType(sScaleTypeArray[scaleType]);
            }
            loadDefault();
            setupAnimations(array);
            array.recycle();
        }
    }

    private void setupAnimations(TypedArray a) {
        useAnimations = a.getBoolean(R.styleable.RemoteImage_use_animations, false);
        if (useAnimations) {
            int _in = a.getResourceId(R.styleable.RemoteImage_animationIn, R.anim.abc_fade_in);
            setAnimationIn(_in);
            int _out = a.getResourceId(R.styleable.RemoteImage_animationOut, R.anim.abc_fade_out);
            setAnimationOut(_out);
        }
    }

    private void setAnimationIn(int resId) {
        mAnimationIn = getAnimation(resId);
    }

    private Animation getAnimation(int resId) {
        return resId > 0 ? AnimationUtils.loadAnimation(getContext(), resId) : null;
    }

    public void loadFrom(String url) {
        loadFrom(url, null);
    }

    public void loadFrom(String url, String fallbackUrl) {
        if (url == null && fallbackUrl == null) {
            loadDefault();
        }
        this.failbackAddress = fallbackUrl;
        this.addresToLoad = !TextUtils.isEmpty(url) ? url : fallbackUrl;
        if (!isSameUrl(addresToLoad)) {
            rLoadUrl(addresToLoad);
        }
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
    }

    @Override
    public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
        if (failbackAddress != null) {
            rLoadUrl(failbackAddress);
        } else {
            loadDefault();
        }
    }

    @Override
    public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
        setTag(R.id.___tag_url, imageUri);
        loadBitmap(loadedImage);
        if (loadedImage != null && hasColorCallbacks()) {
            new AsyncPaletteLoader(getContext(), imageUri).setOnPalleteGenerated(this)
                    .executeCrossPlatform(loadedImage);
        }
    }

    private boolean hasColorCallbacks() {
        return mPaletteColorsGenerated != null
                || mPaletteGenerated != null
                || mPaletteColorsGeneratedSelective != null;
    }

    private void loadBitmap(Bitmap loadedImage) {
        if (useAnimations && mAnimationOut != null) {
            startAnimation(mAnimationOut);
        }
        setImageBitmap(loadedImage);
        if (useAnimations && mAnimationIn != null) {
            this.startAnimation(mAnimationIn);
        }
    }

    @Override
    public void onLoadingCancelled(String imageUri, View view) {
        loadDefault();
    }

    private void loadDefault() {
        setImageResource(noImageRes);
    }

    public void setImageDrawable(int noImageRes) {
        this.noImageRes = noImageRes;
        loadDefault();
    }

    private void handlePaletteCallbacks() {
        if (mPaletteColorsGenerated != null) {
            mPaletteGenerated.onPaletteGenerated(this, mPalette);
        }
        if (mPaletteGenerated != null) {
            mPaletteColorsGenerated.onPaletteColorsGenerated(this,
                    mPalette.getDarkMutedSwatch(),
                    mPalette.getLightVibrantSwatch(),
                    mPalette.getLightMutedSwatch(),
                    mPalette.getVibrantSwatch());
        }
        if (mPaletteColorsGeneratedSelective != null) {
            mPaletteColorsGeneratedSelective.onPaletteDarkColorsGenerated(this,
                    mPalette.getDarkVibrantSwatch(), mPalette.getLightVibrantSwatch());
            mPaletteColorsGeneratedSelective.onPaletteLightColorsGenerated(this,
                    mPalette.getLightVibrantSwatch(), mPalette.getDarkVibrantSwatch());
        }
    }

    public void setPaletteGenerated(OnPaletteGenerated paletteGenerated) {
        this.mPaletteGenerated = paletteGenerated;
    }

    public void setOnPaletteColorsGenerated(PaletteColorsGenerated onPaletteColorsGenerated) {
        this.mPaletteColorsGenerated = onPaletteColorsGenerated;
    }

    @Override
    public void onPaletteGenerated(Bitmap bitmap, String imageUri, Palette palette) {
        if (isSameUrl(imageUri)) {
            mPalette = palette;
            handlePaletteCallbacks();
        }
    }

    public void setAnimationOut(int animationOut) {
        this.animationOut = animationOut;
    }

    public void forceLoadFrom(String url) {
        rLoadUrl(url);
    }

    public void setPaletteColorsGeneratedSelective(PaletteColorsGeneratedSelective
                                                           paletteColorsGeneratedSelective) {
        this.mPaletteColorsGeneratedSelective = paletteColorsGeneratedSelective;
    }

    public interface OnPaletteGenerated {
        void onPaletteGenerated(RemoteImage loadingImage, Palette palette);

    }

    public interface PaletteColorsGenerated {
        void onPaletteColorsGenerated(RemoteImage loadingImage,
                                      Palette.Swatch darkMutedSwatch,
                                      Palette.Swatch lightVibrantSwatch,
                                      Palette.Swatch lightMutedSwatch,
                                      Palette.Swatch vibrantSwatch);
    }

    public interface PaletteColorsGeneratedSelective {
        void onPaletteDarkColorsGenerated(RemoteImage loadingImage,
                                          Palette.Swatch darkVibrant,
                                          Palette.Swatch darkMuted);

        void onPaletteLightColorsGenerated(RemoteImage loadingImage,
                                           Palette.Swatch vibrant,
                                           Palette.Swatch muted);
    }

}
