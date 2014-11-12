package com.bustiblelemons.cthulhator.character.characteristics.logic;

import com.bustiblelemons.cthulhator.character.creation.model.CreatorCard;

import java.util.List;

public interface OnCreatorCardsCreated {
    void onCreatorCardsCreated(List<CreatorCard> cards);
}