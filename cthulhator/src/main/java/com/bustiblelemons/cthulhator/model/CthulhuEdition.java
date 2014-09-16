package com.bustiblelemons.cthulhator.model;

import android.support.v4.util.LruCache;

import com.bustiblelemons.cthulhator.model.brp.skills.BRPSkills;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by bhm on 13.08.14.
 */
public enum CthulhuEdition {
    ToC {
        @Override
        public Set<CharacterProperty> getCharacteristics() {
            return Collections.emptySet();
        }
    }, CoC5, CoC6, CoC7, CthulhuLite {
        @Override
        public int getHobbySkillPointMultiplier() {
            return 5;
        }

        @Override
        public int getCarrierSkillPointMultiplier() {
            return 10;
        }
    };
    private static LruCache<CthulhuEdition, Set<CharacterProperty>> skillsCache =
            new LruCache<CthulhuEdition, Set<CharacterProperty>>(3);
    private static LruCache<CthulhuEdition, Set<CharacterProperty>> cache =
            new LruCache<CthulhuEdition, Set<CharacterProperty>>(3);

    public int getHobbySkillPointMultiplier() {
        return 10;
    }

    public int getCarrierSkillPointMultiplier() {
        return 20;
    }

    public Set<CharacterProperty> getCharacteristics() {
        if (cache.get(this) == null) {
            Set<CharacterProperty> r = new HashSet<CharacterProperty>();
            r.add(CharacterProperties.STR);
            r.add(CharacterProperties.CON);
            r.add(CharacterProperties.POW);
            r.add(CharacterProperties.DEX);
            r.add(CharacterProperties.APP);

            r.add(CharacterProperties.INT);
            r.add(CharacterProperties.SIZ);

            r.add(CharacterProperties.EDU);
            cache.put(this, r);
        }
        return cache.get(this);
    }

    public Set<CharacterProperty> getSkills() {
        if (skillsCache.get(this) == null) {
            Set<CharacterProperty> r = new HashSet<CharacterProperty>();
            for (BRPSkills s : BRPSkills.values()) {
                if (s.getEditions().contains(this)) {
                    r.add(s.asCharacterProperty(this));
                }
            }
            skillsCache.put(this, r);
        }
        return skillsCache.get(this);
    }

}
