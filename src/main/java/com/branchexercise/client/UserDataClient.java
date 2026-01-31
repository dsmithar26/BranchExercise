package com.branchexercise.client;

import com.branchexercise.client.dto.RepoData;
import com.branchexercise.client.dto.UserData;

import java.util.List;

public interface UserDataClient {
    UserData GetUserData(String userName);
    List<RepoData> GetRepoData(String userName);
}
