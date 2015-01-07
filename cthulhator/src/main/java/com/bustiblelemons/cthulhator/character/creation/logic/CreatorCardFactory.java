package com.bustiblelemons.cthulhator.character.creation.logic;

import com.bustiblelemons.cthulhator.character.creation.model.CreatorCard;
import com.bustiblelemons.cthulhator.system.edition.GameEdition;
import com.bustiblelemons.cthulhator.system.properties.CharacterProperty;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by hiv on 02.11.14.
 */
public class CreatorCardFactory {

    public static List<CreatorCard> getCardsFrom(GameEdition edition,
                                                 RelatedPropertesRetreiver retreiver,
                                                 Collection<CharacterProperty> properties) {
        if (edition == null) {
            throw new IllegalArgumentException("edition == null");
        }
        List<CreatorCard> r = new ArrayList<CreatorCard>();
        for (CreatorCard.Type type : CreatorCard.Type.values()) {
            r.add(getCardFrom(retreiver, type, properties));
        }
        return r;
    }

    private static CreatorCard getCardFrom(RelatedPropertesRetreiver retreiver,
                                           CreatorCard.Type type,
                                           Collection<CharacterProperty> properties) {
        CreatorCard.Builder b = CreatorCard.newCreationCard();
        for (String name : type.getPropertyNames() ) {
            if (name != null) {
                for (CharacterProperty property : properties) {
                    if (property != null && property.nameMatches(name)) {
                        b.addProperty(property);
                    }
                }
            }
        }
        b.retreiver(retreiver);
        return b.build();
    }
}
