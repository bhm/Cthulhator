package com.bustiblelemons.cthulhator.system.brp.skills;

import com.bustiblelemons.cthulhator.system.brp.statistics.BRPStatistic;
import com.bustiblelemons.cthulhator.system.edition.GameEdition;
import com.bustiblelemons.cthulhator.system.properties.ActionGroup;
import com.bustiblelemons.cthulhator.system.properties.CharacterProperty;
import com.bustiblelemons.cthulhator.system.properties.ModifierType;
import com.bustiblelemons.cthulhator.system.properties.PropertyFormat;
import com.bustiblelemons.cthulhator.system.properties.PropertyType;
import com.bustiblelemons.cthulhator.system.properties.Relation;

import java.util.ArrayList;
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
        public int getBaseValue(GameEdition edition) {
            return 15;
        }
    },
    Anthropology {
        private List<ActionGroup> mActionGroups;

        @Override
        public List<ActionGroup> getActionGroups() {
            if (mActionGroups == null) {
                mActionGroups = new ArrayList<ActionGroup>();
                mActionGroups.add(ActionGroup.INVESTIGATION);
            }
            return mActionGroups;
        }
    },
    Archaeology {
        private List<ActionGroup> mActionGroups;

        @Override
        public List<ActionGroup> getActionGroups() {
            if (mActionGroups == null) {
                mActionGroups = new ArrayList<ActionGroup>();
                mActionGroups.add(ActionGroup.INVESTIGATION);
            }
            return mActionGroups;
        }
    },
    Art {
        private List<ActionGroup> mActionGroups;

        @Override
        public List<ActionGroup> getActionGroups() {
            if (mActionGroups == null) {
                mActionGroups = new ArrayList<ActionGroup>();
                mActionGroups.add(ActionGroup.SOCIAL);
            }
            return mActionGroups;
        }

        @Override
        public int getBaseValue(GameEdition edition) {
            return 5;
        }
    },
    Astronomy {
        private List<ActionGroup> mActionGroups;

        @Override
        public List<ActionGroup> getActionGroups() {
            if (mActionGroups == null) {
                mActionGroups = new ArrayList<ActionGroup>();
                mActionGroups.add(ActionGroup.INVESTIGATION);
            }
            return mActionGroups;
        }
    },
    Bargain {
        private List<ActionGroup> mActionGroups;

        @Override
        public List<ActionGroup> getActionGroups() {
            if (mActionGroups == null) {
                mActionGroups = new ArrayList<ActionGroup>();
                mActionGroups.add(ActionGroup.SOCIAL);
            }
            return mActionGroups;
        }

        @Override
        public int getBaseValue(GameEdition edition) {
            return 5;
        }
    },
    Biology {
        private List<ActionGroup> mActionGroups;

        @Override
        public List<ActionGroup> getActionGroups() {
            if (mActionGroups == null) {
                mActionGroups = new ArrayList<ActionGroup>();
                mActionGroups.add(ActionGroup.MAGIC);
            }
            return mActionGroups;
        }
    },
    Chemistry {
        private List<ActionGroup> mActionGroups;

        @Override
        public List<ActionGroup> getActionGroups() {
            if (mActionGroups == null) {
                mActionGroups = new ArrayList<ActionGroup>();
                mActionGroups.add(ActionGroup.INVESTIGATION);
            }
            return mActionGroups;
        }
    },
    Climb {
        @Override
        public int getBaseValue(GameEdition edition) {
            return 40;
        }
    },
    Conceal {
        @Override
        public int getBaseValue(GameEdition edition) {
            return 15;
        }
    },
    Credit_Rating {
        @Override
        public int getBaseValue(GameEdition edition) {
            return 15;
        }
    },
    CthulhuMythos {
        private List<ActionGroup> mActionGroups;

        @Override
        public List<ActionGroup> getActionGroups() {
            if (mActionGroups == null) {
                mActionGroups = new ArrayList<ActionGroup>();
                mActionGroups.add(ActionGroup.MAGIC);
            }
            return mActionGroups;
        }
    },
    Dodge {
        private List<ActionGroup> mActionGroups;

        @Override
        public List<ActionGroup> getActionGroups() {
            if (mActionGroups == null) {
                mActionGroups = new ArrayList<ActionGroup>();
                mActionGroups.add(ActionGroup.COMBAT);
            }
            return mActionGroups;
        }

        public Relation dexRelation;
        public Set<Relation> relations;

        @Override
        public Set<Relation> getRelations() {
            if (relations == null) {
                relations = new HashSet<Relation>();
                dexRelation = new Relation()
                        .addPropertyName(BRPStatistic.DEX.name())
                        .setModifier(2)
                        .setModifierType(ModifierType.MULTIPLY);
                relations.add(dexRelation);
            }
            return relations;
        }
    },
    DriveAuto {
        @Override
        public int getBaseValue(GameEdition edition) {
            return 20;
        }
    },
    ElectricalRepair {
        @Override
        public int getBaseValue(GameEdition edition) {
            return 10;
        }
    },
    FastTalk {
        private List<ActionGroup> mActionGroups;

        @Override
        public List<ActionGroup> getActionGroups() {
            if (mActionGroups == null) {
                mActionGroups = new ArrayList<ActionGroup>();
                mActionGroups.add(ActionGroup.SOCIAL);
            }
            return mActionGroups;
        }

        @Override
        public int getBaseValue(GameEdition edition) {
            return 5;
        }
    },
    FirstAid {
        private List<ActionGroup> mActionGroups;

        @Override
        public List<ActionGroup> getActionGroups() {
            if (mActionGroups == null) {
                mActionGroups = new ArrayList<ActionGroup>();
                mActionGroups.add(ActionGroup.COMBAT);
            }
            return mActionGroups;
        }

        @Override
        public int getBaseValue(GameEdition edition) {
            return 30;
        }
    },
    Geology,
    Hide {
        @Override
        public int getBaseValue(GameEdition edition) {
            return 10;
        }
    },
    History {
        private List<ActionGroup> mActionGroups;

        @Override
        public List<ActionGroup> getActionGroups() {
            if (mActionGroups == null) {
                mActionGroups = new ArrayList<ActionGroup>();
                mActionGroups.add(ActionGroup.INVESTIGATION);
            }
            return mActionGroups;
        }

        @Override
        public int getBaseValue(GameEdition edition) {
            return 20;
        }
    },
    Jump {
        @Override
        public int getBaseValue(GameEdition edition) {
            return 25;
        }
    },
    Law {
        private List<ActionGroup> mActionGroups;

        @Override
        public List<ActionGroup> getActionGroups() {
            if (mActionGroups == null) {
                mActionGroups = new ArrayList<ActionGroup>();
                mActionGroups.add(ActionGroup.INVESTIGATION);
            }
            return mActionGroups;
        }

        @Override
        public int getBaseValue(GameEdition edition) {
            return 5;
        }
    },
    LibraryUse {
        private List<ActionGroup> mActionGroups;

        @Override
        public List<ActionGroup> getActionGroups() {
            if (mActionGroups == null) {
                mActionGroups = new ArrayList<ActionGroup>();
                mActionGroups.add(ActionGroup.INVESTIGATION);
            }
            return mActionGroups;
        }

        @Override
        public int getBaseValue(GameEdition edition) {
            return 25;
        }
    },
    Listen {
        private List<ActionGroup> mActionGroups;

        @Override
        public List<ActionGroup> getActionGroups() {
            if (mActionGroups == null) {
                mActionGroups = new ArrayList<ActionGroup>();
                mActionGroups.add(ActionGroup.INVESTIGATION);
            }
            return mActionGroups;
        }

        @Override
        public int getBaseValue(GameEdition edition) {
            return 25;
        }
    },
    Locksmith,
    MartialArts {
        private List<ActionGroup> mActionGroups;

        @Override
        public List<ActionGroup> getActionGroups() {
            if (mActionGroups == null) {
                mActionGroups = new ArrayList<ActionGroup>();
                mActionGroups.add(ActionGroup.COMBAT);
            }
            return mActionGroups;
        }

    },
    MechanicalRepair {
        @Override
        public int getBaseValue(GameEdition edition) {
            return 5;
        }
    },
    Medicine {
        private List<ActionGroup> mActionGroups;

        @Override
        public List<ActionGroup> getActionGroups() {
            if (mActionGroups == null) {
                mActionGroups = new ArrayList<ActionGroup>();
                mActionGroups.add(ActionGroup.INVESTIGATION);
            }
            return mActionGroups;
        }

        @Override
        public int getBaseValue(GameEdition edition) {
            return 5;
        }
    },
    NaturalHistory {
        @Override
        public int getBaseValue(GameEdition edition) {
            return 10;
        }
    },
    Navigate {
        @Override
        public int getBaseValue(GameEdition edition) {
            return 10;
        }
    },
    Occult {
        private List<ActionGroup> mActionGroups;

        @Override
        public List<ActionGroup> getActionGroups() {
            if (mActionGroups == null) {
                mActionGroups = new ArrayList<ActionGroup>();
                mActionGroups.add(ActionGroup.MAGIC);
            }
            return mActionGroups;
        }

        @Override
        public int getBaseValue(GameEdition edition) {
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
                        .addPropertyName(BRPStatistic.EDU.name())
                        .setModifier(5)
                        .setModifierType(ModifierType.MULTIPLY);
                relations.add(eduRelation);
            }
            return relations;
        }
    },
    Persuade {
        @Override
        public int getBaseValue(GameEdition edition) {
            return 15;
        }
    },
    Pharmacy,
    Photography {
        @Override
        public int getBaseValue(GameEdition edition) {
            return 10;
        }
    },
    Physics,
    Pilot,
    Psychoanalysis,
    Ride {
        @Override
        public int getBaseValue(GameEdition edition) {
            return 5;
        }
    },
    Speak {
        @Override
        public int getBaseValue(GameEdition edition) {
            return 10;
        }
    },
    SpotHidden {
        @Override
        public int getBaseValue(GameEdition edition) {
            return 25;
        }
    },
    Swim {
        @Override
        public int getBaseValue(GameEdition edition) {
            return 25;
        }
    },
    Throw {
        @Override
        public int getBaseValue(GameEdition edition) {
            return 25;
        }
    },
    Track {
        @Override
        public int getBaseValue(GameEdition edition) {
            return 10;
        }
    }
    //Weapons
    ,
    Axe {
        private List<ActionGroup> mActionGroups;

        @Override
        public List<ActionGroup> getActionGroups() {
            if (mActionGroups == null) {
                mActionGroups = new ArrayList<ActionGroup>();
                mActionGroups.add(ActionGroup.OTHER);
            }
            return mActionGroups;
        }


        @Override
        public int getBaseValue(GameEdition edition) {
            return 20;
        }
    },
    BlackJack {
        private List<ActionGroup> mActionGroups;

        @Override
        public List<ActionGroup> getActionGroups() {
            if (mActionGroups == null) {
                mActionGroups = new ArrayList<ActionGroup>();
                mActionGroups.add(ActionGroup.OTHER);
            }
            return mActionGroups;
        }

        @Override
        public int getBaseValue(GameEdition edition) {
            return 40;
        }
    },
    Club {
        private List<ActionGroup> mActionGroups;

        @Override
        public List<ActionGroup> getActionGroups() {
            if (mActionGroups == null) {
                mActionGroups = new ArrayList<ActionGroup>();
                mActionGroups.add(ActionGroup.OTHER);
            }
            return mActionGroups;
        }


        @Override
        public int getBaseValue(GameEdition edition) {
            return 25;
        }
    },
    Knife {
        private List<ActionGroup> mActionGroups;

        @Override
        public List<ActionGroup> getActionGroups() {
            if (mActionGroups == null) {
                mActionGroups = new ArrayList<ActionGroup>();
                mActionGroups.add(ActionGroup.OTHER);
            }
            return mActionGroups;
        }


        @Override
        public int getBaseValue(GameEdition edition) {
            return 25;
        }
    },
    Sabre {
        private List<ActionGroup> mActionGroups;

        @Override
        public List<ActionGroup> getActionGroups() {
            if (mActionGroups == null) {
                mActionGroups = new ArrayList<ActionGroup>();
                mActionGroups.add(ActionGroup.OTHER);
            }
            return mActionGroups;
        }


        @Override
        public int getBaseValue(GameEdition edition) {
            return 15;
        }
    },
    Sword {
        private List<ActionGroup> mActionGroups;

        @Override
        public List<ActionGroup> getActionGroups() {
            if (mActionGroups == null) {
                mActionGroups = new ArrayList<ActionGroup>();
                mActionGroups.add(ActionGroup.OTHER);
            }
            return mActionGroups;
        }


        @Override
        public int getBaseValue(GameEdition edition) {
            return 20;
        }
    },
    Handgun {
        private List<ActionGroup> mActionGroups;

        @Override
        public List<ActionGroup> getActionGroups() {
            if (mActionGroups == null) {
                mActionGroups = new ArrayList<ActionGroup>();
                mActionGroups.add(ActionGroup.OTHER);
            }
            return mActionGroups;
        }


        @Override
        public int getBaseValue(GameEdition edition) {
            return 20;
        }
    },
    MachineGun {
        private List<ActionGroup> mActionGroups;

        @Override
        public List<ActionGroup> getActionGroups() {
            if (mActionGroups == null) {
                mActionGroups = new ArrayList<ActionGroup>();
                mActionGroups.add(ActionGroup.OTHER);
            }
            return mActionGroups;
        }


        @Override
        public int getBaseValue(GameEdition edition) {
            return 15;
        }
    },
    Rifle {
        private List<ActionGroup> mActionGroups;

        @Override
        public List<ActionGroup> getActionGroups() {
            if (mActionGroups == null) {
                mActionGroups = new ArrayList<ActionGroup>();
                mActionGroups.add(ActionGroup.OTHER);
            }
            return mActionGroups;
        }

        @Override
        public int getBaseValue(GameEdition edition) {
            return 25;
        }
    },
    Shotgun {
        private List<ActionGroup> mActionGroups;

        @Override
        public List<ActionGroup> getActionGroups() {
            if (mActionGroups == null) {
                mActionGroups = new ArrayList<ActionGroup>();
                mActionGroups.add(ActionGroup.OTHER);
            }
            return mActionGroups;
        }

        @Override
        public int getBaseValue(GameEdition edition) {
            return 30;
        }
    },
    SubmachineGun {
        private List<ActionGroup> mActionGroups;

        @Override
        public List<ActionGroup> getActionGroups() {
            if (mActionGroups == null) {
                mActionGroups = new ArrayList<ActionGroup>();
                mActionGroups.add(ActionGroup.OTHER);
            }
            return mActionGroups;
        }

        @Override
        public int getBaseValue(GameEdition edition) {
            return 15;
        }
    };
    private Set<GameEdition> editions = new HashSet<GameEdition>();

    {
        editions.add(GameEdition.CoC5);
    }

    private List<ActionGroup> mActionGroups;
    private int nameResId      = -1;
    private int shortNameResId = -1;

    public Set<Relation> getRelations() {
        return Collections.emptySet();
    }

    public CharacterProperty asCharacterProperty(GameEdition edition) {
        CharacterProperty r = new CharacterProperty();
        r.setName(name());
        r.setNameResId(getNameResId());
        r.setShortNameResId(getShortNameResId());
        r.setFormat(PropertyFormat.PERCENTILE);
        r.setType(PropertyType.SKILL);
        r.setMaxValue(100);
        r.setMinValue(0);
        r.setValue(getBaseValue(edition));
        r.setRelations(getRelations());
        r.setActionGroup(getActionGroups());
        r.setBaseValue(getBaseValue(edition));
        return r;
    }

    public int getBaseValue(GameEdition edition) {
        if (GameEdition.CoC6.equals(edition)) {
            return 1;
        } else {
            return 0;
        }
    }

    public Set<GameEdition> getEditions() {
        if (editions == null) {
            editions = new HashSet<GameEdition>();
            editions.add(GameEdition.CoC5);
        }
        return editions;
    }

    public List<ActionGroup> getActionGroups() {
        if (mActionGroups == null) {
            mActionGroups = new ArrayList<ActionGroup>();
            mActionGroups.add(ActionGroup.OTHER);
        }
        return mActionGroups;
    }

    public int getNameResId() {
        return nameResId;
    }

    public void setNameResId(int nameResId) {
        this.nameResId = nameResId;
    }

    public int getShortNameResId() {
        return shortNameResId;
    }

    public void setShortNameResId(int shortNameResId) {
        this.shortNameResId = shortNameResId;
    }
}
