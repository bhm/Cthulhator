package com.bustiblelemons.cthulhator.character.persistance;

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
import com.bustiblelemons.cthulhator.system.damage.DamageBonusFactory;
import com.bustiblelemons.cthulhator.system.edition.GameEdition;
import com.bustiblelemons.cthulhator.system.properties.CharacterProperty;
import com.bustiblelemons.cthulhator.system.properties.PropertyType;
import com.bustiblelemons.cthulhator.system.properties.PropertyValueRetreiver;
import com.bustiblelemons.cthulhator.system.properties.Relation;
import com.bustiblelemons.cthulhator.system.time.YearsPeriodImpl;
import com.bustiblelemons.randomuserdotme.model.Location;
import com.bustiblelemons.randomuserdotme.model.Name;
import com.fasterxml.jackson.annotation.JsonIgnore;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by hiv on 07.01.15.
 */
public class CharacterWrappper extends SavedCharacter implements PropertyValueRetreiver {

    static {
        sShouldHaveAssignedAtLeast = BRPStatistic.values().length / 5;
    }

    public static final Parcelable.Creator<CharacterWrappper>        CREATOR                   =
            new Parcelable.Creator<CharacterWrappper>() {
                public CharacterWrappper createFromParcel(Parcel source) {
                    return new CharacterWrappper(source);
                }

                public CharacterWrappper[] newArray(int size) {
                    return new CharacterWrappper[size];
                }
            };
    private static      LruCache<CharacterProperty, List<Possesion>> cachedAffectedPossessions =
            new LruCache<CharacterProperty, List<Possesion>>(20);

    private static YearsPeriodImpl sDefaultPeriod             = YearsPeriodImpl.JAZZAGE;
    private static int             sShouldHaveAssignedAtLeast = 2;

    @JsonIgnore
    private long suggestedDate        = Long.MIN_VALUE;
    @JsonIgnore
    private int  skillPointsAvailable = -1;
    @JsonIgnore
    private int  careerPoints         = -1;
    @JsonIgnore
    private int  hobbyPoints          = -1;
    @JsonIgnore
    private Sanity sanity;

    public CharacterWrappper(Parcel in) {
        super(in);
        this.suggestedDate = in.readLong();
        this.skillPointsAvailable = in.readInt();
    }

    public CharacterWrappper(SavedCharacter character) {
        super.setId(character.getId());
        super.setProperties(character.getProperties());
        super.setDescription(character.getDescription());
        super.setPeriod(character.getPeriod());
        super.setBirth(character.getBirth());
        super.setAge(character.getAge());
        super.setFullHistory(character.getFullHistory());
        super.setPossesions(character.getPossesions());
        setEdition(character.getEdition());
    }

    public CharacterWrappper(GameEdition edition) {
        setEdition(edition);
    }

    @JsonIgnore
    public int updatSecondaryStatistics() {
        return updateRelatedProperties(getStatistics());
    }

    @JsonIgnore
    private void setupAgeAndBirth() {
        int edu = getStatisticValue(BRPStatistic.EDU.name());
        int year = getPeriod().getDefaultYear();
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
        if (getEdition() != null) {
            fillSkillPointPools(getEdition());
        }
    }

    @JsonIgnore
    private void fillSkillPointPools(GameEdition edition) {
        fillCareerPoints(edition);
        fillHobbyPoints(edition);
    }

    private void fillHobbyPoints(GameEdition edition) {
        CharacterProperty edu = getPropertyByName(BRPStatistic.EDU.name());
        if (edu != null) {
            CharacterProperty pointsProperty = BRPSkillPointPools.CAREER.asProperty();
            int hobbyPointsValue = edu.getValue() * edition.getCareerSkillPointMultiplier();
            pointsProperty.setValue(hobbyPointsValue);
            addCharacterProperty(pointsProperty);
        }
    }

    @JsonIgnore
    private void fillCareerPoints(GameEdition edition) {
        CharacterProperty __int = getPropertyByName(BRPStatistic.INT.name());
        if (__int != null) {
            CharacterProperty pointsProperty = BRPSkillPointPools.HOBBY.asProperty();
            int hobbyPointsValue = __int.getValue() * edition.getCareerSkillPointMultiplier();
            pointsProperty.setValue(hobbyPointsValue);
            addCharacterProperty(pointsProperty);
        }
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
        if (properties == null || properties.isEmpty()) {
            addPropertiesList(getEdition().getCharacteristics());
        }
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

    public void setEdition(GameEdition edition) {
        if (edition == null) {
            return;
        }
        super.setEdition(edition);
        fillStatistics();
        addDamageBonus();
        addHitPoints();
        updatSecondaryStatistics();
        fillSkillsList();
        updateSkillPointPools();
        setupAgeAndBirth();
    }

    private void addHitPoints() {
        int con = getStatisticValue(BRPStatistic.CON.name());
        int siz = getStatisticValue(BRPStatistic.SIZ.name());
        CharacterProperty property = HitPoints.forProperties(getEdition(), con, siz)
                .asCharacterProperty();
        addCharacterProperty(property);
    }

    private void addDamageBonus() {
        int con = getStatisticValue(BRPStatistic.CON.name());
        int siz = getStatisticValue(BRPStatistic.SIZ.name());
        CharacterProperty damageBonus = DamageBonusFactory.forEdition(getEdition(), con, siz)
                .asCharacterProperty();
        addCharacterProperty(damageBonus);
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

    @JsonIgnore
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeLong(this.suggestedDate);
        dest.writeInt(this.skillPointsAvailable);
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
                hobbyPoints = getEdition().getHobbySkillPointMultiplier() * intVal;
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
                careerPoints = getEdition().getCareerSkillPointMultiplier() * intVal;
            }
        }
        return careerPoints;
    }

    @Override
    public int onRetreivePropertValue(String propertyName) {
        for (CharacterProperty property : properties) {
            if (property != null && property.nameMatches(propertyName)) {
                return property.getValue();
            }
        }
        return 0;
    }

    @JsonIgnore
    public String getName() {
        return getDescription() != null && getDescription().getName() != null ? getDescription().getName().getFullName() : null;
    }

    @JsonIgnore
    public String getPhotoUrl() {
        if (getDescription() != null
                && getDescription().getMainPortrait() != null) {
            return getDescription().getMainPortrait().getUrl();
        }
        return null;
    }

    public List<Portrait> getPortraits() {
        return getDescription() != null ? getDescription().getPortraitList() : new ArrayList<Portrait>();
    }

    public void setPortraits(List<Portrait> portraits) {
        if (getDescription() == null) {
            setDescription(new CharacterDescription());
        }
        getDescription().setPortraitList(portraits);
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


    @Override
    public YearsPeriodImpl getPeriod() {
        YearsPeriodImpl period = super.getPeriod();
        return period == null ? sDefaultPeriod : period;
    }

    public void setPeriod(YearsPeriodImpl period) {
        super.setPeriod(period);
        setupAgeAndBirth();
    }

    @JsonIgnore
    public Name getNameObject() {
        return getDescription() != null ? getDescription().getName() : null;
    }


    @JsonIgnore
    public Sanity getSanity() {
        return getSanity(false);
    }

    @JsonIgnore
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


    @JsonIgnore
    public List<HistoryEvent> getHistoryEvents(long tillDate, Location around) {
        List<HistoryEvent> r = getHistoryEvents(tillDate);
        //TODO Filter by location long/lat and distance toleration according to times
        return r;
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

    public static CharacterWrappper from(SavedCharacter character) {
        return new CharacterWrappper(character);
    }
}
