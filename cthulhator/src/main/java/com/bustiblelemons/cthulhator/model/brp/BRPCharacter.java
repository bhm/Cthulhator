package com.bustiblelemons.cthulhator.model.brp;

import com.bustiblelemons.cthulhator.model.ICharacter;
import com.bustiblelemons.cthulhator.model.Skill;
import com.bustiblelemons.cthulhator.model.brp.items.Weapon;
import com.bustiblelemons.cthulhator.model.brp.skills.BRPSkill;
import com.bustiblelemons.cthulhator.model.brp.statistics.HitPoints;
import com.bustiblelemons.cthulhator.model.brp.statistics.MagicPoints;
import com.bustiblelemons.cthulhator.model.brp.statistics.Sanity;
import com.bustiblelemons.cthulhator.model.brp.statistics.Statistic;

import java.util.ArrayList;
import java.util.List;

/**
 * Created 5 Nov 2013
 */
public abstract class BRPCharacter implements ICharacter {

    private CoCEdition edition;

    private String name;
    private String secondName;

    private List<Statistic> statistics = new ArrayList<Statistic>();

    private List<BRPSkill> skills = new ArrayList<BRPSkill>();

    private Sanity      sanity;
    private HitPoints   hitPoints;
    private MagicPoints magicPoints;

    private List<Weapon> weapons = new ArrayList<Weapon>();

    @Override
    public int getSkillValue(Skill s) {
        for (BRPSkill skill : skills) {
            if (skill != null) {
                if (skill.getName().equalsIgnoreCase(s.getName())) {
                    return skill.getValue();
                }
            }
        }
        return -1;
    }

    @Override
    public int getStatisticValue(String name) {
        for (Statistic statistic : statistics) {
            if (statistic != null) {
                if (statistic.getName().equalsIgnoreCase(name)) {
                    return statistic.getValue();
                }
            }
        }
        return -1;
    }

    @Override
    public int getCurrentSanity() {
        return sanity.getCurrent();
    }

    @Override
    public int getMaxSanity() {
        return sanity.getMax();
    }
}