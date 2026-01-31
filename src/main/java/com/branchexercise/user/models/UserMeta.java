package com.branchexercise.user.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.Singular;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Getter
@Setter
@Builder
// not sure if you care about the output order, but it's easier to fix it than ask ;)
@JsonPropertyOrder({ "user_name", "display_name", "avatar", "geo_location", "email",
        "url", "created_at", "repos"})
@Slf4j
public class UserMeta {

    public String avatar;

    @JsonProperty("created_at")
    public String createdAt;

    @JsonProperty("display_name")
    public String displayName;

    public String email;

    @JsonProperty("geo_location")
    public String geoLocation;

    @Singular
    public List<RepoMeta> repos;

    public String url;

    @JsonProperty("user_name")
    public String userName;

    private static final ObjectMapper jsonMapper = new ObjectMapper();

    @Override
    public String toString() {
        try {
            return jsonMapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            log.error("Error converting POJO to Json for logging", e);
            return "Error converting POJO";
        }
    }
}

