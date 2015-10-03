package com.bustiblelemons.cthulhator.storage;

import android.content.Context;

import io.realm.Realm;

/**
 * Created by hiv on 29.03.15.
 */
public class RandomCharacteristicsFacade {
    // store into on te amount of data separtely
    // read in random amount of data into the memory
    // shuffle positions and read another set
    private static Realm  sRandomPhotos;
    private        String sRandomPhotosFilname;

    private void getRandomPhoto(Context context) {
        if (sRandomPhotos == null) {
            sRandomPhotos = Realm.getInstance(context, sRandomPhotosFilname, new byte[1]);
        }
    }

}
