package com.bustiblelemons.cthulhator.character.creation.model;

import com.bustiblelemons.cthulhator.character.creation.logic.RelatedPropertesRetreiver;
import com.bustiblelemons.cthulhator.system.brp.statistics.BRPStatistic;
import com.bustiblelemons.cthulhator.system.properties.CharacterProperty;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by hiv on 02.11.14.
 */
public class CreatorCard {

    private RelatedPropertesRetreiver retreiver;
    private List<CharacterProperty>   properties;

    private CreatorCard(Builder builder) {
        this.retreiver = builder.retreiver;
        this.properties = builder.properties;
    }

    public static Builder newCreationCard() {
        return new Builder();
    }

    public RelatedPropertesRetreiver getRetreiver() {
        return retreiver;
    }

    public List<CharacterProperty> getProperties() {
        return properties;
    }

    public enum Type {
        BODY {
            private List<String> mNames;

            @Override
            public Collection<String> getPropertyNames() {
                if (mNames == null) {
                    mNames = new ArrayList<String>(4);
                    mNames.add(BRPStatistic.CON.name());
                    mNames.add(BRPStatistic.SIZ.name());
                    mNames.add(BRPStatistic.STR.name());
                    mNames.add(BRPStatistic.DEX.name());
                }
                return mNames;
            }
        }, MIND {
            private List<String> mNames;

            @Override
            public Collection<String> getPropertyNames() {
                if (mNames == null) {
                    mNames = new ArrayList<String>(2);
                    mNames.add(BRPStatistic.INT.name());
                    mNames.add(BRPStatistic.EDU.name());
                }
                return mNames;
            }
        }, APPEARANCE {
            private List<String> mNames;

            @Override
            public Collection<String> getPropertyNames() {
                if (mNames == null) {
                    mNames = new ArrayList<String>(1);
                    mNames.add(BRPStatistic.APP.name());
                }
                return mNames;
            }
        }, POWER {
            private List<String> mNames;

            @Override
            public Collection<String> getPropertyNames() {
                if (mNames == null) {
                    mNames = new ArrayList<String>(1);
                    mNames.add(BRPStatistic.POW.name());
                }
                return mNames;
            }
        };

        public abstract Collection<String> getPropertyNames();
    }

    public static final class Builder {
        private RelatedPropertesRetreiver retreiver;
        private List<CharacterProperty> properties = new ArrayList<CharacterProperty>();

        private Builder() {
        }

        public CreatorCard build() {
            return new CreatorCard(this);
        }

        public Builder retreiver(RelatedPropertesRetreiver retreiver) {
            this.retreiver = retreiver;
            return this;
        }

        public Builder properties(List<CharacterProperty> properties) {
            if (properties == null) {
                this.properties = new ArrayList<CharacterProperty>();
            }
            this.properties = properties;
            return this;
        }

        public Builder addProperty(CharacterProperty property) {
            if (properties == null) {
                this.properties = new ArrayList<CharacterProperty>();
            }
            if (property != null) {
                this.properties.add(property);
            }
            return this;
        }
    }
}
