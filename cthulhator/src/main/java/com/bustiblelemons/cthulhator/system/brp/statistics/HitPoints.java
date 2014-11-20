package com.bustiblelemons.cthulhator.system.brp.statistics;

import com.bustiblelemons.cthulhator.system.edition.CthulhuEdition;
import com.bustiblelemons.cthulhator.system.properties.ActionGroup;
import com.bustiblelemons.cthulhator.system.properties.CharacterProperty;
import com.bustiblelemons.cthulhator.system.properties.ModifierType;
import com.bustiblelemons.cthulhator.system.properties.PropertyType;
import com.bustiblelemons.cthulhator.system.properties.Relation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by bhm on 20.07.14.
 */
public class HitPoints {
    private final static Collection<Relation> sRelations      = new ArrayList<Relation>();
    private static final Relation             sConStrRelation = new Relation()
            .withModifier(0)
            .withRelation(BRPStatistic.CON.name())
            .withRelation(BRPStatistic.STR.name())
            .withModifierType(ModifierType.AVERAGE);

    static {
        sRelations.add(sConStrRelation);
    }

    private int max     = 0;
    private int current = 0;
    private int min     = -2;

    public static HitPoints forProperties(CthulhuEdition edition, float con, float siz) {
        HitPoints hitPoints = new HitPoints();
        int value = getValue(con, siz, edition);
        hitPoints.setMax(value);
        hitPoints.setCurrent(value);
        hitPoints.setMin(-2);
        return hitPoints;
    }

    private static int getValue(float con, float siz, CthulhuEdition edition) {
        float sum;
        int value;
        switch (edition) {
        default:
        case CoC5:
        case CoC6:
            sum = con + siz;
            value = Math.round(sum / 2);
        }
        return value;
    }

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
        r.setType(PropertyType.HIT_POINTS);
        r.setMinValue(getMin());
        r.setMaxValue(getMax());
        r.setValue(getMax());
        r.setRelations(sRelations);
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