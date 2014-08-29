package com.bustiblelemons.cthulhator.async;

import com.bustiblelemons.cthulhator.model.Grouping;
import com.bustiblelemons.cthulhator.model.cache.SavedCharacter;

import java.util.List;

import io.github.scottmaclure.character.traits.network.api.asyn.AbsAsynTask;

/**
 * Created by bhm on 13.08.14.
 */
public interface SavedCharactersCallBack extends AbsAsynTask.AsynCallback<Grouping, List<SavedCharacter>> {
}
