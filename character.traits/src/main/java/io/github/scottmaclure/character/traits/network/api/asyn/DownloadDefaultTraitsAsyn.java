package io.github.scottmaclure.character.traits.network.api.asyn;

import android.content.Context;
import android.util.Pair;

import org.codehaus.jackson.map.ObjectMapper;

import java.io.File;

import io.github.scottmaclure.character.traits.model.TraitsSet;
import io.github.scottmaclure.character.traits.network.api.CharacterTraitsQuery;
import io.github.scottmaclure.character.traits.storage.Storage;

/**
 * Created by bhm on 02.08.14.
 */
public class DownloadDefaultTraitsAsyn extends SimpleAsync<String, Pair<String, TraitsSet>> {

    public DownloadDefaultTraitsAsyn(Context context) {
        super(context);
    }

    public DownloadDefaultTraitsAsyn(Context context, OnTraitsDownloaded onTraitsDownloaded) {
        super(context);
        this.onTraitsDownloaded = onTraitsDownloaded;
    }

    private OnTraitsDownloaded onTraitsDownloaded;

    @Override
    protected Pair<String, TraitsSet> call(String... params) throws Exception {
        CharacterTraitsQuery query = new CharacterTraitsQuery();
        TraitsSet set = query.getObject(TraitsSet.class);
        Pair<String, TraitsSet> r = null;
        String fileName = TraitsSet.FILE;
        if (set != null) {
            r = Pair.create(fileName, set);
            publishProgress(r);
            ObjectMapper mapper = new ObjectMapper();
            File saveFile = Storage.getStorageFile(context, fileName);
            mapper.writeValue(saveFile, r);
        }
        return r;
    }

    @Override
    protected void onProgressUpdate(Pair<String, TraitsSet>... values) {
        super.onProgressUpdate(values);
        if (values != null) {
            for (Pair<String, TraitsSet> pair : values) {
                if (pair != null && onTraitsDownloaded != null) {
                    onTraitsDownloaded.onTraitsDownloaded(pair.first);
                }
            }
        }
    }

    @Override
    protected boolean onException(Exception e) {
        return false;
    }

    @Override
    protected boolean onSuccess(Pair<String, TraitsSet> result) {
        return false;
    }

    public interface OnTraitsDownloaded {
        boolean onTraitsDownloaded(String fileName);
    }
}
