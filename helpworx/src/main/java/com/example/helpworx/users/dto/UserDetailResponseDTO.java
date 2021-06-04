package com.example.helpworx.users.dto;

import com.example.helpworx.users.domain.UserRoleMap;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
public class UserDetailResponseDTO {
    private String ctmmnyCd;
    private Long userId;
    private String userNm;
    private String userPwd;
    private String userEmail;
    private String userTeleno;
    private Boolean isUsed;
    private List<Long> roles;
}
