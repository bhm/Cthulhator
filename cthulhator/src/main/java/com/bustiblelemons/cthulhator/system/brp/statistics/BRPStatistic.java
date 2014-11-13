package com.bustiblelemons.cthulhator.system.brp.statistics;

import com.bustiblelemons.cthulhator.system.brp.skills.BRPSkill;
import com.bustiblelemons.cthulhator.system.damage.DamageBonus;
import com.bustiblelemons.cthulhator.system.dice.PointPoolFromDiceBuilder;
import com.bustiblelemons.cthulhator.system.dice.model.PointPool;
import com.bustiblelemons.cthulhator.system.dice.model.PolyhedralDice;
import com.bustiblelemons.cthulhator.system.properties.ActionGroup;
import com.bustiblelemons.cthulhator.system.properties.CharacterProperty;
import com.bustiblelemons.cthulhator.system.properties.ModifierType;
import com.bustiblelemons.cthulhator.system.properties.PropertyFormat;
import com.bustiblelemons.cthulhator.system.properties.PropertyType;
import com.bustiblelemons.cthulhator.system.properties.Relation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by bhm on 20.07.14.
 */
public enum BRPStatistic {
    STR {
        public Relation damageRelation;
        public List<Relation> relations;

        @Override
        public List<Relation> getRelations() {
            if (relations == null) {
                relations = new ArrayList<Relation>();
                damageRelation = new Relation()
                        .setPropertyName(DamageBonus.class.getSimpleName())
                        .setModifierType(ModifierType.NONE);
                relations.add(damageRelation);
            }
            return relations;
        }

    }, DEX {
        public Relation dodgeRelation;
        public List<Relation> relations;

        @Override
        public List<Relation> getRelations() {
            if (relations == null) {
                relations = new ArrayList<Relation>();
                dodgeRelation = new Relation()
                        .setPropertyName(BRPSkill.Dodge.name())
                        .setModifier(2)
                        .setModifierType(ModifierType.MULTIPLY);
                relations.add(dodgeRelation);
            }
            return relations;
        }
    }, INT {
        public Relation ideaReloation;
        public List<Relation> relations;

        @Override
        public List<Relation> getRelations() {
            if (relations == null) {
                relations = new ArrayList<Relation>();
                ideaReloation = new Relation()
                        .setPropertyName(IDEA.name())
                        .setModifier(5)
                        .setModifierType(ModifierType.MULTIPLY);
                relations.add(ideaReloation);
            }
            return relations;
        }
    }, CON {
    }, APP {
    }, POW {
        public Relation sanityRelation;
        public Relation luckRelation;
        public List<Relation> relations;

        @Override
        public List<Relation> getRelations() {
            if (relations == null) {
                relations = new ArrayList<Relation>();
                sanityRelation = new Relation()
                        .setPropertyName(SAN.name())
                        .setModifier(5)
                        .setModifierType(ModifierType.MULTIPLY);
                relations.add(sanityRelation);
                luckRelation = new Relation()
                        .setPropertyName(LUCK.name())
                        .setModifier(5)
                        .setModifierType(ModifierType.MULTIPLY);
                relations.add(luckRelation);
            }
            return relations;
        }
    }, SIZ {
        public Relation damageRelation;
        public List<Relation> relations;

        @Override
        public List<Relation> getRelations() {
            if (relations == null) {
                relations = new ArrayList<Relation>();
                damageRelation = new Relation()
                        .setPropertyName(DamageBonus.class.getSimpleName())
                        .setModifierType(ModifierType.NONE);
                relations.add(damageRelation);
            }
            return relations;
        }


    }, SAN {
        public Relation powRelation;
        public List<Relation> relations;

        @Override
        public List<Relation> getRelations() {
            if (relations == null) {
                relations = new ArrayList<Relation>();
                powRelation = new Relation()
                        .setPropertyName(POW.name())
                        .setModifier(5)
                        .setModifierType(ModifierType.MULTIPLY);
                relations.add(powRelation);
            }
            return relations;
        }
    }, EDU {
        public Relation knowledgeRelation;
        public List<Relation> relations;

        @Override
        public List<Relation> getRelations() {
            if (relations == null) {
                relations = new ArrayList<Relation>();
                knowledgeRelation = new Relation()
                        .setPropertyName(KNOW.name())
                        .setModifier(5)
                        .setModifierType(ModifierType.MULTIPLY);
                relations.add(knowledgeRelation);
            }
            return relations;
        }
    }, IDEA {
        public Relation intRelation;
        public List<Relation> relations;

        @Override
        public List<Relation> getRelations() {
            if (relations == null) {
                relations = new ArrayList<Relation>();
                intRelation = new Relation()
                        .setPropertyName(INT.name())
                        .setModifier(5)
                        .setModifierType(ModifierType.MULTIPLY);
                relations.add(intRelation);
            }
            return relations;
        }

        @Override
        public CharacterProperty asCharacterProperty() {
            CharacterProperty r = super.asCharacterProperty();
            r.setFormat(PropertyFormat.PERCENTILE);
            return r;
        }
    }, KNOW {
        public Relation eduRelation;
        public List<Relation> relations;

        @Override
        public List<Relation> getRelations() {
            if (relations == null) {
                relations = new ArrayList<Relation>();
                eduRelation = new Relation()
                        .setPropertyName(EDU.name())
                        .setModifier(5)
                        .setModifierType(ModifierType.MULTIPLY);
                relations.add(eduRelation);
            }
            return relations;
        }

        @Override
        public CharacterProperty asCharacterProperty() {
            CharacterProperty r = super.asCharacterProperty();
            r.setMaxValue(99);
            r.setFormat(PropertyFormat.PERCENTILE);
            return r;
        }
    }, LUCK {
        public Relation powRelation;
        public List<Relation> relations;

        @Override
        public List<Relation> getRelations() {
            if (relations == null) {
                relations = new ArrayList<Relation>();
                powRelation = new Relation()
                        .setPropertyName(POW.name())
                        .setModifier(5)
                        .setModifierType(ModifierType.MULTIPLY);

                relations.add(powRelation);
            }
            return relations;
        }

        @Override
        public CharacterProperty asCharacterProperty() {
            CharacterProperty r = super.asCharacterProperty();
            r.setFormat(PropertyFormat.PERCENTILE);
            return r;
        }
    };

