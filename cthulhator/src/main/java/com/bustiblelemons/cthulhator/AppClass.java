package com.bustiblelemons.cthulhator;

import com.bustiblelemons.BaseApplication;
import com.bustiblelemons.cthulhator.system.brp.skills.BRPSkill;
import com.bustiblelemons.cthulhator.system.brp.statistics.BRPStatistic;
import com.bustiblelemons.utils.ResourceHelper;

/**
 * Created 9 Dec 2013
 */
public class AppClass extends BaseApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        readALlStatisticNames();
        readAllSkillNames();
    }

    private int readALlStatisticNames() {
        int r = 0;
        for (BRPStatistic statistic : BRPStatistic.values()) {
            if (statistic != null) {
                int nameResId = ResourceHelper.from(this)
                        .getIdentifierForStringByNameParts("stat", statistic.name());
                int shortNameResId = ResourceHelper.from(this)
                        .getIdentifierForStringByNameParts("stat", "short", statistic.name());
                statistic.setNameResId(nameResId);
                statistic.setShortNameResId(shortNameResId);
            }
        }
        return r;
    }

    private int readAllSkillNames() {
        int r = 0;
        for (BRPSkill skill : BRPSkill.values()) {
            if (skill != null) {
                int nameResId = ResourceHelper.from(this)
                        .getIdentifierForStringByNameParts("skill", skill.name());
                skill.setNameResId(nameResId);
                int shortNameResId = ResourceHelper.from(this)
                        .getIdentifierForStringByNameParts("skill", "short", skill.name());
                skill.setNameResId(nameResId);
                skill.setShortNameResId(shortNameResId);
            }
        }
        return r;
    }

}
