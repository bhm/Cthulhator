package com.bustiblelemons.cthulhator.model;

import android.support.v4.util.LruCache;
import android.util.Pair;

import com.bustiblelemons.api.random.names.randomuserdotme.model.Location;
import com.bustiblelemons.cthulhator.model.cache.SavedCharacter;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bhm on 29.07.14.
 */
@JsonIgnoreProperties({"cachedStats", "cachdSkills"})
public class CthulhuCharacter extends SavedCharacter {
    private BirthData      birth;
    private long           presentDate;
    private List<Portrait> portraits;
    private LruCache<CharacterProperty, List<Possesion>> cachedAffectedPossessions =
            new LruCache<CharacterProperty, List<Possesion>>(20);
    private Pair<Long, List<HistoryEvent>> historyForCurrentAge;

    private CthulhuCharacter(CthulhuEdition edition) {
        setEdition(edition);
    }

    public static CthulhuCharacter forEdition(CthulhuEdition edition) {
        return new CthulhuCharacter(edition);
    }

    public BirthData getBirth() {
        return birth;
    }

    public void setBirth(BirthData birth) {
        this.birth = birth;
    }

    public List<Portrait> getPortraits() {
        return portraits;
    }

    public void setPortraits(List<Portrait> portraits) {
        this.portraits = portraits;
    }

    public Portrait getMainPortrait() {
        Portrait r = null;
        if (portraits != null) {
            for (Portrait portrait : portraits) {
                if (r == null && portrait != null) {
                    r = portrait;
                }
                if (portrait != null && portrait.isMain()) {
                    return portrait;
                }
            }
        }
        return r;
    }

    public List<Possesion> getPossesions(CharacterProperty affectedBy) {
        List<Possesion> _prop = cachedAffectedPossessions.get(affectedBy);
        if (_prop == null) {
            _prop = extractPoseesions(affectedBy);
            cachedAffectedPossessions.put(affectedBy, _prop);
        }
        return _prop;
    }

    public List<Possesion> extractPoseesions(CharacterProperty characterProperty) {
        List<Possesion> _prop = new ArrayList<Possesion>();
        if (this.possesions != null) {
            for (Possesion possesion : possesions) {
                if (possesion != null) {
                    List<Relation> relations = possesion.getRelations();
                    for (Relation relation : relations) {
                        String propertyName = relation.getPropertyName();
                        String soughtPropName = characterProperty.getName();
                        if (propertyName != null && propertyName.equals(soughtPropName)) {
                            _prop.add(possesion);
                        }
                    }
                }
            }
        }
        return _prop;
    }

    public List<HistoryEvent> getHistory() {
        if (historyForCurrentAge == null && historyForCurrentAge.first.longValue() != presentDate) {
            List<HistoryEvent> events = getHistoryEvents(presentDate);
            historyForCurrentAge = Pair.create(presentDate, events);
        }
        return historyForCurrentAge.second;
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
