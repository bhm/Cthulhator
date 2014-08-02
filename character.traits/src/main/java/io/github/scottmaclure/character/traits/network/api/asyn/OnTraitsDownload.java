package io.github.scottmaclure.character.traits.network.api.asyn;

import io.github.scottmaclure.character.traits.model.TraitsSet;

public interface OnTraitsDownload extends AbsAsynTask.AsynCallback<String, TraitsSet> {
}
