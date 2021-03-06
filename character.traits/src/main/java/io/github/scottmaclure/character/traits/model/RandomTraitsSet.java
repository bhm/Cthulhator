package io.github.scottmaclure.character.traits.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.Locale;

import io.github.scottmaclure.character.traits.providers.CharacteristicProvider;
import io.github.scottmaclure.character.traits.providers.StringTraitProvider;

/**
 * Created by bhm on 02.08.14.
 */
@JsonIgnoreProperties
public class RandomTraitsSet implements Serializable, Parcelable {

    public static final Parcelable.Creator<RandomTraitsSet> CREATOR = new Parcelable.Creator<RandomTraitsSet>() {
        public RandomTraitsSet createFromParcel(Parcel source) {
            return new RandomTraitsSet(source);
        }

        public RandomTraitsSet[] newArray(int size) {
            return new RandomTraitsSet[size];
        }
    };
    private String hair;
    private String facial;
    private String characteristic;
    private String personality;
    private String speech;

    public RandomTraitsSet() {
    }

    private RandomTraitsSet(Builder b) {
        this.hair = b.hair;
        this.facial = b.facial;
        this.characteristic = b.characteristic;
        this.personality = b.personality;
        this.speech = b.speech;
    }

    private RandomTraitsSet(Parcel in) {
        this.hair = in.readString();
        this.facial = in.readString();
        this.characteristic = in.readString();
        this.personality = in.readString();
        this.speech = in.readString();
    }

    public static RandomTraitsSet from(TraitsSet set) {
        if (set != null) {
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
        return null;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.hair);
        dest.writeString(this.facial);
        dest.writeString(this.characteristic);
        dest.writeString(this.personality);
        dest.writeString(this.speech);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RandomTraitsSet that = (RandomTraitsSet) o;

        if (characteristic != null ? !characteristic.equals(that.characteristic) : that.characteristic != null)
            return false;
        if (facial != null ? !facial.equals(that.facial) : that.facial != null) return false;
        if (hair != null ? !hair.equals(that.hair) : that.hair != null) return false;
        if (personality != null ? !personality.equals(that.personality) : that.personality != null)
            return false;
        if (speech != null ? !speech.equals(that.speech) : that.speech != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = hair != null ? hair.hashCode() : 0;
        result = 31 * result + (facial != null ? facial.hashCode() : 0);
        result = 31 * result + (characteristic != null ? characteristic.hashCode() : 0);
        result = 31 * result + (personality != null ? personality.hashCode() : 0);
        result = 31 * result + (speech != null ? speech.hashCode() : 0);
        return result;
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
