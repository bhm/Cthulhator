package com.bustiblelemons.cthulhator.system.brp.statistics;

import com.bustiblelemons.cthulhator.system.properties.ActionGroup;
import com.bustiblelemons.cthulhator.system.properties.CharacterProperty;
import com.bustiblelemons.cthulhator.system.properties.PropertyType;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bhm on 20.07.14.
 */
public class HitPoints {
    private int max;
    private int current;
    private int min;

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public int getCurrent() {
        return current;
    }

    public void setCurrent(int current) {
        this.current = current;
    }

    public CharacterProperty asCharacterProperty() {
        CharacterProperty r = new CharacterProperty();
        r.setDisplayName(toString());
        r.setType(PropertyType.DAMAGE_BONUS);
        r.setMinValue(getMin());
        r.setMaxValue(getMax());
        r.setName(HitPoints.class.getSimpleName());
        List<ActionGroup> g = new ArrayList<ActionGroup>();
        g.add(ActionGroup.COMBAT);
        r.setActionGroup(g);
        return r;
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }
}