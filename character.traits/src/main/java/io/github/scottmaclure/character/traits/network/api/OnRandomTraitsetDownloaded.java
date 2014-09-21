package io.github.scottmaclure.character.traits.network.api;

import java.io.File;

import io.github.scottmaclure.character.traits.model.TraitsSet;

/**
 * Created by bhm on 21.09.14.
 */
public interface OnRandomTraitsetDownloaded {
    void onRandomTraitsFileDownloaded(File file, TraitsSet set);
}
