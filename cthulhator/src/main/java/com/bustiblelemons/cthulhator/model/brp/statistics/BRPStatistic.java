package com.bustiblelemons.cthulhator.model.brp.statistics;

import com.bustiblelemons.cthulhator.model.BRPStatCharacterProperty;
import com.bustiblelemons.cthulhator.model.CharacterProperty;
import com.bustiblelemons.cthulhator.model.ModifierType;
import com.bustiblelemons.cthulhator.model.Relation;
import com.bustiblelemons.cthulhator.model.skills.BRPSkills;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by bhm on 20.07.14.
 */
public enum BRPStatistic {
    STR {
    }, DEX {
        public final Relation DodgeRelation = new Relation()
                .setPropertyName(BRPSkills.Dodge.name())
                .setModifier(2)
                .setModifierType(ModifierType.MULTIPLY);

        @Override
        public List<Relation> getRelations() {
            List<Relation> r = new ArrayList<Relation>();
            r.add(DodgeRelation);
            return r;
        }
    }, INT {
        public Relation ideaReloation = new Relation()
                .setPropertyName(IDEA.name())
                .setModifier(5)
                .setModifierType(ModifierType.MULTIPLY);

        @Override
        public List<Relation> getRelations() {
            List<Relation> r = new ArrayList<Relation>();
            r.add(ideaReloation);
            return r;
        }
    }, CON {
    }, APP {
    }, POW {
        public final Relation sanityRelation = new Relation()
                .setPropertyName(SAN.name())
                .setModifier(5)
                .setModifierType(ModifierType.MULTIPLY);
        public final Relation luckRelation = new Relation()
                .setPropertyName(LUCK.name())
                .setModifier(5)
                .setModifierType(ModifierType.MULTIPLY);

        @Override
        public List<Relation> getRelations() {
            List<Relation> r = new ArrayList<Relation>();
            r.add(sanityRelation);
            r.add(luckRelation);
            return r;
        }
    }, SIZ {
    }, SAN {public Relation powRelation = new Relation()
            .setPropertyName(POW.name())
            .setModifier(5)
            .setModifierType(ModifierType.MULTIPLY);

        @Override
        public List<Relation> getRelations() {
            List<Relation> r = new ArrayList<Relation>();
            r.add(powRelation);
            return r;
        }
    }, EDU {
        public Relation knowledgeRelation = new Relation()
                .setPropertyName(KNOW.name())
                .setModifier(5)
                .setModifierType(ModifierType.MULTIPLY);

        @Override
        public List<Relation> getRelations() {
            List<Relation> r = new ArrayList<Relation>();
            r.add(knowledgeRelation);
            return r;
        }
    }, IDEA {
        public Relation intRelation = new Relation()
                .setPropertyName(INT.name())
                .setModifier(5)
                .setModifierType(ModifierType.MULTIPLY);

        @Override
        public List<Relation> getRelations() {
            List<Relation> r = new ArrayList<Relation>();
            r.add(intRelation);
            return r;
        }
    }, KNOW {
        public Relation eduRelation = new Relation()
                .setPropertyName(EDU.name())
                .setModifier(5)
                .setModifierType(ModifierType.MULTIPLY);

        @Override
        public List<Relation> getRelations() {
            List<Relation> r = new ArrayList<Relation>();
            r.add(eduRelation);
            return r;
        }
    }, LUCK {
        public Relation powRelation = new Relation()
                .setPropertyName(POW.name())
                .setModifier(5)
                .setModifierType(ModifierType.MULTIPLY);

        @Override
        public List<Relation> getRelations() {
            List<Relation> r = new ArrayList<Relation>();
            r.add(powRelation);
            return r;
        }
    };

    public List<Relation> getRelations() {
        return Collections.emptyList();
    }

    public CharacterProperty asCharacterProperty() {
        return BRPStatCharacterProperty.fromStatistic(this);
    }
}
