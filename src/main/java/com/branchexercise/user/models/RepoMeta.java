package com.branchexercise.user.models;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@JsonPropertyOrder({"name", "url"})
public class RepoMeta {
    String name;
    String url;
}
