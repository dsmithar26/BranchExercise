package com.branchexercise.client;

import com.branchexercise.client.dto.RepoData;
import com.branchexercise.client.dto.UserData;
import com.branchexercise.util.AppConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientResponseException;

import java.util.List;

@Component
@Slf4j
public class GitHubClient implements UserDataClient {
    private final RestClient client;

    public GitHubClient(RestClient.Builder builder, AppConfig config) {
        log.debug("Creating instance of GitHubClient" + config.getGitHubURL());

        // common mistake to not be consistent on the / at the end of the url in the config.
        // check it
        String url = config.getGitHubURL();
        if (!url.endsWith("/"))
            url += "/";

        client = builder
                .baseUrl(url)
                .build();
    }

    public UserData GetUserData(String userName) {
        log.info("Calling GitHub for user metadata");
        try {
            return client.get()
                    .uri("/users/{userName}", userName)
                    .retrieve()
                    .onStatus(HttpStatusCode::is4xxClientError, (_, res) -> {
                        log.error("400 level error while requesting UserData from GitHub: " + res.getStatusText());
                        throw new GitHubException();
                    })
                    .onStatus(HttpStatusCode::is5xxServerError, (_, res) -> {
                        log.error("500 level error while requesting UserData from GitHub: " + res.getStatusText());
                        throw new GitHubException();
                    })
                    .body(UserData.class);
        // custom status handlers already logged, just return null
        } catch (GitHubException e) {
            return null;
        } catch (RestClientResponseException e) {
            log.error("Generic RestClient exception collecting user data", e);
            return null;
        } catch (Exception e) {
            log.error("Unexpected error occurred collecting user data", e);
            return null;
        }
    }

    public List<RepoData> GetRepoData(String userName) {
        try {
            log.info("Calling GitHub for repo data");
            return client.get()
                    .uri("/users/{userName}/repos", userName)
                    .retrieve()
                    .onStatus(HttpStatusCode::is4xxClientError, (_, res) -> {
                        log.error("400 level error while requesting RepoData from GitHub: " + res.getStatusText());
                        throw new GitHubException();
                    })
                    .onStatus(HttpStatusCode::is5xxServerError, (_, res) -> {
                        log.error("500 level error while requesting RepoData from GitHub: " + res.getStatusText());
                        throw new GitHubException();
                    })
                    .body(new ParameterizedTypeReference<>() {});
        // custom status handlers already logged, just return null
        } catch (GitHubException e) {
            return null;
        } catch (RestClientResponseException e) {
            log.error("Generic RestClient exception collecting repo data", e);
            return null;
        } catch (Exception e) {
            log.error("Unexpected error occurred collecting repo data", e);
            return null;
        }
    }
}