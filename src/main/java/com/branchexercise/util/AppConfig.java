package com.branchexercise.util;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.URL;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Validated
@Component
@Getter
@Setter // I'd prefer not to have setters on the config, but I want to use @URL for validation which
@ConfigurationProperties(prefix = "app") // only works with @ConfigProp, which uses Setters so...
public class AppConfig {

    @NotBlank(message = "GitHub URL must not be blank")
    @URL(message = "GitHub URL must be a valid URL")
    private String gitHubURL;

    @NotNull(message = "Time out must not be blank")
    private int gitHubTOSeconds;
}
