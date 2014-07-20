package com.bustiblelemons.cthulhator.model;

import com.bustiblelemons.cthulhator.model.brp.skills.SkillGroup;

/**
 * Created by bhm on 20.07.14.
 */
public abstract class AbsSkill implements Skill {
    private int        value;
    private String     name;
    private SkillGroup group;

    public SkillGroup getGroup() {
        return group;
    }

    public void setGroup(SkillGroup group) {
        this.group = group;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
