
package io.github.scottmaclure.character.traits.model;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.codehaus.jackson.annotate.JsonAnyGetter;
import org.codehaus.jackson.annotate.JsonAnySetter;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.annotate.JsonPropertyOrder;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@JsonPropertyOrder({
    "speech",
    "hair",
    "facialFeatures",
    "characteristics",
    "personality",
    "bodyLocations"
})
public class TraitsSet {

    public static final String               FILE                 = "traits.json";
    @JsonProperty("speech")
    private             List<String>         speech               = new ArrayList<String>();
    @JsonProperty("hair")
    private             List<String>         hair                 = new ArrayList<String>();
    @JsonProperty("facialFeatures")
    private             List<String>         facialFeatures       = new ArrayList<String>();
    @JsonProperty("characteristics")
    private             List<Characteristic> characteristics      = new ArrayList<Characteristic>();
    @JsonProperty("personality")
    private             List<String>         personality          = new ArrayList<String>();
    @JsonProperty("bodyLocations")
    private             List<String>         bodyLocations        = new ArrayList<String>();
    @JsonIgnore
    private             Map<String, Object>  additionalProperties = new HashMap<String, Object>();
    private             Random               mRandom              = new Random();

    @JsonProperty("speech")
    public List<String> getSpeech() {
        return speech;
    }

    @JsonProperty("speech")
    public void setSpeech(List<String> speech) {
        this.speech = speech;
    }

    @JsonProperty("hair")
    public List<String> getHair() {
        return hair;
    }

    @JsonProperty("hair")
    public void setHair(List<String> hair) {
        this.hair = hair;
    }

    @JsonProperty("facialFeatures")
    public List<String> getFacialFeatures() {
        return facialFeatures;
    }

    @JsonProperty("facialFeatures")
    public void setFacialFeatures(List<String> facialFeatures) {
        this.facialFeatures = facialFeatures;
    }

    @JsonProperty("characteristics")
    public List<Characteristic> getCharacteristics() {
        return characteristics;
    }

    @JsonProperty("characteristics")
    public void setCharacteristics(List<Characteristic> characteristics) {
        this.characteristics = characteristics;
    }

    @JsonProperty("personality")
    public List<String> getPersonality() {
        return personality;
    }

    @JsonProperty("personality")
    public void setPersonality(List<String> personality) {
        this.personality = personality;
    }

    @JsonProperty("bodyLocations")
    public List<String> getBodyLocations() {
        return bodyLocations;
    }

    @JsonProperty("bodyLocations")
    public void setBodyLocations(List<String> bodyLocations) {
        this.bodyLocations = bodyLocations;
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public boolean equals(Object other) {
        return EqualsBuilder.reflectionEquals(this, other);
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }
}
