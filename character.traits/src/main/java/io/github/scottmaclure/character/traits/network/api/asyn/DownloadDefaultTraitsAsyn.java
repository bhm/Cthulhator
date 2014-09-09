package io.github.scottmaclure.character.traits.network.api.asyn;

import android.content.Context;

import org.codehaus.jackson.map.ObjectMapper;

import java.io.File;

import io.github.scottmaclure.character.traits.model.TraitsSet;
import io.github.scottmaclure.character.traits.network.api.CharacterTraitsQuery;
import io.github.scottmaclure.character.traits.storage.Storage;

/**
 * Created by bhm on 02.08.14.
 */
public class DownloadDefaultTraitsAsyn extends AbsAsynTask<String, TraitsSet> {

    private OnTraitsDownload callback;

    public DownloadDefaultTraitsAsyn(Context context) {
        super(context);
    }

    public DownloadDefaultTraitsAsyn(Context context, OnTraitsDownload callback) {
        super(context);
        this.callback = callback;
    }

    @Override
    protected TraitsSet call(String... params) throws Exception {
        CharacterTraitsQuery query = new CharacterTraitsQuery();
        TraitsSet set = query.getObject(TraitsSet.class);
        String fileName = TraitsSet.FILE;
        if (set != null) {
            ObjectMapper mapper = new ObjectMapper();
            File saveFile = Storage.getStorageFile(context, fileName);
            mapper.writeValue(saveFile, set);
            publishProgress(fileName, set);
        }
        return set;
    }

    @Override
    protected boolean onException(Exception e) {
        return false;
    }

    @Override
    public void onProgressUpdate(String param, TraitsSet result) {
        if (callback != null) {
            callback.onTraitsDownlaoded(param, result);
        }
    }

}