    public List<Relation> getRelations() {
        return Collections.emptyList();
    }

    public List<ActionGroup> getActionGroups() {
        return Collections.emptyList();
    }

    public CharacterProperty asCharacterProperty() {
        CharacterProperty r = fromBRPStatistic(this);
        r.setRelations(getRelations());
        r.setActionGroup(getActionGroups());
        return r;
    }

    private CharacterProperty fromBRPStatistic(BRPStatistic statistic) {
        PointPoolFromDiceBuilder fromDice;
        switch (statistic) {
        case CON:
        case DEX:
        case STR:
        case APP:
        case POW:
            fromDice = new PointPoolFromDiceBuilder();
            fromDice.addDicePool(3, PolyhedralDice.D6);
            return fromPointPool(fromDice.build());
        case INT:
        case SIZ:
            fromDice = new PointPoolFromDiceBuilder();
            fromDice.addDicePool(2, PolyhedralDice.D6).addModifiers(6);
            return fromPointPool(fromDice.build());
        case EDU:
            fromDice = new PointPoolFromDiceBuilder();
            fromDice.addDicePool(3, PolyhedralDice.D6).addModifiers(3);
            return fromPointPool(fromDice.build());
        default:
            return new CharacterProperty();
        }
    }

    private CharacterProperty fromPointPool(PointPool p) {
        CharacterProperty property = new CharacterProperty();
        property.setMaxValue(p.getMax());
        property.setMinValue(p.getMin());
        property.setName(this.name());
        property.setFormat(PropertyFormat.NUMBER);
        property.setType(PropertyType.STATISTIC);
        return property;
    }
}
