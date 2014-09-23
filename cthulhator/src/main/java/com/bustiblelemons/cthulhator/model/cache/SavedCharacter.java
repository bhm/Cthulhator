package com.bustiblelemons.cthulhator.model.cache;

import android.util.LruCache;
import android.util.Pair;

import com.bustiblelemons.api.random.names.randomuserdotme.model.Location;
import com.bustiblelemons.api.random.names.randomuserdotme.model.Name;
import com.bustiblelemons.cthulhator.model.BirthData;
import com.bustiblelemons.cthulhator.model.CharacterProperty;
import com.bustiblelemons.cthulhator.model.CthulhuEdition;
import com.bustiblelemons.cthulhator.model.HistoryEvent;
import com.bustiblelemons.cthulhator.model.Portrait;
import com.bustiblelemons.cthulhator.model.Possesion;
import com.bustiblelemons.cthulhator.model.PropertyType;
import com.bustiblelemons.cthulhator.model.Relation;
import com.bustiblelemons.cthulhator.model.brp.skills.BRPSkillPointPools;
import com.bustiblelemons.cthulhator.model.brp.statistics.BRPStatistic;
import com.bustiblelemons.cthulhator.model.desc.CharacterDescription;

import org.codehaus.jackson.annotate.JsonIgnore;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by bhm on 12.08.14.
 */
public class SavedCharacter implements Serializable {

    protected Set<CharacterProperty> properties  = new HashSet<CharacterProperty>();
    protected List<Possesion>        possesions  = new ArrayList<Possesion>();
    protected Set<HistoryEvent>      fullHistory = new HashSet<HistoryEvent>();
    private CthulhuEdition       edition;
    private CharacterDescription description;
    private BirthData            birth;
    private long                 presentDate;
    @JsonIgnore
    private transient LruCache<CharacterProperty, List<Possesion>> cachedAffectedPossessions =
            new LruCache<CharacterProperty, List<Possesion>>(20);

    private Pair<Long, List<HistoryEvent>> historyForCurrentAge;
    @JsonIgnore
    private transient Comparator<CharacterProperty> sPropertyComparator = new Comparator<CharacterProperty>() {
        @Override
        public int compare(CharacterProperty lhs, CharacterProperty rhs) {
            int lhsv = lhs.getValue();
            int rhsv = rhs.getValue();
            if (lhsv > rhsv) {
                return 1;
            } else if (lhsv < rhsv) {
                return -1;
            }
            return 0;
        }
    };
    private int age;

    public CthulhuEdition getEdition() {
        return edition;
    }

    public void setEdition(CthulhuEdition edition) {
        this.edition = edition;
        fillStatistics(edition);
        fillSkillsList(edition);
        updateSkillPointPools();
    }

    @JsonIgnore
    public Set<CharacterProperty> getTopCharacteristics() {
        Set<CharacterProperty> r = new TreeSet<CharacterProperty>(sPropertyComparator);
        r.addAll(getStatistics());
        return r;
    }

    @JsonIgnore
    public Set<CharacterProperty> getTopCharacteristics(int max) {
        Set<CharacterProperty> r = new TreeSet<CharacterProperty>(sPropertyComparator);
        int i = 0;
        for (CharacterProperty property : getStatistics()) {
            r.add(property);
            i++;
            if (i == max) {
                return r;
            }
        }
        return r;
    }

    @JsonIgnore
    public Set<CharacterProperty> getTopSkills() {
        Set<CharacterProperty> r = new TreeSet<CharacterProperty>(sPropertyComparator);
        r.addAll(getSkills());
        return r;
    }

    @JsonIgnore
    public Set<CharacterProperty> getTopSkills(int max) {
        Set<CharacterProperty> r = new TreeSet<CharacterProperty>(sPropertyComparator);
        int i = 0;
        for (CharacterProperty property : getSkills()) {
            r.add(property);
            i++;
            if (i == max) {
                return r;
            }
        }
        return r;
    }

    private void updateSkillPointPools() {
        if (edition != null) {
            fillSkillPointPools(this.edition);
        }
    }

    private void fillSkillPointPools(CthulhuEdition edition) {
        fillCareerPoints(edition);
        CharacterProperty edu = getPropertyByName(BRPStatistic.EDU.name());
        CharacterProperty pointsProperty = BRPSkillPointPools.CAREER.asProperty();
        int hobbyPointsValue = edu.getValue() * edition.getCareerSkillPointMultiplier();
        pointsProperty.setValue(hobbyPointsValue);
        addCharacterProperty(pointsProperty);
    }

