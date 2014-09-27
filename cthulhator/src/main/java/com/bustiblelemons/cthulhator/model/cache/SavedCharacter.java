package com.bustiblelemons.cthulhator.model.cache;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.LruCache;

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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by bhm on 12.08.14.
 */
public class SavedCharacter implements Parcelable {

    public static final Parcelable.Creator<SavedCharacter> CREATOR     = new Parcelable.Creator<SavedCharacter>() {
        public SavedCharacter createFromParcel(Parcel source) {
            return new SavedCharacter(source);
        }

        public SavedCharacter[] newArray(int size) {
            return new SavedCharacter[size];
        }
    };
    protected           Set<CharacterProperty>             properties  = new HashSet<CharacterProperty>();
    protected           List<Possesion>                    possesions  = new ArrayList<Possesion>();
    protected           Set<HistoryEvent>                  fullHistory = new HashSet<HistoryEvent>();
    private CthulhuEdition       edition;
    private CharacterDescription description;
    private BirthData            birth;
    private long                 presentDate;
    @JsonIgnore
    private transient LruCache<CharacterProperty, List<Possesion>> cachedAffectedPossessions =
            new LruCache<CharacterProperty, List<Possesion>>(20);
    @JsonIgnore
    private transient Comparator<CharacterProperty>                sPropertyComparator       = new Comparator<CharacterProperty>() {
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

    public SavedCharacter() {
    }

    private SavedCharacter(Parcel in) {
        int propSize = in.readInt();
        CharacterProperty[] props = new CharacterProperty[propSize];
        in.readTypedArray(props, CharacterProperty.CREATOR);
        this.properties = new HashSet<CharacterProperty>();
        Collections.addAll(this.properties, props);
        this.possesions = new ArrayList<Possesion>();
        in.readList(this.possesions, List.class.getClassLoader());
        int fullHistorySize = in.readInt();
        HistoryEvent[] events = new HistoryEvent[fullHistorySize];
        in.readTypedArray(events, HistoryEvent.CREATOR);
        this.fullHistory = new HashSet<HistoryEvent>();
        Collections.addAll(this.fullHistory, events);
        int tmpEdition = in.readInt();
        this.edition = tmpEdition == -1 ? null : CthulhuEdition.values()[tmpEdition];
        this.description = in.readParcelable(CharacterDescription.class.getClassLoader());
        this.birth = in.readParcelable(BirthData.class.getClassLoader());
        this.presentDate = in.readLong();
        this.age = in.readInt();
    }

    public CthulhuEdition getEdition() {
        return edition;
    }

    public void setEdition(CthulhuEdition edition) {
        this.edition = edition;
        this.properties.clear();
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
            if (prop != null && prop.getName() != null) {
                if (prop.getName().equalsIgnoreCase(propertyName)) {
                    return prop;
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
        properties.addAll(characterProperties);
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
            if (prop != null && type != null) {
                if (type.equals(prop.getType())) {
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
                ", sPropertyComparator=" + sPropertyComparator +
                ", age=" + age +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        int propSize = this.properties != null ? this.properties.size() : 0;
        dest.writeInt(propSize);
        CharacterProperty[] props = new CharacterProperty[propSize];
        if (this.properties != null) {
            props = this.properties.toArray(new CharacterProperty[propSize]);
        }
        dest.writeTypedArray(props, flags);
        dest.writeList(this.possesions);
        int historySize = this.fullHistory != null ? this.fullHistory.size() : 0;
        dest.writeInt(historySize);
        HistoryEvent[] h = new HistoryEvent[historySize];
        if (fullHistory != null) {
            h = this.fullHistory.toArray(new HistoryEvent[historySize]);
        }
        dest.writeTypedArray(h, flags);
        dest.writeInt(this.edition == null ? -1 : this.edition.ordinal());
        dest.writeParcelable(this.description, flags);
        dest.writeParcelable(this.birth, flags);
        dest.writeLong(this.presentDate);
        dest.writeInt(this.age);
    }

    public int getHobbyPoints() {
        CharacterProperty _int = getPropertyByName(BRPStatistic.INT.name());
        if (_int != null) {
            int intVal = _int.getValue();
            return edition.getHobbySkillPointMultiplier() * intVal;
        }
        return 0;
    }

    public int getCareerPoints() {
        CharacterProperty _int = getPropertyByName(BRPStatistic.EDU.name());
        if (_int != null) {
            int intVal = _int.getValue();
            return edition.getCareerSkillPointMultiplier() * intVal;
        }
        return 0;
    }
}