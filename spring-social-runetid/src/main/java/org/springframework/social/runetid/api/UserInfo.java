package org.springframework.social.runetid.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.social.connect.UserProfile;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class UserInfo extends UserProfile {
    private int rocId;
    private int runetId;
    private String fatherName;
    private Date creationDate;
    private boolean visible;
    private boolean verified;
    private String gender;
    private UserPhoto photo;
    private UserPosition position;

    @JsonCreator
    public UserInfo(
            @JsonProperty(value = "Email") String email,
            @JsonProperty(value = "RocId", required = true) int rocId,
            @JsonProperty(value = "RunetId", required = true) int runetId,
            @JsonProperty(value = "FirstName") String lastName,
            @JsonProperty(value = "LastName") String firstName,
            @JsonProperty(value = "FatherName") String fatherName,
            @JsonProperty(value = "CreationTime", required = true) @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss") Date creationDate,
            @JsonProperty(value = "Visible", required = true) boolean visible,
            @JsonProperty(value = "Verified", required = true) boolean verified,
            @JsonProperty(value = "Gender", required = true) String gender,
            @JsonProperty(value = "Photo", required = true) UserPhoto photo,
            @JsonProperty(value = "Work") UserPosition position
    ) {
        super(
                Integer.toString(runetId),
                Stream.of(firstName, lastName)
                        .filter(StringUtils::hasLength)
                        .collect(Collectors.joining(" ")),
                firstName,
                lastName,
                email,
                Integer.toString(runetId)
        );
        this.rocId = rocId;
        this.runetId = runetId;
        this.fatherName = fatherName;
        this.creationDate = creationDate;
        this.visible = visible;
        this.verified = verified;
        this.gender = gender;
        this.photo = photo;
        this.position = position;
    }

    public int getRocId() {
        return rocId;
    }

    public int getRunetId() {
        return runetId;
    }

    public String getFatherName() {
        return fatherName;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public boolean isVisible() {
        return visible;
    }

    public boolean isVerified() {
        return verified;
    }

    public String getGender() {
        return gender;
    }

    public UserPhoto getPhoto() {
        return photo;
    }

    public UserPosition getPosition() {
        return position;
    }
}
