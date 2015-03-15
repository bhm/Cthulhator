package com.bustiblelemons.cthulhator.system;

import android.support.v4.util.LruCache;
import android.util.Pair;

import com.bustiblelemons.cthulhator.character.history.model.HistoryEvent;
import com.bustiblelemons.cthulhator.character.persistance.CharacterWrapper;
import com.bustiblelemons.cthulhator.character.possessions.model.Possesion;
import com.bustiblelemons.cthulhator.system.edition.GameEdition;
import com.bustiblelemons.cthulhator.system.properties.CharacterProperty;
import com.bustiblelemons.randomuserdotme.model.Location;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bhm on 29.07.14.
 */
@JsonIgnoreProperties({"cachedStats", "cachdSkills"})
public class CthulhuCharacter extends CharacterWrapper {

    private transient LruCache<CharacterProperty, List<Possesion>> cachedAffectedPossessions =
            new LruCache<CharacterProperty, List<Possesion>>(20);
    private Pair<Long, List<HistoryEvent>> historyForCurrentAge;

    private CthulhuCharacter(GameEdition edition) {
        super(edition);
    }

    public static CthulhuCharacter forEdition(GameEdition edition) {
        return new CthulhuCharacter(edition);
    }

    public List<Possesion> getPossesions(CharacterProperty affectedBy) {
        List<Possesion> _prop = cachedAffectedPossessions.get(affectedBy);
        if (_prop == null) {
            _prop = extractPoseesions(affectedBy);
            cachedAffectedPossessions.put(affectedBy, _prop);
        }
        return _prop;
    }

    public List<HistoryEvent> getHistoryEvents(long tillDate) {
        List<HistoryEvent> events = new ArrayList<HistoryEvent>();
        if (this.fullHistory != null) {
            for (HistoryEvent event : fullHistory) {
                if (event != null && event.isBefore(tillDate)) {
                    events.add(event);
                }
            }
        }
        return events;
    }

    public List<HistoryEvent> getHistoryEvents(long tillDate, Location around) {
        List<HistoryEvent> r = getHistoryEvents(tillDate);
        //TODO Filter by location long/lat and distance toleration according to times
        return r;
    }

    public CharacterProperty getStatistic(String byName) {
        for (CharacterProperty stat : getStatistics()) {
            String statName = stat.getName();
            if (statName.equalsIgnoreCase(byName)) {
                return stat;
            }
        }
        return null;
    }

    public CharacterProperty getSkill(String byName) {
        for (CharacterProperty skill : getSkills()) {
            String skillName = skill.getName();
            if (skillName.equalsIgnoreCase(byName)) {
                return skill;
            }
        }
        return null;
    }
}
