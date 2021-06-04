package com.example.helpworx.users.repository;

import com.example.helpworx.users.domain.UserRoleMap;
import com.example.helpworx.users.domain.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRoleMapRepository extends JpaRepository<UserRoleMap,Long>, UserRoleMapRepositoryCustom {
    List<UserRoleMap> findAll();
    List<UserRoleMap> findByUser(Users user);
}
