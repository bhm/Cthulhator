package com.bustiblelemons.cthulhator.character.characterslist.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v4.util.LruCache;

import com.bustiblelemons.cthulhator.character.characteristics.logic.CharacterPropertyComparators;
import com.bustiblelemons.cthulhator.character.description.model.CharacterDescription;
import com.bustiblelemons.cthulhator.character.history.model.BirthData;
import com.bustiblelemons.cthulhator.character.history.model.HistoryEvent;
import com.bustiblelemons.cthulhator.character.portrait.model.Portrait;
import com.bustiblelemons.cthulhator.character.possessions.model.Possesion;
import com.bustiblelemons.cthulhator.system.brp.skills.BRPSkillPointPools;
import com.bustiblelemons.cthulhator.system.brp.statistics.BRPStatistic;
import com.bustiblelemons.cthulhator.system.brp.statistics.HitPoints;
import com.bustiblelemons.cthulhator.system.brp.statistics.Sanity;
import com.bustiblelemons.cthulhator.system.damage.DamageBonus;
import com.bustiblelemons.cthulhator.system.damage.DamageBonusFactory;
import com.bustiblelemons.cthulhator.system.edition.CthulhuEdition;
import com.bustiblelemons.cthulhator.system.properties.CharacterProperty;
import com.bustiblelemons.cthulhator.system.properties.PropertyType;
import com.bustiblelemons.cthulhator.system.properties.Relation;
import com.bustiblelemons.cthulhator.system.time.CthulhuPeriod;
import com.bustiblelemons.randomuserdotme.model.Location;
import com.bustiblelemons.randomuserdotme.model.Name;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import org.joda.time.DateTime;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by bhm on 12.08.14.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SavedCharacter implements Parcelable, Serializable, Relation.Retreiver {

    @JsonIgnore
    public static final Parcelable.Creator<SavedCharacter>           CREATOR                    = new Parcelable.Creator<SavedCharacter>() {
        public SavedCharacter createFromParcel(Parcel source) {
            return new SavedCharacter(source);
        }

        public SavedCharacter[] newArray(int size) {
            return new SavedCharacter[size];
        }
    };
    @JsonIgnore
    private static      LruCache<CharacterProperty, List<Possesion>> cachedAffectedPossessions  =
            new LruCache<CharacterProperty, List<Possesion>>(20);
    @JsonIgnore
    private static      int                                          sShouldHaveAssignedAtLeast = 2;

    static {
        sShouldHaveAssignedAtLeast = BRPStatistic.values().length / 5;
    }

    @JsonIgnore
    private static AtomicInteger          sAtomicId   = new AtomicInteger(-1);
    protected      Set<CharacterProperty> properties  = new HashSet<CharacterProperty>();
    protected      List<Possesion>        possesions  = new ArrayList<Possesion>();
    protected      Set<HistoryEvent>      fullHistory = new HashSet<HistoryEvent>();
    @JsonIgnore
    private int id;
    private CthulhuEdition edition = CthulhuEdition.CoC5;
    private CharacterDescription description;
    private BirthData            birth;
    private int                  age;
    private CthulhuPeriod period        = CthulhuPeriod.JAZZAGE;
    @JsonIgnore
    private long          suggestedDate = Long.MIN_VALUE;
    @JsonIgnore
    private HitPoints hitPoints;
    @JsonIgnore
    private int skillPointsAvailable = -1;
    @JsonIgnore
    private int careerPoints         = -1;
    @JsonIgnore
    private int hobbyPoints          = -1;
    @JsonIgnore
    private Sanity sanity;

    public SavedCharacter() {
        this.id = sAtomicId.incrementAndGet();
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
        int tmpPeriod = in.readInt();
        this.period = tmpPeriod == -1 ? null : CthulhuPeriod.values()[tmpPeriod];
        this.description = in.readParcelable(CharacterDescription.class.getClassLoader());
        this.birth = in.readParcelable(BirthData.class.getClassLoader());
        this.age = in.readInt();
        this.suggestedDate = in.readLong();
        this.skillPointsAvailable = in.readInt();
    }

    @JsonIgnore
    public int getId() {
        return id;
    }

    public void setFullHistory(Set<HistoryEvent> fullHistory) {
        setFullHistory(fullHistory);
    }

    public CthulhuEdition getEdition() {
        if (edition == null) {
            edition = CthulhuEdition.CoC5;
        }
        return edition;
    }

    public void setEdition(CthulhuEdition edition) {
        if (edition == null) {
            return;
        }
        this.edition = edition;
        this.properties.clear();
        fillStatistics();
        updatSecondaryStatistics();
        fillSkillsList();
        updateDamageBonus();
        updateHitpoints();
        updateSkillPointPools();
        setupAgeAndBirth();
    }

    @JsonIgnore
    public int updatSecondaryStatistics() {
        return updateRelatedProperties(getStatistics());
    }


    private void updateHitpoints() {
        CharacterProperty hitPoints = getHitPoints().asCharacterProperty();
        addCharacterProperty(hitPoints);
    }


    private void updateDamageBonus() {
        CharacterProperty damageBonus = getDamageBonus().asCharacterProperty();
        addCharacterProperty(damageBonus);
    }

    @JsonIgnore
    public DamageBonus getDamageBonus() {
        int con = getStatisticValue(BRPStatistic.STR.name());
        int siz = getStatisticValue(BRPStatistic.SIZ.name());
        return DamageBonusFactory.forEdition(getEdition(), con, siz);
    }

    public CthulhuPeriod getPeriod() {
        return period;
    }

    public void setPeriod(CthulhuPeriod period) {
        this.period = period;
        setupAgeAndBirth();
    }

    @JsonIgnore
    private void setupAgeAndBirth() {
        int edu = getStatisticValue(BRPStatistic.EDU.name());
        int year = period.getDefaultYear();
        int suggestedAge = edu + 6;
        setAge(suggestedAge);
        int estimateYear = year - getAge();
        Random r = new Random();
        int month = r.nextInt(11) + 1;
        int day = r.nextInt(26) + 1;
        int h = r.nextInt(23) + 1;
        DateTime birthday = new DateTime(estimateYear, month, day, h, 0);
        BirthData birth = new BirthData();
        birth.setDate(birthday.getMillis());
        setBirth(birth);
    }

    @JsonIgnore
    public Collection<CharacterProperty> getTopCharacteristics(int max) {
        List<CharacterProperty> r = new ArrayList<CharacterProperty>();
        int i = 0;
        for (CharacterProperty property : getTopStatistics()) {
            r.add(property);
            i++;
            if (i == max) {
                return r;
            }
        }
        return r;
    }

    @JsonIgnore
    public Collection<CharacterProperty> getTopStatistics() {
        Set<CharacterProperty> r =
                new TreeSet<CharacterProperty>(CharacterPropertyComparators.VALUE_DESC);
        r.addAll(getStatistics());
        return r;
    }

    @JsonIgnore
    public Collection<CharacterProperty> getTopSkills(int max) {
        List<CharacterProperty> r = new ArrayList<CharacterProperty>();
        int i = 0;
        for (CharacterProperty property : getTopSkills()) {
            r.add(property);
            i++;
            if (i == max) {
                return r;
            }
        }
        return r;
    }

    @JsonIgnore
    public Collection<CharacterProperty> getTopSkills() {
        Set<CharacterProperty> r =
                new TreeSet<CharacterProperty>(CharacterPropertyComparators.VALUE_DESC);
        r.addAll(getSkills());
        return r;
    }

    @JsonIgnore
    private void updateSkillPointPools() {
        if (edition != null) {
            fillSkillPointPools(this.edition);
        }
    }

    @JsonIgnore
    private void fillSkillPointPools(CthulhuEdition edition) {
        fillCareerPoints(edition);
        CharacterProperty edu = getPropertyByName(BRPStatistic.EDU.name());
        CharacterProperty pointsProperty = BRPSkillPointPools.CAREER.asProperty();
        int hobbyPointsValue = edu.getValue() * edition.getCareerSkillPointMultiplier();
        pointsProperty.setValue(hobbyPointsValue);
        addCharacterProperty(pointsProperty);
    }

    @JsonIgnore
    private void fillCareerPoints(CthulhuEdition edition) {
        CharacterProperty __int = getPropertyByName(BRPStatistic.INT.name());
        CharacterProperty pointsProperty = BRPSkillPointPools.HOBBY.asProperty();
        int hobbyPointsValue = __int.getValue() * edition.getCareerSkillPointMultiplier();
        pointsProperty.setValue(hobbyPointsValue);
        addCharacterProperty(pointsProperty);
    }

    @JsonIgnore
    public Set<CharacterProperty> getCorelatives(CharacterProperty property) {
        Set<CharacterProperty> r = Collections.emptySet();
        if (property == null) {
            return r;
        }
        r = new HashSet<CharacterProperty>();
        Set<CharacterProperty> relatedProperties = getRelatedProperties(property);
        for (CharacterProperty related : relatedProperties) {
            if (related != null) {
                Set<CharacterProperty> corelativesSet = getRelatedProperties(related);
                r.addAll(corelativesSet);
            }
        }
        return r;
    }

    @JsonIgnore
    public Set<CharacterProperty> getRelatedProperties(@NonNull CharacterProperty toProperty) {
        if (toProperty == null) {
            throw new IllegalArgumentException("Passed param cannot be null");
        }
        Set<CharacterProperty> r = new HashSet<CharacterProperty>();
        Collection<Relation> relations = toProperty.getRelations();
        for (Relation relation : relations) {
            if (relation != null) {
                List<String> propNames = relation.getPropertyNames();
                for (String propName : propNames) {
                    CharacterProperty relatedProperty = getPropertyByName(propName);
                    if (relatedProperty != null && relatedProperty.getRelations() != null) {
                        for (Relation relatedPropRelation : relatedProperty.getRelations()) {
                            int value = relatedPropRelation.getValueByRelation(this);
                            relatedProperty.setBaseValue(value);
                            relatedProperty.setMaxValue(value);
                            relatedProperty.setValue(value);
                        }
                        r.add(relatedProperty);
                    }
                }
            }
        }
        return r;
    }

    @JsonIgnore
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
    public void fillSkillsList() {
        addPropertiesList(getEdition().getSkills());
        updateSkills();
    }

    @JsonIgnore
    public void fillStatistics() {
        addPropertiesList(getEdition().getCharacteristics());
    }

    @JsonIgnore
    public int updateSkills() {
        return updateRelatedProperties(getSkills());
    }

    @JsonIgnore
    public int updateRelatedProperties(Collection<CharacterProperty> ofProperties) {
        int modified = 0;
        for (CharacterProperty property : ofProperties) {
            if (property != null) {
                for (Relation relation : property.getRelations()) {
                    if (relation != null) {
                        int val = relation.getValueByRelation(this);
                        property.setValue(val);
                        property.setBaseValue(val);
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

    @JsonIgnore
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
            if (prop != null && prop.nameMatches(name)) {
                prop.setValue(val);
                return true;
            }
        }
        return false;
    }

    @JsonIgnore
    public int getCurrentSanity() {
        return getSanity().getCurrent();
    }

    @JsonIgnore
    public int getMaxSanity() {
        return getSanity().getMax();
    }

    @JsonIgnore
    public int setPropertyValues(Collection<CharacterProperty> propertyCollection) {
        int r = 0;
        if (propertyCollection == null) {
            return r;
        }
        for (CharacterProperty s : propertyCollection) {
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

    @JsonIgnore
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

    @JsonIgnore
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
                        List<String> propertyNames = relation.getPropertyNames();
                        for (String propertyName : propertyNames) {
                            String soughtPropName = characterProperty.getName();
                            if (propertyName != null && propertyName.equals(soughtPropName)) {
                                _prop.add(possesion);
                            }
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
        if (fullHistory == null) {
            fullHistory = new HashSet<HistoryEvent>(5);
        }
        return fullHistory;
    }

    public void setFullHistory(Collection<HistoryEvent> fullHistory) {
        this.fullHistory = new HashSet<HistoryEvent>(fullHistory);
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
                ", cachedAffectedPossessions=" + cachedAffectedPossessions +
                ", age=" + age +
                '}';
    }

    @JsonIgnore
    @Override
    public int describeContents() {
        return 0;
    }

    @JsonIgnore
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
        dest.writeInt(this.period == null ? -1 : this.period.ordinal());
        dest.writeParcelable(this.description, flags);
        dest.writeParcelable(this.birth, flags);
        dest.writeInt(this.age);
        dest.writeLong(this.suggestedDate);
        dest.writeInt(this.skillPointsAvailable);
    }

    public int getSkillPointsAvailable() {
        if (skillPointsAvailable < 0) {
            skillPointsAvailable = getHobbyPoints() + getCareerPoints();
        }
        return skillPointsAvailable;
    }

    public void setSkillPointsAvailable(int skillPointsAvailable) {
        this.skillPointsAvailable = skillPointsAvailable;
    }

    @JsonIgnore
    public int getHobbyPoints() {
        if (hobbyPoints < 0) {
            CharacterProperty _int = getPropertyByName(BRPStatistic.INT.name());
            if (_int != null) {
                int intVal = _int.getValue();
                hobbyPoints = edition.getHobbySkillPointMultiplier() * intVal;
            }
        }
        return hobbyPoints;
    }

    @JsonIgnore
    public int getCareerPoints() {
        if (careerPoints < 0) {
            CharacterProperty _int = getPropertyByName(BRPStatistic.EDU.name());
            if (_int != null) {
                int intVal = _int.getValue();
                careerPoints = edition.getCareerSkillPointMultiplier() * intVal;
            }
        }
        return careerPoints;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SavedCharacter character = (SavedCharacter) o;

        if (birth != null ? !birth.equals(character.birth) : character.birth != null) return false;
        if (description != null ? !description.equals(character.description) : character.description != null)
            return false;
        if (edition != character.edition) return false;
        if (period != character.period) return false;
        if (properties != null ? !properties.equals(character.properties) : character.properties != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = properties != null ? properties.hashCode() : 0;
        result = 31 * result + (edition != null ? edition.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);

        //TODO birth data does not rapport correctly
        result = 31 * result + (birth != null ? birth.hashCode() : 0);
        result = 31 * result + (period != null ? period.hashCode() : 0);
        return result;
    }

    @JsonIgnore
    public boolean hasAssignedStatistics() {
        int count = 0;
        for (CharacterProperty property : getStatistics()) {
            if (property != null && property.getValue() > 0) {
                count++;
            }
        }
        return count >= sShouldHaveAssignedAtLeast;
    }

    @JsonIgnore
    public long getSuggestedBirthDateEpoch() {
        if (Long.MIN_VALUE == suggestedDate) {
            DateTime now = new DateTime();
            int year = getAge() + getPeriod().getDefaultYear();
            int month = now.getMonthOfYear();
            int dayOfMonth = now.getDayOfMonth();
            int hour = now.getHourOfDay();
            int minute = now.getMinuteOfHour();
            DateTime time = new DateTime(year, month, dayOfMonth, hour, minute);
            suggestedDate = time.getMillis();
        }
        return suggestedDate;
    }

    @JsonIgnore
    public void setSuggestedDate(long suggestedDate) {
        this.suggestedDate = suggestedDate;
    }

    public boolean removeHistoryEvent(HistoryEvent event) {
        if (event == null) {
            return false;
        }
        if (fullHistory == null) {
            fullHistory = new TreeSet<HistoryEvent>();
        }
        fullHistory.remove(event);
        return true;
    }

    public boolean addHistoryEvent(HistoryEvent event) {
        if (event == null) {
            return false;
        }
        if (fullHistory == null) {
            fullHistory = new TreeSet<HistoryEvent>();
        }
        fullHistory.add(event);
        return true;
    }

    @JsonIgnore
    public HitPoints getHitPoints() {
        int con = getStatisticValue(BRPStatistic.CON.name());
        int siz = getStatisticValue(BRPStatistic.SIZ.name());
        return HitPoints.forProperties(getEdition(), con, siz);
    }

    @JsonIgnore
    public void setHitPoints(HitPoints hitPoints) {
        this.hitPoints = hitPoints;
    }

    @JsonIgnore
    public Set<CharacterProperty> randomizeStatistics() {
        careerPoints = -1;
        skillPointsAvailable = -1;
        hobbyPoints = -1;
        for (CharacterProperty property : getStatistics()) {
            if (property != null) {
                int newRand = property.randomValue();
                property.setValue(newRand);
            }
        }
        updateSkills();
        return getStatistics();
    }

    public Sanity getSanity() {
        return getSanity(false);
    }

    public Sanity getSanity(boolean fresh) {
        if (sanity == null || fresh) {
            sanity = new Sanity();
            int pow = getStatisticValue(BRPStatistic.POW.name());
            int max = pow * 5;
            sanity.setMax(max);
            sanity.setCurrent(max);
        }
        return sanity;
    }

    @Override
    public int retreivePropertValue(String propertyName) {
        for (CharacterProperty property : properties) {
            if (property != null && property.nameMatches(propertyName)) {
                return property.getValue();
            }
        }
        return 0;
    }
}