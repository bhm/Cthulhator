package com.bustiblelemons.cthulhator.system.edition;

import android.support.v4.util.LruCache;

import com.bustiblelemons.cthulhator.system.brp.skills.BRPSkill;
import com.bustiblelemons.cthulhator.system.brp.statistics.BRPCharacterProperties;
import com.bustiblelemons.cthulhator.system.brp.statistics.BRPStatistic;
import com.bustiblelemons.cthulhator.system.properties.CharacterProperty;

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
        public int getCareerSkillPointMultiplier() {
            return 10;
        }
    };
    private static LruCache<CthulhuEdition, Set<CharacterProperty>> skillsCache =
            new LruCache<CthulhuEdition, Set<CharacterProperty>>(3);
    private static LruCache<CthulhuEdition, Set<CharacterProperty>> cache       =
            new LruCache<CthulhuEdition, Set<CharacterProperty>>(3);

    public int getHobbySkillPointMultiplier() {
        return 10;
    }

    public int getCareerSkillPointMultiplier() {
        return 20;
    }

    public Set<CharacterProperty> getCharacteristics() {
        if (cache.get(this) == null) {
            Set<CharacterProperty> r = new HashSet<CharacterProperty>();
            r.add(BRPCharacterProperties.STR);
            r.add(BRPCharacterProperties.CON);
            r.add(BRPCharacterProperties.POW);
            r.add(BRPCharacterProperties.DEX);
            r.add(BRPCharacterProperties.APP);

            r.add(BRPCharacterProperties.INT);
            r.add(BRPCharacterProperties.SIZ);

            r.add(BRPCharacterProperties.EDU);

            r.add(BRPStatistic.SAN.asCharacterProperty());
            r.add(BRPStatistic.LUCK.asCharacterProperty());
            r.add(BRPStatistic.IDEA.asCharacterProperty());
            r.add(BRPStatistic.KNOW.asCharacterProperty());
            cache.put(this, r);
        }
        return cache.get(this);
    }

    public Set<CharacterProperty> getSkills() {
        if (skillsCache.get(this) == null) {
            Set<CharacterProperty> r = new HashSet<CharacterProperty>();
            for (BRPSkill s : BRPSkill.values()) {
                for (CthulhuEdition edition : s.getEditions()) {
                    if (this.equals(edition)) {
                        r.add(s.asCharacterProperty(this));
                    }
                }
            }
            skillsCache.put(this, r);
        }
        return skillsCache.get(this);
    }

}
