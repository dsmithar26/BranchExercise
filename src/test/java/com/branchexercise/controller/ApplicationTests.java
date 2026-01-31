package com.branchexercise.controller;

import com.branchexercise.client.GitHubClient;
import com.branchexercise.client.dto.RepoData;
import com.branchexercise.client.dto.UserData;
import com.branchexercise.user.models.UserMeta;
import com.branchexercise.user.UserController;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;


@SpringBootTest
class ApplicationTests {

    /*  Things which can/should be tested that I'm excluding as I don't think it adds much in this context.
        UserMapper
        GitHubClient
        UserService
        AppConfig
     */

    @MockitoBean
    private GitHubClient ghClient;

    @Autowired
    private UserController userController;

    // Not really a unit test, but shows the system works on happy case
    @Test
    public void fullFlowWorks() {
        var input = "totallyValid";

        var aUser = UserData.builder()
                .login(input).name("Full Name").build();

        when(ghClient.GetUserData(input)).thenReturn(aUser);

        var aRepo = RepoData.builder()
                .name("someName").url("someURL").build();
        var repoData = List.of(aRepo);

        when(ghClient.GetRepoData(input)).thenReturn(repoData);

        UserMeta result = userController.get(input);

        assertNotNull(result, "Null result is not expected");
        assertNotNull(result.repos, "Repo value should exist");
        assertEquals(1, result.repos.size(), "Repo does not contain the correct number of elements");
        assertEquals(input, result.userName, "userName does not contain expected value");
        assertEquals("Full Name", result.displayName, "displayName does not contain expected value");
        assertEquals("someName", result.repos.getFirst().getName(), "Repo name was not as expected");
        // skipping the rest as it doesn't add value to the exercise
    }

    @Test
    public void invalidInput_returnsError() {
        var input = "YouCan'tUseApostrophes";

        Throwable exception = assertThrows(Exception.class, () -> userController.get(input));

        assertNotNull(exception, "An exception result was expected");
        assertInstanceOf(ConstraintViolationException.class, exception, "Incorrect exception was returned");
    }
}