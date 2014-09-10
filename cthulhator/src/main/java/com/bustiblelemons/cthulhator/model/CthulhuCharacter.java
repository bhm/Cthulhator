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
    private List<CharacterProperty> properties  = new ArrayList<CharacterProperty>();
    private List<Possesion>         possesions  = new ArrayList<Possesion>();
    private List<HistoryEvent>      fullHistory = new ArrayList<HistoryEvent>();
    private List<CharacterProperty> cachedStats;
    private List<CharacterProperty> cachedSkills;
    private LruCache<CharacterProperty, List<Possesion>> cachedAffectedPossessions =
            new LruCache<CharacterProperty, List<Possesion>>(20);
    private Pair<Long, List<HistoryEvent>> historyForCurrentAge;

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

    private List<CharacterProperty> fillProperties(List<CharacterProperty> dest, PropertyType type) {
        if (cachedStats == null) {
            cachedStats = new ArrayList<CharacterProperty>();
            if (dest != null) {
                for (CharacterProperty prop : properties) {
                    if (type != null && type.equals(PropertyType.STATISTIC)) {
                        dest.add(prop);
                    }
                }
            }
        }
        return dest;
    }


    public List<CharacterProperty> getStatistics() {
        return fillProperties(cachedStats, PropertyType.STATISTIC);
    }

    public List<CharacterProperty> getSkills() {
        return fillProperties(cachedSkills, PropertyType.skill);
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
        if (possesions != null) {
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
        if (fullHistory != null) {
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

    public List<CharacterProperty> getProperties() {
        return properties;
    }

    public void setProperties(List<CharacterProperty> properties) {
        this.properties = properties;
    }

    public List<Possesion> getPossesions() {
        return possesions;
    }

    public void setPossesions(List<Possesion> possesions) {
        this.possesions = possesions;
    }

    public List<HistoryEvent> getFullHistory() {
        return fullHistory;
    }

    public void setFullHistory(List<HistoryEvent> fullHistory) {
        this.fullHistory = fullHistory;
    }
}
