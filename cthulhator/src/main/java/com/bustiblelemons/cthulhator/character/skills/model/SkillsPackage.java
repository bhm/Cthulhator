package com.bustiblelemons.cthulhator.character.skills.model;

import com.bustiblelemons.cthulhator.serialization.SerializableCollection;
import com.bustiblelemons.cthulhator.system.properties.CharacterProperty;

/**
 * Created by hiv on 11.11.14.
 */
public class SkillsPackage extends SerializableCollection<CharacterProperty> {
    private int hobbyPoints  = 0;
    private int careerPoints = 0;

    private int skillPoints = -1;

    public int getSkillPoints() {
        if (skillPoints < 0) {
            skillPoints = careerPoints + hobbyPoints;
        }
        return skillPoints;
    }

    public void setSkillPoints(int skillPoints) {
        this.skillPoints = skillPoints;
    }

    public int getHobbyPoints() {
        return hobbyPoints;
    }

    public void setHobbyPoints(int hobbyPoints) {
        this.hobbyPoints = hobbyPoints;
    }

    public int getCareerPoints() {
        return careerPoints;
    }

    public void setCareerPoints(int careerPoints) {
        this.careerPoints = careerPoints;
    }
}
