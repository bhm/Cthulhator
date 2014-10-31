package com.bustiblelemons.cthulhator.character.characterslist.model;

import android.os.Parcel;
import android.os.Parcelable;
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
import com.bustiblelemons.cthulhator.system.damage.DamageBonus;
import com.bustiblelemons.cthulhator.system.damage.DamageBonusFactory;
import com.bustiblelemons.cthulhator.system.edition.CthulhuEdition;
import com.bustiblelemons.cthulhator.system.properties.CharacterProperty;
import com.bustiblelemons.cthulhator.system.properties.ObservableCharacterProperty;
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

/**
 * Created by bhm on 12.08.14.
 */
@JsonIgnoreProperties
public class SavedCharacter implements
                            Parcelable, Serializable,
                            ObservableCharacterProperty.OnCorelativesChanged<CharacterProperty> {

    @JsonIgnore
    public static final Parcelable.Creator<SavedCharacter>                  CREATOR                    = new Parcelable.Creator<SavedCharacter>() {
        public SavedCharacter createFromParcel(Parcel source) {
            return new SavedCharacter(source);
        }

        public SavedCharacter[] newArray(int size) {
            return new SavedCharacter[size];
        }
    };
    @JsonIgnore
    private static      LruCache<CharacterProperty, List<Possesion>>        cachedAffectedPossessions  =
            new LruCache<CharacterProperty, List<Possesion>>(20);
    @JsonIgnore
    private static      LruCache<CharacterProperty, Set<CharacterProperty>> propertyToProperty         =
            new LruCache<CharacterProperty, Set<CharacterProperty>>(BRPStatistic.values().length);
    @JsonIgnore
    private static      int                                                 sShouldHaveAssignedAtLeast = 2;

    static {
        sShouldHaveAssignedAtLeast = BRPStatistic.values().length / 5;
    }

    protected Set<CharacterProperty> properties  = new HashSet<CharacterProperty>();
    protected List<Possesion>        possesions  = new ArrayList<Possesion>();
    protected Set<HistoryEvent>      fullHistory = new HashSet<HistoryEvent>();
    private   CthulhuEdition         edition     = CthulhuEdition.CoC5;
    private CharacterDescription description;
    private BirthData            birth;
    private long                 presentDate;
    private int                  age;
    private CthulhuPeriod period        = CthulhuPeriod.JAZZAGE;
    private long          suggestedDate = Long.MIN_VALUE;
    @JsonIgnore
    private HitPoints hitPoints;

    @JsonIgnore
    private ObservableCharacterProperty.OnReltivesChanged<CharacterProperty> reltivesChanged;

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
        int tmpPeriod = in.readInt();
        this.period = tmpPeriod == -1 ? null : CthulhuPeriod.values()[tmpPeriod];
        this.description = in.readParcelable(CharacterDescription.class.getClassLoader());
        this.birth = in.readParcelable(BirthData.class.getClassLoader());
        this.presentDate = in.readLong();
        this.age = in.readInt();
        this.suggestedDate = in.readLong();
    }

    public void setPropertiesObserver(ObservableCharacterProperty.OnReltivesChanged<CharacterProperty> reltivesChanged) {
        this.reltivesChanged = reltivesChanged;
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
        fillSkillsList();
        updateDamageBonus();
        updateSkillPointPools();
        setupAgeAndBirth();
    }

    @JsonIgnore
    public DamageBonus getDamageBonus() {
        int con = getStatisticValue(BRPStatistic.CON.name());
        int siz = getStatisticValue(BRPStatistic.SIZ.name());
        return DamageBonusFactory.forEdition(getEdition(), con, siz);
    }

    private void updateDamageBonus() {
        CharacterProperty damageBonus = getDamageBonus().asCharacterProperty();
        addCharacterProperty(damageBonus);
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
    public Set<CharacterProperty> getTopCharacteristics() {
        Set<CharacterProperty> r = new TreeSet<CharacterProperty>(
                CharacterPropertyComparators.VALUE);
        r.addAll(getStatistics());
        return r;
    }

    @JsonIgnore
    public Set<CharacterProperty> getTopCharacteristics(int max) {
        Set<CharacterProperty> r = new TreeSet<CharacterProperty>(
                CharacterPropertyComparators.VALUE);
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
        Set<CharacterProperty> r = new TreeSet<CharacterProperty>(
                CharacterPropertyComparators.VALUE);
        r.addAll(getSkills());
        return r;
    }

    @JsonIgnore
    public Set<CharacterProperty> getTopSkills(int max) {
        Set<CharacterProperty> r = new TreeSet<CharacterProperty>(
                CharacterPropertyComparators.VALUE);
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
    @Override
    public void onUpdateCorelatives(CharacterProperty ofProperty) {
        if (reltivesChanged == null) {
            return;
        }
        Set<CharacterProperty> corelatives = getCorelatives(ofProperty);
        if (corelatives != null) {
            for (CharacterProperty property : corelatives) {
                String ofPropertyName = ofProperty.getName();
                if (property != null) {
                    if (!ofPropertyName.equalsIgnoreCase(property.getName())) {
                        reltivesChanged.onUpdateRelativeProperties(property);
                    }
                }
            }
        }
    }

    @JsonIgnore
    public Set<CharacterProperty> getRelatedProperties(CharacterProperty toProperty) {
        if (toProperty == null) {
            throw new IllegalArgumentException("Passed param was null");
        }
        Collection<Relation> relations = toProperty.getRelations();
        Set<CharacterProperty> r = new HashSet<CharacterProperty>();
        toProperty.addCorelativesObserver(this);
        for (Relation relation : relations) {
            if (relation != null) {
                String propName = relation.getPropertyName();
                CharacterProperty relatedProperty = getPropertyByName(propName);
                if (relatedProperty != null) {
                    if (PropertyType.DAMAGE_BONUS.equals(relatedProperty.getType())) {
                        relatedProperty = getDamageBonus().asCharacterProperty();
                    } else if (PropertyType.HIT_POINTS.equals(relatedProperty.getType())) {
                        relatedProperty = getHitPoints().asCharacterProperty();
                    } else {
                        int value = relation.getBaseValueByRelation(toProperty.getValue());
                        relatedProperty.setValue(value);
                    }
                    r.add(relatedProperty);
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
            if (prop != null) {
                if (prop.getName().equalsIgnoreCase(name)) {
                    prop.setValue(val);
                    return true;
                }
            }
        }
        return false;
    }

    @JsonIgnore
    public int getCurrentSanity() {
        return 0;
    }

    @JsonIgnore
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
        dest.writeLong(this.presentDate);
        dest.writeInt(this.age);
        dest.writeLong(this.suggestedDate);
    }

    @JsonIgnore
    public int getHobbyPoints() {
        CharacterProperty _int = getPropertyByName(BRPStatistic.INT.name());
        if (_int != null) {
            int intVal = _int.getValue();
            return edition.getHobbySkillPointMultiplier() * intVal;
        }
        return 0;
    }

    @JsonIgnore
    public int getCareerPoints() {
        CharacterProperty _int = getPropertyByName(BRPStatistic.EDU.name());
        if (_int != null) {
            int intVal = _int.getValue();
            return edition.getCareerSkillPointMultiplier() * intVal;
        }
        return 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SavedCharacter that = (SavedCharacter) o;

        if (age != that.age) {
            return false;
        }
        if (presentDate != that.presentDate) {
            return false;
        }
        if (birth != null ? !birth.equals(that.birth) : that.birth != null) {
            return false;
        }
        if (description != null ? !description.equals(
                that.description) : that.description != null) {
            return false;
        }
        if (edition != that.edition) {
            return false;
        }
        if (fullHistory != null ? !fullHistory.equals(
                that.fullHistory) : that.fullHistory != null) {
            return false;
        }
        if (possesions != null ? !possesions.equals(that.possesions) : that.possesions != null) {
            return false;
        }
        if (properties != null ? !properties.equals(that.properties) : that.properties != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = properties != null ? properties.hashCode() : 0;
        result = 31 * result + (possesions != null ? possesions.hashCode() : 0);
        result = 31 * result + (fullHistory != null ? fullHistory.hashCode() : 0);
        result = 31 * result + (edition != null ? edition.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (birth != null ? birth.hashCode() : 0);
        result = 31 * result + (int) (presentDate ^ (presentDate >>> 32));
        result = 31 * result + age;
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

    public long getSuggestedDate() {
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
        return hitPoints;
    }

    @JsonIgnore
    public void setHitPoints(HitPoints hitPoints) {
        this.hitPoints = hitPoints;
    }

}