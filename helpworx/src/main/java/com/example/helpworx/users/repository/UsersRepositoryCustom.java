package com.example.helpworx.users.repository;

import com.example.helpworx.users.domain.Users;
import com.example.helpworx.users.dto.UserDetailResponseDTO;

import com.google.common.collect.Lists;

import org.springframework.data.domain.Pageable;


import java.util.List;

public interface UsersRepositoryCustom {

    List<Users> findUserInfoList(Pageable pageable, Long userId, String userNm, String ctmmnyNm, Boolean isUsed);

    UserDetailResponseDTO findUser(Long userId);
    List<Users> findAllByCtmmnyCd(String ctmmnyCd);
}
