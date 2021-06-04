package com.example.helpworx.users.dto;

import com.example.helpworx.users.domain.Users;
import lombok.Getter;

@Getter
public class UserInfoResponseDTO {
    private Long userId;
    private String userNm;
    private String ctmmnyNm;
    private Boolean isUsed;

    public UserInfoResponseDTO(Users entity) {
        this.userId = entity.getId();
        this.userNm = entity.getUserNm();
        this.ctmmnyNm = entity.getCtmmny().getCtmmnyNm();
        this.isUsed = entity.getIsUsed();
    }
}
