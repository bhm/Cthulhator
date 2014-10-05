package com.bustiblelemons.cthulhator.system.properties;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bhm on 20.07.14.
 */
public enum ActionGroup {
    COMBAT, MAGIC, SOCIAL, INVESTIGATION, OTHER;

    private static ActionGroup[] mValuesWithoutOther;

    static {
        List<ActionGroup> temp = new ArrayList<ActionGroup>();
        for (ActionGroup g : values()) {
            if (!OTHER.equals(g)) {
                temp.add(g);
            }
        }
        mValuesWithoutOther = temp.toArray(new ActionGroup[values().length - 1]);
        temp = null;
    }

    public static final ActionGroup[] valuesWithoutOther() {
        return mValuesWithoutOther;
    }
}
