package com.bustiblelemons.cthulhator.character.characteristics.logic;

import android.content.Context;

import com.bustiblelemons.async.AbsSimpleAsync;
import com.bustiblelemons.cthulhator.character.creation.logic.CreatorCardFactory;
import com.bustiblelemons.cthulhator.character.creation.logic.RelatedPropertesRetreiver;
import com.bustiblelemons.cthulhator.character.creation.model.CreatorCard;
import com.bustiblelemons.cthulhator.system.edition.CthulhuEdition;
import com.bustiblelemons.cthulhator.system.properties.CharacterProperty;

import java.util.Collection;
import java.util.List;

/**
 * Created by hiv on 12.11.14.
 */
public class LoadCreatorCardsAsyn
        extends AbsSimpleAsync<Collection<CharacterProperty>, List<CreatorCard>> {

    private OnCreatorCardsCreated     mOnCreatorCardsCreated;
    private RelatedPropertesRetreiver mRetreiver;
    private CthulhuEdition            mEdition;

    public LoadCreatorCardsAsyn(Context context) {
        super(context);
    }

    public LoadCreatorCardsAsyn(Context context, OnCreatorCardsCreated onCreatorCardsCreated) {
        super(context);
        this.mOnCreatorCardsCreated = onCreatorCardsCreated;
    }

    public LoadCreatorCardsAsyn withCardsCreatedCallback(OnCreatorCardsCreated callback) {
        mOnCreatorCardsCreated = callback;
        return this;
    }

    public LoadCreatorCardsAsyn withRelatedRetreiver(RelatedPropertesRetreiver relatedRetreiver) {
        mRetreiver = relatedRetreiver;
        return this;
    }

    public LoadCreatorCardsAsyn withEdition(CthulhuEdition edition) {
        mEdition = edition;
        return this;
    }

    @Override
    protected List<CreatorCard> call(Collection<CharacterProperty>... params) throws Exception {
        for (Collection<CharacterProperty> collection : params) {
            if (collection != null) {
                List<CreatorCard> cards = CreatorCardFactory.getCardsFrom(
                        mEdition, mRetreiver, collection);
                publishProgress(collection, cards);
            }
        }
        return null;
    }

    @Override
    protected boolean onException(Exception e) {
        return false;
    }

    @Override
    protected void onProgressUpdate(Collection<CharacterProperty> param, List<CreatorCard> result) {
        if (mOnCreatorCardsCreated != null) {
            mOnCreatorCardsCreated.onCreatorCardsCreated(result);
        }
    }


}
