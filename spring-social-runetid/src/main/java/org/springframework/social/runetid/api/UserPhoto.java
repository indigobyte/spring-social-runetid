package org.springframework.social.runetid.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class UserPhoto implements Serializable {
    private String small;
    private String medium;
    private String large;

    @JsonCreator
    public UserPhoto(
            @JsonProperty(value = "Small", required = true) String small,
            @JsonProperty(value = "Medium", required = true) String medium,
            @JsonProperty(value = "Large", required = true) String large
    ) {
        this.small = small;
        this.medium = medium;
        this.large = large;
    }

    public String getSmall() {
        return small;
    }

    public String getMedium() {
        return medium;
    }

    public String getLarge() {
        return large;
    }
}
