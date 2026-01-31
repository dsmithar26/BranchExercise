package com.branchexercise.client.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;

@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class RepoData {
    public String name;
    public String url;
}
