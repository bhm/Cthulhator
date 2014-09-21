package io.github.scottmaclure.character.traits.network.api.asyn;

import android.content.Context;

import org.codehaus.jackson.map.ObjectMapper;

import java.io.File;

import io.github.scottmaclure.character.traits.asyn.AbsAsynTask;
import io.github.scottmaclure.character.traits.model.TraitsSet;
import io.github.scottmaclure.character.traits.network.api.CharacterTraitsQuery;
import io.github.scottmaclure.character.traits.network.api.OnRandomTraitsetDownloaded;

/**
 * Created by bhm on 02.08.14.
 */
public class DownloadTraitsetAsyn extends AbsAsynTask<File, TraitsSet> {

    private final OnRandomTraitsetDownloaded callback;

    public DownloadTraitsetAsyn(Context context, OnRandomTraitsetDownloaded callback) {
        super(context);
        this.callback = callback;
    }

    @Override
    protected TraitsSet call(File... params) throws Exception {
        CharacterTraitsQuery query = new CharacterTraitsQuery();
        TraitsSet set = query.getObject(TraitsSet.class);
        for (File saveFile : params) {
            if (set != null) {
                ObjectMapper mapper = new ObjectMapper();
                mapper.writeValue(saveFile, set);
                publishProgress(saveFile, set);
            }
        }
        return set;
    }

    @Override
    protected boolean onException(Exception e) {
        return false;
    }

    @Override
    public void onProgressUpdate(File file, TraitsSet set) {
        if (callback != null) {
            callback.onRandomTraitsFileDownloaded(file, set);
        }
    }

}
