package io.github.scottmaclure.character.traits.storage;

import android.content.Context;
import android.os.Environment;

import java.io.File;

public class Storage {

    public static File getStorage(Context context) {
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            return context.getExternalCacheDir();
        }
        return context.getCacheDir();
    }

    public static File getStorageFile(Context context, String filename) {
        return new File(getStorage(context), filename);
    }
}