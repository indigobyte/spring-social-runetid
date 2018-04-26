package org.springframework.social.runetid.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class UserCompany implements Serializable {
    private int id;
    private String name;

    @JsonCreator
    public UserCompany(
            @JsonProperty(value = "Id", required = true) int id,
            @JsonProperty(value = "Name", required = true) String name
    ) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
