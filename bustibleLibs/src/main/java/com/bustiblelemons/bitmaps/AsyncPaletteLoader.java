package com.bustiblelemons.bitmaps;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.graphics.Palette;

import com.bustiblelemons.async.AbsSimpleAsync;

/**
 * Created by hiv on 25.10.14.
 */
public class AsyncPaletteLoader extends AbsSimpleAsync<Bitmap, Palette> {

    private final String             mImageUri;
    private       OnPalleteGenerated mOnPalleteGenerated;

    public AsyncPaletteLoader(Context context, String imageUri) {
        super(context);
        this.mImageUri = imageUri;
        if (context instanceof OnPalleteGenerated) {
            mOnPalleteGenerated = (OnPalleteGenerated) context;
        }
    }

    @Override
    protected Palette call(Bitmap... params) throws Exception {
        for (Bitmap bitmap : params) {
            if (bitmap != null) {
                Palette palette = Palette.generate(bitmap);
                if (palette != null) {
                    publishProgress(bitmap, palette);
                }
            }
        }
        return null;
    }

    @Override
    protected boolean onException(Exception e) {
        return false;
    }

    @Override
    protected void onProgressUpdate(Bitmap param, Palette result) {
        if (mOnPalleteGenerated != null) {
            mOnPalleteGenerated.onPaletteGenerated(param, mImageUri, result);
        }
    }

    public AsyncPaletteLoader setOnPalleteGenerated(OnPalleteGenerated onPalleteGenerated) {
        this.mOnPalleteGenerated = onPalleteGenerated;
        return this;
    }

    public interface OnPalleteGenerated {
        void onPaletteGenerated(Bitmap bitmap, String uri, Palette palette);
    }
}
