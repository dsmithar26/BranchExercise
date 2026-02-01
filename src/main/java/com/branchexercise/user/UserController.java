package com.branchexercise.user;

import com.branchexercise.user.models.UserMeta;
import jakarta.validation.constraints.Pattern;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

@Validated
@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        log.debug("Creating instance of UserController");
        this.userService = userService;
    }

    // GitHub's limitations on user id's appears to be letter, numbers and hyphen. Max 39
    // So that's what I'm doing here. Not worrying about simplifying the error message that
    // goes back as this is just an exercise.
    @GetMapping("/{userName}/meta")
    public UserMeta get(@PathVariable() @Pattern(regexp = "[a-zA-Z0-9-]{1,39}") String userName) {

        log.info("New request on api /user/{userName}/meta: {}", userName);
        var toRet = userService.getUserMetaData(userName);
        log.info("Request on api /user/{userName}/meta complete: {}", userName);

        if (toRet == null) // null comes back if there are any issues
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        // doing it this way prevents converting the obj to json unless it's actually going to be logged
        log.debug("Response: {}", toRet);
        return toRet;
    }
}
