package org.springframework.social.runetid.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class UserPosition implements Serializable {
    private String position;
    private UserCompany company;

    @JsonCreator
    public UserPosition(
            @JsonProperty(value = "Position", required = true) String position,
            @JsonProperty(value = "Company", required = true) UserCompany company
    ) {
        this.position = position;
        this.company = company;
    }

    public String getPosition() {
        return position;
    }

    public UserCompany getCompany() {
        return company;
    }
}
