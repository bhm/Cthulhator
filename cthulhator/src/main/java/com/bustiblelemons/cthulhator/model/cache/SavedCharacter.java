package com.bustiblelemons.cthulhator.model.cache;

import android.util.LruCache;
import android.util.Pair;

import com.bustiblelemons.api.random.names.randomuserdotme.model.Location;
import com.bustiblelemons.cthulhator.model.BirthData;
import com.bustiblelemons.cthulhator.model.CharacterProperty;
import com.bustiblelemons.cthulhator.model.CthulhuEdition;
import com.bustiblelemons.cthulhator.model.HistoryEvent;
import com.bustiblelemons.cthulhator.model.Portrait;
import com.bustiblelemons.cthulhator.model.Possesion;
import com.bustiblelemons.cthulhator.model.PropertyType;
import com.bustiblelemons.cthulhator.model.Relation;
import com.bustiblelemons.cthulhator.model.brp.skills.BRPSkills;
import com.bustiblelemons.cthulhator.model.brp.statistics.BRPStatistic;
import com.bustiblelemons.cthulhator.model.desc.CharacterDescription;

import org.codehaus.jackson.annotate.JsonIgnore;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bhm on 12.08.14.
 */
public class SavedCharacter {

    private CthulhuEdition edition;
    private CharacterDescription description;
    private BirthData            birth;
    private long                 presentDate;
    private List<Portrait>       portraits;
    private List<CharacterProperty> properties  = new ArrayList<CharacterProperty>();
    private List<Possesion>         possesions  = new ArrayList<Possesion>();
    private List<HistoryEvent>      fullHistory = new ArrayList<HistoryEvent>();
    @JsonIgnore
    private List<CharacterProperty> cachedStats;
    @JsonIgnore
    private List<CharacterProperty> cachedSkills;
    @JsonIgnore
    private LruCache<CharacterProperty, List<Possesion>> cachedAffectedPossessions =
            new LruCache<CharacterProperty, List<Possesion>>(20);

    private Pair<Long, List<HistoryEvent>> historyForCurrentAge;

    public CthulhuEdition getEdition() {
        return edition;
    }

    public void setEdition(CthulhuEdition edition) {
        this.edition = edition;
        fillSkillsList(edition);
    }

    @JsonIgnore
    public boolean addCharacterProperty(CharacterProperty property) {
        return properties != null && properties.add(property);
    }

    @JsonIgnore
    private void fillSkillsList(CthulhuEdition edition) {
        for (BRPSkills skill : BRPSkills.getListByEdition(edition)) {
            if (skill != null) {
                addCharacterProperty(skill.asCharacterProperty(edition));
            }
        }
    }

    public int updateSkills() {
        int modified = 0;
        for (CharacterProperty skill : getSkills()) {
            if (skill != null) {
                for (Relation r : skill.getRelations()) {
                    if (r != null) {
                        int newBaseValue = r.getBaseValueByRelation(skill.getValue());
                        skill.setBaseValue(newBaseValue);
                        modified++;
                    }
                }
            }
        }
        return modified;
    }

    public CharacterDescription getDescription() {
        return description;
    }

    public void setDescription(CharacterDescription description) {
        this.description = description;
    }

    public String getName() {
        return description.getName().getFullName();
    }

    public String getPhotoUrl() {
        List<Portrait> portraitList = description.getPortraitList();
        if (portraitList != null && portraitList.size() > 0) {
            return portraitList.get(0) != null ? portraitList.get(0).getUrl() : null;
        }
        return null;
    }

    @JsonIgnore
    public int getSkillValue(String name) {
        CharacterProperty skill = getSkill(name);
        return skill != null ? skill.getValue() : -1;
    }

    @JsonIgnore
    public int getStatisticValue(String soughtStatistic) {
        CharacterProperty stat = getStatistic(soughtStatistic);
        return stat != null ? stat.getValue() : 1;
    }

    @JsonIgnore
    protected boolean setPropertyValue(String name, int val) {
        for (CharacterProperty prop : getProperties()) {
            if (prop != null) {
                if (prop.getName().equalsIgnoreCase(name)) {
                    prop.setValue(val);
                    return true;
                }
            }
        }
        return false;
    }

    public int getCurrentSanity() {
        return 0;
    }

    public int getMaxSanity() {
        return 0;
    }

    @JsonIgnore
    public int setStatistics(List<CharacterProperty> statistics) {
        int r = 0;
        for (CharacterProperty s : statistics) {
            if (s != null) {
                setPropertyValue(s.getName(), s.getValue());
                r++;
            }
        }
        updateSkills();
        return r;
    }

    @JsonIgnore
    public boolean setDexterity(int dex) {
        return setPropertyValue(BRPStatistic.DEX.name(), dex);
    }

    @JsonIgnore
    public boolean setEducation(int edu) {
        return setPropertyValue(BRPStatistic.EDU.name(), edu);
    }

    @JsonIgnore
    public boolean setIntelligence(int intellingence) {
        return setPropertyValue(BRPStatistic.INT.name(), intellingence);
    }

    @JsonIgnore
    public boolean setAppearance(int app) {
        return setPropertyValue(BRPStatistic.APP.name(), app);
    }

    @JsonIgnore
    public boolean setSize(int size) {
        return setPropertyValue(BRPStatistic.SIZ.name(), size);
    }

    @JsonIgnore
    public boolean setStrength(int str) {
        return setPropertyValue(BRPStatistic.STR.name(), str);
    }

    @JsonIgnore
    public boolean setPower(int pow) {
        return setPropertyValue(BRPStatistic.POW.name(), pow);
    }

    @JsonIgnore
    public boolean setSanity(int san) {
        return setPropertyValue(BRPStatistic.SAN.name(), san);
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

    @JsonIgnore
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

    @JsonIgnore
    public List<CharacterProperty> getStatistics() {
        return fillProperties(cachedStats, PropertyType.STATISTIC);
    }

    @JsonIgnore
    public List<CharacterProperty> getSkills() {
        return fillProperties(cachedSkills, PropertyType.SKILL);
    }

    @JsonIgnore
    public List<Possesion> getPossesions(CharacterProperty affectedBy) {
        List<Possesion> _prop = cachedAffectedPossessions.get(affectedBy);
        if (_prop == null) {
            _prop = extractPoseesions(affectedBy);
            cachedAffectedPossessions.put(affectedBy, _prop);
        }
        return _prop;
    }

    @JsonIgnore
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

    @JsonIgnore
    public List<HistoryEvent> getHistory() {
        if (historyForCurrentAge == null && historyForCurrentAge.first.longValue() != presentDate) {
            List<HistoryEvent> events = getHistoryEvents(presentDate);
            historyForCurrentAge = Pair.create(presentDate, events);
        }
        return historyForCurrentAge.second;
    }

    @JsonIgnore
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

    @JsonIgnore
    public List<HistoryEvent> getHistoryEvents(long tillDate, Location around) {
        List<HistoryEvent> r = getHistoryEvents(tillDate);
        //TODO Filter by location long/lat and distance toleration according to times
        return r;
    }

    @JsonIgnore
    public CharacterProperty getStatistic(String byName) {
        for (CharacterProperty stat : getStatistics()) {
            String statName = stat.getName();
            if (statName.equalsIgnoreCase(byName)) {
                return stat;
            }
        }
        return null;
    }

    @JsonIgnore
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