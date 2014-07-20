package com.bustiblelemons.cthulhator.model;

import com.bustiblelemons.cthulhator.fragments.main.BRPCard;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by bhm on 20.07.14.
 */
public class CharacterPropertyFactory {
    private Map<String, List<CharacterProperty>> cache =
            new HashMap<String, List<CharacterProperty>>();

    public static List<CharacterProperty> getFor(BRPCard card) {
        List<CharacterProperty> r = new ArrayList<CharacterProperty>();
        switch(card){
        case MYTHOS:

        }
        return r;
    }
}
