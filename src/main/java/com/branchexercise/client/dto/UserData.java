package com.branchexercise.client.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;

@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserData {
    public String login;
    public String name;
    public String avatar_url;
    public String location;
    public String email;
    public String url;
    public String created_at;
}
