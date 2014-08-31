package com.bustiblelemons.cthulhator.model.brp;

import com.bustiblelemons.cthulhator.model.CharacterProperty;
import com.bustiblelemons.cthulhator.model.CthulhuCharacter;
import com.bustiblelemons.cthulhator.model.CthulhuEdition;
import com.bustiblelemons.cthulhator.model.ICharacter;
import com.bustiblelemons.cthulhator.model.Skill;
import com.bustiblelemons.cthulhator.model.brp.statistics.BRPStatistic;

/**
 * Created 5 Nov 2013
 */
public abstract class AbsBRPCharacter extends CthulhuCharacter implements ICharacter {

    private CthulhuEdition edition;

    public CthulhuEdition getEdition() {
        return edition;
    }

    public void setEdition(CthulhuEdition edition) {
        this.edition = edition;
    }

    @Override
    public int getSkillValue(Skill soughtSkill) {
        CharacterProperty skill = getSkill(soughtSkill.getName());
        return skill != null ? skill.getValue() : -1;
    }

    @Override
    public int getStatisticValue(String soughtStatistic) {
        CharacterProperty stat = getStatistic(soughtStatistic);
        return stat != null ? stat.getValue() : -1;
    }

    protected boolean setPropertyValue(String name, int dex) {
        for (CharacterProperty prop : getProperties()) {
            if (prop != null) {
                if (prop.getName().equalsIgnoreCase(name)) {
                    prop.setValue(dex);
                    return true;
                }
            }
        }
        return false;
    }

    public int getCarrierSkillPoints() {
        return edition != null ? edition.getCarrierSkillPointMultiplier() * getEdu() : 0;
    }

    public int getHobbySkillPoints() {
        return edition != null ? edition.getHobbySkillPointMultiplier() * getInt() : 0;
    }

    public int getTotalSkillPoints() {
        return getCarrierSkillPoints() + getHobbySkillPoints();
    }

    @Override
    public int getCurrentSanity() {
        return 0;
    }

    @Override
    public int getMaxSanity() {
        return 0;
    }

    public boolean setDexterity(int dex) {
        return setPropertyValue(BRPStatistic.DEX.name(), dex);
    }

    public boolean setEducation(int edu) {
        return setPropertyValue(BRPStatistic.EDU.name(), edu);
    }

    public boolean setIntelligence(int intellingence) {
        return setPropertyValue(BRPStatistic.INT.name(), intellingence);
    }

    public boolean setAppearance(int app) {
        return setPropertyValue(BRPStatistic.APP.name(), app);
    }

    public boolean setSize(int size) {
        return setPropertyValue(BRPStatistic.SIZ.name(), size);
    }

    public boolean setStrength(int str) {
        return setPropertyValue(BRPStatistic.STR.name(), str);
    }

    public boolean setPower(int pow) {
        return setPropertyValue(BRPStatistic.POW.name(), pow);
    }

    public boolean setSanity(int san) {
        return setPropertyValue(BRPStatistic.SAN.name(), san);
    }

    public boolean setIdea(int idea) {
        return setPropertyValue(BRPStatistic.IDEA.name(), idea);
    }

    public boolean setKnowledg(int know) {
        return setPropertyValue(BRPStatistic.KNOW.name(), know);
    }

    public int getInt() {
        CharacterProperty p = getStatistic(BRPStatistic.INT.name());
        return p != null ? p.getValue() : 0;
    }

    public int getDex() {
        CharacterProperty p = getStatistic(BRPStatistic.DEX.name());
        return p != null ? p.getValue() : 0;
    }

    public int getEdu() {
        CharacterProperty p = getStatistic(BRPStatistic.EDU.name());
        return p != null ? p.getValue() : 0;
    }

    public int getStr() {
        CharacterProperty p = getStatistic(BRPStatistic.STR.name());
        return p != null ? p.getValue() : 0;
    }

    public int getSize() {
        CharacterProperty p = getStatistic(BRPStatistic.SIZ.name());
        return p != null ? p.getValue() : 0;
    }

    public int getPow() {
        CharacterProperty p = getStatistic(BRPStatistic.POW.name());
        return p != null ? p.getValue() : 0;
    }

    public int getAppearance() {
        CharacterProperty p = getStatistic(BRPStatistic.APP.name());
        return p != null ? p.getValue() : 0;
    }
}