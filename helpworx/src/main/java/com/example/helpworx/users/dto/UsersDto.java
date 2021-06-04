package com.example.helpworx.users.dto;

import com.example.helpworx.ctmmny.domain.Ctmmny;
import com.example.helpworx.users.domain.UserRoleMap;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import java.util.List;

@Getter
@Builder
@ToString
@AllArgsConstructor
public class UsersDto {
    private Long id;
    private String userEmail;
    private String userNm;
    private String userTeleno;
    private List<UserRoleMap> roles;
    private Ctmmny ctmmny;

}
