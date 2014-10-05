package io.github.scottmaclure.character.traits.providers;

import android.content.Context;
import android.content.res.AssetManager;

import org.codehaus.jackson.map.ObjectMapper;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import io.github.scottmaclure.character.traits.model.RandomTraitsSet;
import io.github.scottmaclure.character.traits.model.TraitsSet;
import io.github.scottmaclure.character.traits.network.api.OnRandomTraitsetDownloaded;
import io.github.scottmaclure.character.traits.network.api.asyn.DownloadTraitsetAsyn;
import io.github.scottmaclure.character.traits.storage.Storage;

/**
 * Created by bhm on 02.08.14.
 */
public class RandomTraitsSetProvider {
    private static RandomTraitsSetProvider instance;
    private final  Context                 mContext;
    private String sDefaultTraitsJson = "traits-default.json";
    private TraitsSet mSet;
    private OnRandomTraitsetDownloaded sOnRandomTraitsetCallbackExtra = new OnRandomTraitsetDownloaded() {
        @Override
        public void onRandomTraitsFileDownloaded(File file, TraitsSet set) {
            if (set != null) {
                mSet = set;
            }
        }
    };

    private RandomTraitsSetProvider(Context context) {
        mContext = context;
        mSet = getTraitSet(context);
    }

    public static RandomTraitsSetProvider from(Context context) throws IOException {
        return instance == null ? instance = new RandomTraitsSetProvider(context) : instance;
    }

    private TraitsSet getTraitSet(Context context) {
        if (hasDownloaded(context)) {
            try {
                return readFromDownloaded(mContext);
            } catch (IOException e) {
                e.printStackTrace();
                return readFromAssets(context);
            }
        } else {
            return readFromAssets(mContext);
        }
    }

    private TraitsSet readFromDownloaded(Context context) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        File saveFile = Storage.getStorageFile(context, TraitsSet.FILE);
        return mapper.readValue(new FileInputStream(saveFile), TraitsSet.class);
    }

    private boolean hasDownloaded(Context context) {
        File saveFile = Storage.getStorageFile(context, TraitsSet.FILE);
        if (saveFile != null
                && saveFile.isFile()
                && saveFile.canRead()
                && saveFile.canWrite()) {
            return false;
        }
        return false;
    }

    private TraitsSet readFromAssets(Context context) {
        AssetManager manager = context.getAssets();
        if (manager != null) {
            try {
                InputStream in = manager.open(sDefaultTraitsJson);
                ObjectMapper mapper = new ObjectMapper();
                TraitsSet set = mapper.readValue(in, TraitsSet.class);
                return set;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public void updateFile(OnRandomTraitsetDownloaded callback) {
        AnonymDownloadTraiSet asyn = new AnonymDownloadTraiSet(mContext, callback,
                sOnRandomTraitsetCallbackExtra);
        File saveFile = Storage.getStorageFile(mContext, TraitsSet.FILE);
        asyn.executeCrossPlatform(saveFile);
    }

    public RandomTraitsSet getRandomTraitsSet() {
        return RandomTraitsSet.from(mSet);
    }

    public List<RandomTraitsSet> getRandomTraitSets(int total) {
        List<RandomTraitsSet> r = new ArrayList<RandomTraitsSet>();
        for (int i = 0; i < total; i++) {
            RandomTraitsSet set = getRandomTraitsSet();
            if (set != null) {
                r.add(set);
            }
        }
        return r;
    }

    private class AnonymDownloadTraiSet extends DownloadTraitsetAsyn {

        private final OnRandomTraitsetDownloaded extraCallback;

        public AnonymDownloadTraiSet(Context context, OnRandomTraitsetDownloaded callback,
                                     OnRandomTraitsetDownloaded extraCallback) {
            super(context, callback);
            this.extraCallback = extraCallback;
        }

        @Override
        public void onProgressUpdate(File file, TraitsSet set) {
            if (extraCallback != null) {
                extraCallback.onRandomTraitsFileDownloaded(file, set);
            }
            super.onProgressUpdate(file, set);
        }
    }

}