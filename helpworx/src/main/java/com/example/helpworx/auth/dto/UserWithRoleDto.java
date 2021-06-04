package com.example.helpworx.auth.dto;

import com.example.helpworx.ctmmny.domain.Ctmmny;
import lombok.Builder;
import lombok.Getter;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Builder
public class UserWithRoleDto {
    @NotNull
    private String userEmail;

    @NotNull
    private String userNm;

    private String userTeleno;

    private Ctmmny ctmmny;

    private List<String> roles;

    private Long userId;


}
