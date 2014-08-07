package io.github.scottmaclure.character.traits.model;

import java.io.Serializable;
import java.util.Locale;

import io.github.scottmaclure.character.traits.model.providers.CharacteristicProvider;
import io.github.scottmaclure.character.traits.model.providers.StringTraitProvider;

/**
 * Created by bhm on 02.08.14.
 */
public class RandomTraitsSet implements Serializable{

    private String hair;
    private String facial;
    private String characteristic;
    private String personality;
    private String speech;

    private RandomTraitsSet(Builder b) {
        this.hair = b.hair;
        this.facial = b.facial;
        this.characteristic = b.characteristic;
        this.personality = b.personality;
        this.speech = b.speech;
    }

    public String getHair() {
        return hair;
    }

    public void setHair(String hair) {
        this.hair = hair;
    }

    public String getFacial() {
        return facial;
    }

    public void setFacial(String facial) {
        this.facial = facial;
    }

    public String getCharacteristic() {
        return characteristic;
    }

    public void setCharacteristic(String characteristic) {
        this.characteristic = characteristic;
    }

    public String getPersonality() {
        return personality;
    }

    public void setPersonality(String personality) {
        this.personality = personality;
    }

    public String getSpeech() {
        return speech;
    }

    public void setSpeech(String speech) {
        this.speech = speech;
    }

    public static RandomTraitsSet from(TraitsSet set) {
        RandomTraitsSet.Builder builder = new RandomTraitsSet.Builder();
        StringTraitProvider stp = StringTraitProvider.getInstance();
        String facial = stp.getRandomTrait(set.getFacialFeatures());
        String speech = stp.getRandomTrait(set.getSpeech());
        String hair = stp.getRandomTrait(set.getHair());
        String personality = stp.getRandomTrait(set.getPersonality());
        builder.setFacial(facial)
                .setSpeech(speech)
                .setHair(hair)
                .setPersonality(personality);
        CharacteristicProvider cp = new CharacteristicProvider();
        Characteristic characteristic = cp.getRandomTrait(set.getCharacteristics());
        String charName = characteristic.getText();
        if (characteristic.hasLocation()) {
            String location = stp.getRandomTrait(set.getBodyLocations());
            String formated = String.format(Locale.ENGLISH, "%s (%s)", charName, location);
            builder.setCharacteristic(formated);
        } else {
            builder.setCharacteristic(charName);
        }
        return builder.build();
    }

    public static class Builder {

        protected String hair;
        protected String speech;
        protected String facial;
        protected String characteristic;
        protected String personality;

        public Builder setPersonality(String personality) {
            this.personality = personality;
            return this;
        }

        public Builder setHair(String hair) {
            this.hair = hair;
            return this;
        }

        public Builder setSpeech(String speech) {
            this.speech = speech;
            return this;
        }

        public Builder setFacial(String facial) {
            this.facial = facial;
            return this;
        }

        public Builder setCharacteristic(String characteristic) {
            this.characteristic = characteristic;
            return this;
        }

        public RandomTraitsSet build() {
            return new RandomTraitsSet(this);
        }
    }
}