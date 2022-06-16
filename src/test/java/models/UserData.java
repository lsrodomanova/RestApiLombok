package models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserData {

    private String id;
    private String name;
    private String job;
    private String createdAt;
    private String updatedAt;
    private String email;
}