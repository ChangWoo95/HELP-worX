package com.example.helpworx.users.repository;
import com.example.helpworx.users.domain.UserRoleMap;

import java.util.List;

public interface UserRoleMapRepositoryCustom {
    List<UserRoleMap> findListByUserId(Long userId);
}
