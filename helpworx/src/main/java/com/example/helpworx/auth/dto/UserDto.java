package com.example.helpworx.auth.dto;

import lombok.Getter;
import javax.validation.constraints.NotNull;

@Getter
public class UserDto {
    @NotNull
    private String userEmail;

    @NotNull
    private String userNm;

    @NotNull
    private String userPwd;

    private String userTeleno;
    private String ctmmnyCd;

}
