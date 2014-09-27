package com.bustiblelemons.cthulhator.model.brp.skills;

import com.bustiblelemons.cthulhator.model.ActionGroup;
import com.bustiblelemons.cthulhator.model.CharacterProperty;
import com.bustiblelemons.cthulhator.model.CthulhuEdition;
import com.bustiblelemons.cthulhator.model.ModifierType;
import com.bustiblelemons.cthulhator.model.PropertyFormat;
import com.bustiblelemons.cthulhator.model.PropertyType;
import com.bustiblelemons.cthulhator.model.Relation;
import com.bustiblelemons.cthulhator.model.brp.statistics.BRPStatistic;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by bhm on 13.09.14.
 */
public enum BRPSkill {
    Accounting {
        @Override
        public int getBaseValue(CthulhuEdition edition) {
            return 15;
        }
    },
    Anthropology,
    Archaeology,
    Art {
        @Override
        public int getBaseValue(CthulhuEdition edition) {
            return 5;
        }
    },
    Astronomy,
    Bargain {
        @Override
        public int getBaseValue(CthulhuEdition edition) {
            return 5;
        }
    },

    Biology,
    Chemistry,
    Climb {
        @Override
        public int getBaseValue(CthulhuEdition edition) {
            return 40;
        }
    },
    Conceal {
        @Override
        public int getBaseValue(CthulhuEdition edition) {
            return 15;
        }
    },
    Credit_Rating {
        @Override
        public int getBaseValue(CthulhuEdition edition) {
            return 15;
        }
    },
    CthulhuMythos,
    Dodge {
        public Relation dexRelation;
        public Set<Relation> relations;

        @Override
        public Set<Relation> getRelations() {
            if (relations == null) {
                relations = new HashSet<Relation>();
                dexRelation = new Relation()
                        .setPropertyName(BRPStatistic.DEX.name())
                        .setModifier(2)
                        .setModifierType(ModifierType.MULTIPLY);
                relations.add(dexRelation);
            }
            return relations;
        }
    },
    DriveAuto {
        @Override
        public int getBaseValue(CthulhuEdition edition) {
            return 20;
        }
    },
    ElectricalRepair {
        @Override
        public int getBaseValue(CthulhuEdition edition) {
            return 10;
        }
    },
    FastTalk {
        @Override
        public int getBaseValue(CthulhuEdition edition) {
            return 5;
        }
    },
    FirstAid {
        @Override
        public int getBaseValue(CthulhuEdition edition) {
            return 30;
        }
    },
    Geology,
    Hide {
        @Override
        public int getBaseValue(CthulhuEdition edition) {
            return 10;
        }
    },
    History {
        @Override
        public int getBaseValue(CthulhuEdition edition) {
            return 20;
        }
    },
    Jump {
        @Override
        public int getBaseValue(CthulhuEdition edition) {
            return 25;
        }
    },
    Law {
        @Override
        public int getBaseValue(CthulhuEdition edition) {
            return 5;
        }
    },
    LibraryUse {
        @Override
        public int getBaseValue(CthulhuEdition edition) {
            return 25;
        }
    },
    Listen {
        @Override
        public int getBaseValue(CthulhuEdition edition) {
            return 25;
        }
    },
    Locksmith,
    MartialArts,
    MechanicalRepair {
        @Override
        public int getBaseValue(CthulhuEdition edition) {
            return 5;
        }
    },
    Medicine {
        @Override
        public int getBaseValue(CthulhuEdition edition) {
            return 5;
        }
    },
    NaturalHistory {
        @Override
        public int getBaseValue(CthulhuEdition edition) {
            return 10;
        }
    },
    Navigate {
        @Override
        public int getBaseValue(CthulhuEdition edition) {
            return 10;
        }
    },
    Occult {
        @Override
        public int getBaseValue(CthulhuEdition edition) {
            return 5;
        }
    },
    OwnLanguage {
        public Relation eduRelation;
        public Set<Relation> relations;

        @Override
        public Set<Relation> getRelations() {
            if (relations == null) {
                relations = new HashSet<Relation>();
                eduRelation = new Relation()
                        .setPropertyName(BRPStatistic.EDU.name())
                        .setModifier(5)
                        .setModifierType(ModifierType.MULTIPLY);
                relations.add(eduRelation);
            }
            return relations;
        }
    },
    Persuade {
        @Override
        public int getBaseValue(CthulhuEdition edition) {
            return 15;
        }
    },
    Pharmacy,
    Photography {
        @Override
        public int getBaseValue(CthulhuEdition edition) {
            return 10;
        }
    },
    Physics,
    Pilot,
    Psychoanalysis,
    Ride {
        @Override
        public int getBaseValue(CthulhuEdition edition) {
            return 5;
        }
    },
    Speak {
        @Override
        public int getBaseValue(CthulhuEdition edition) {
            return 10;
        }
    },
    SpotHidden {
        @Override
        public int getBaseValue(CthulhuEdition edition) {
            return 25;
        }
    },
    Swim {
        @Override
        public int getBaseValue(CthulhuEdition edition) {
            return 25;
        }
    },
    Throw {
        @Override
        public int getBaseValue(CthulhuEdition edition) {
            return 25;
        }
    },
    Track {
        @Override
        public int getBaseValue(CthulhuEdition edition) {
            return 10;
        }
    }
    //Weapons
    ,
    Axe {
        @Override
        public int getBaseValue(CthulhuEdition edition) {
            return 20;
        }
    },
    BlackJack {
        @Override
        public int getBaseValue(CthulhuEdition edition) {
            return 40;
        }
    },
    Club {
        @Override
        public int getBaseValue(CthulhuEdition edition) {
            return 25;
        }
    },
    Knife {
        @Override
        public int getBaseValue(CthulhuEdition edition) {
            return 25;
        }
    },
    Sabre {
        @Override
        public int getBaseValue(CthulhuEdition edition) {
            return 15;
        }
    },
    Sword {
        @Override
        public int getBaseValue(CthulhuEdition edition) {
            return 20;
        }
    },
    Handgun {
        @Override
        public int getBaseValue(CthulhuEdition edition) {
            return 20;
        }
    },
    MachineGun {
        @Override
        public int getBaseValue(CthulhuEdition edition) {
            return 15;
        }
    },
    Rifle {
        @Override
        public int getBaseValue(CthulhuEdition edition) {
            return 25;
        }
    },
    Shotgun {
        @Override
        public int getBaseValue(CthulhuEdition edition) {
            return 30;
        }
    },
    SubmashineGun {
        @Override
        public int getBaseValue(CthulhuEdition edition) {
            return 15;
        }
    };
    private Set<CthulhuEdition> editions = new HashSet<CthulhuEdition>();

    {
        editions.add(CthulhuEdition.CoC5);
    }

    public Set<Relation> getRelations() {
        return Collections.emptySet();
    }

    public CharacterProperty asCharacterProperty(CthulhuEdition edition) {
        CharacterProperty r = new CharacterProperty();
        r.setName(name());
        r.setFormat(PropertyFormat.PERCENTILE);
        r.setType(PropertyType.SKILL);
        r.setMaxValue(100);
        r.setMinValue(0);
        r.setRelations(getRelations());
        r.setActionGroup(getActionGroups());
        r.setBaseValue(getBaseValue(edition));
        return r;
    }

    public int getBaseValue(CthulhuEdition edition) {
        if (CthulhuEdition.CoC6.equals(edition)) {
            return 1;
        } else {
            return 0;
        }
    }

    public Set<CthulhuEdition> getEditions() {
        if (editions == null) {
            editions = new HashSet<CthulhuEdition>();
            editions.add(CthulhuEdition.CoC5);
        }
        return editions;
    }

    public List<ActionGroup> getActionGroups() {
        return Collections.emptyList();
    }
}
