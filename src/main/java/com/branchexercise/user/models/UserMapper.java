package com.branchexercise.user.models;

import com.branchexercise.client.dto.RepoData;
import com.branchexercise.client.dto.UserData;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserMapper {
    public static UserMeta mapDTOtoModel(UserData ud, List<RepoData> repoList) {

        if (ud == null)
            return null;

        // Map the users basic metadata
        var builder = UserMeta.builder()
                .userName(ud.login)
                .email(ud.email)
                .displayName(ud.name)
                .avatar(ud.avatar_url)
                .geoLocation(ud.location)
                .url(ud.url)
                .createdAt(ud.created_at);

        // Map each of the repos over
        if (repoList != null)
            repoList.forEach( i -> builder.repo(RepoMeta.builder()
                    .name(i.name)
                    .url(i.url)
                    .build()));

        return builder.build();
    }
}