    private void fillCareerPoints(CthulhuEdition edition) {
        CharacterProperty __int = getPropertyByName(BRPStatistic.INT.name());
        CharacterProperty pointsProperty = BRPSkillPointPools.HOBBY.asProperty();
        int hobbyPointsValue = __int.getValue() * edition.getCareerSkillPointMultiplier();
        pointsProperty.setValue(hobbyPointsValue);
        addCharacterProperty(pointsProperty);
    }

    @JsonIgnore
    public Set<CharacterProperty> getRelatedProperties(CharacterProperty toProperty) {
        Collection<Relation> relations = toProperty.getRelations();
        Set<CharacterProperty> r = new HashSet<CharacterProperty>();
        for (Relation relation : relations) {
            if (relation != null) {
                CharacterProperty relatedProperty = getPropertyByName(relation.getPropertyName());
                if (relatedProperty != null) {
                    int value = relation.getBaseValueByRelation(toProperty.getValue());
                    relatedProperty.setValue(value);
                    r.add(relatedProperty);
                }
            }
        }
        return r;
    }

    private CharacterProperty getPropertyByName(String propertyName) {
        for (CharacterProperty prop : properties) {
            if (prop != null) {
                if (prop.getName() != null) {
                    if (prop.getName().equalsIgnoreCase(propertyName)) {
                        return prop;
                    }
                }
            }
        }
        return null;
    }

    @JsonIgnore
    public boolean addCharacterProperty(CharacterProperty property) {
        if (properties != null && properties.contains(property)) {
            properties.remove(property);
        }
        return properties != null && properties.add(property);
    }

    @JsonIgnore
    public void fillSkillsList(CthulhuEdition edition) {
        addPropertiesList(edition.getSkills());
        updateSkills();
    }

    @JsonIgnore
    public void fillStatistics(CthulhuEdition edition) {
        addPropertiesList(edition.getCharacteristics());
    }

    @JsonIgnore
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
        return description != null && description.getName() != null ? description.getName().getFullName() : null;
    }

    @JsonIgnore
    public String getPhotoUrl() {
        if (description != null && description.getPortraitList() != null) {
            for (Portrait portrait : description.getPortraitList()) {
                if (portrait != null && portrait.getUrl() != null) {
                    return portrait.getUrl();
                }
            }
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
        return description != null ? description.getPortraitList() : new ArrayList<Portrait>();
    }

    public void setPortraits(List<Portrait> portraits) {
        if (description == null) {
            description = new CharacterDescription();
        }
        description.setPortraitList(portraits);
    }

    public Portrait getMainPortrait() {
        Portrait r = null;
        if (getPortraits() != null) {
            for (Portrait portrait : getPortraits()) {
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

    public void addPropertiesList(Set<CharacterProperty> characterProperties) {
        if (properties == null) {
            properties = new HashSet<CharacterProperty>();
        }
        for (CharacterProperty property : characterProperties) {
            if (property != null) {
                properties.add(property);
            }
        }
        updateSkills();
    }

    @JsonIgnore
    public Set<CharacterProperty> getStatistics() {
        return getPropertiesOfType(PropertyType.STATISTIC);
    }

    @JsonIgnore
    public Set<CharacterProperty> getSkills() {
        return getPropertiesOfType(PropertyType.SKILL);
    }

    @JsonIgnore
    public Set<CharacterProperty> getPropertiesOfType(PropertyType type) {
        Set<CharacterProperty> ret = new HashSet<CharacterProperty>();
        for (CharacterProperty prop : properties) {
            if (type != null && type.equals(PropertyType.STATISTIC)) {
                if (prop != null) {
                    ret.add(prop);
                }
            }
        }
        return ret;
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

    public Set<CharacterProperty> getProperties() {
        return properties;
    }

    public void setProperties(Set<CharacterProperty> properties) {
        this.properties = properties;
    }

    public List<Possesion> getPossesions() {
        return possesions;
    }

    public void setPossesions(List<Possesion> possesions) {
        this.possesions = possesions;
    }

    public Set<HistoryEvent> getFullHistory() {
        return fullHistory;
    }

    public void setFullHistory(Set<HistoryEvent> fullHistory) {
        this.fullHistory = fullHistory;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }


    @JsonIgnore
    public Name getNameObject() {
        return getDescription() != null ? getDescription().getName() : null;
    }

    @Override
    public String toString() {
        return "SavedCharacter{" +
                "properties=" + properties +
                ", possesions=" + possesions +
                ", fullHistory=" + fullHistory +
                ", edition=" + edition +
                ", description=" + description +
                ", birth=" + birth +
                ", presentDate=" + presentDate +
                ", cachedAffectedPossessions=" + cachedAffectedPossessions +
                ", historyForCurrentAge=" + historyForCurrentAge +
                ", sPropertyComparator=" + sPropertyComparator +
                ", age=" + age +
                '}';
    }
}