package com.bustiblelemons.cthulhator.model.brp;

import com.bustiblelemons.cthulhator.model.CharacterProperty;
import com.bustiblelemons.cthulhator.model.CthulhuCharacter;
import com.bustiblelemons.cthulhator.model.ICharacter;
import com.bustiblelemons.cthulhator.model.Skill;

/**
 * Created 5 Nov 2013
 */
public abstract class BRPCharacter extends CthulhuCharacter implements ICharacter {

    private CoCEdition edition;

    public CoCEdition getEdition() {
        return edition;
    }

    public void setEdition(CoCEdition edition) {
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

    @Override
    public int getCurrentSanity() {
        return 0;
    }

    @Override
    public int getMaxSanity() {
        return 0;
    }

}