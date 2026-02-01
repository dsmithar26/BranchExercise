package com.branchexercise.user;

import com.branchexercise.client.UserDataClient;
import com.branchexercise.user.models.UserMeta;
import com.branchexercise.user.models.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserService {
    private final UserDataClient client;

    public UserService (UserDataClient client) {
        log.debug("Creating instance of UserService");
        this.client = client;
    }

    // This will cache permanently in memory. In prod we'd used prefer to use something like
    // redis which provides TTL functionality.
    @Cacheable("UserMeta")
    public UserMeta getUserMetaData (String userName) {
        log.debug("Collecting user data");
        var ghMData = client.GetUserData(userName);
        var ghRData = client.GetRepoData(userName);

        log.debug("Mapping User data");
        return UserMapper.mapDTOtoModel(ghMData, ghRData);
    }
}
