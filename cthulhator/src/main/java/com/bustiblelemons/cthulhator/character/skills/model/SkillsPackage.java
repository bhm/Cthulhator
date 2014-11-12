package com.bustiblelemons.cthulhator.character.skills.model;

import com.bustiblelemons.cthulhator.serialization.SerializableCollection;
import com.bustiblelemons.cthulhator.system.properties.CharacterProperty;

/**
 * Created by hiv on 11.11.14.
 */
public class SkillsPackage extends SerializableCollection<CharacterProperty> {
    private int hobbyPoints  = 0;
    private int careerPoints = 0;

    private int availableSkillPoints = -1;
    private int mMaxPoints = -1;

    public int getAvailableSkillPoints() {
        if (availableSkillPoints < 0) {
            availableSkillPoints = careerPoints + hobbyPoints;
        }
        return availableSkillPoints;
    }

    public void setAvailableSkillPoints(int availableSkillPoints) {
        this.availableSkillPoints = availableSkillPoints;
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

    public int getMaxPoints() {
        if (mMaxPoints < 0) {
            mMaxPoints = careerPoints + hobbyPoints;
        }
        return mMaxPoints;
    }

    public void setMaxPoints(int maxPoints) {
        this.mMaxPoints = maxPoints;
    }
}
