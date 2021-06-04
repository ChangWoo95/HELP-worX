package com.example.helpworx.users.dto;

import com.example.helpworx.ctmmny.domain.Ctmmny;
import com.example.helpworx.sr.domain.Sr;
import com.example.helpworx.users.domain.Users;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;


public class RegistingUser {
    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class RequestDTO {
        private Long userId;
        private String userEmail;
        private String userNm;
        private String userPwd;
        private String userTelno;
        private String ctmmnyCd;
        private Boolean isUsed;
        private List<Long> roles;

        public Users toEntity(Ctmmny ctmmny){
            return Users.builder()
                    .userEmail(userEmail)
                    .userNm(userNm)
                    .userPwd(userPwd)
                    .userTeleno(userTelno)
                    .isUsed(isUsed)
                    .ctmmny(ctmmny)
                    .build();
        }
    }
    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ResponseDTO {
        private Long userId;
        private String userEmail;
        private String userNm;
        private String userPwd;
        private String userTelno;
        private String ctmmnyCd;
        private Boolean isUsed;
        private List<Long> roles;

        public ResponseDTO(Users entity, List<Long> roles) {
            this.userId = entity.getId();
            this.userEmail = entity.getUserEmail();
            this.userNm = entity.getUserNm();
            this.userPwd = entity.getUserPwd();
            this.userTelno = entity.getUserTeleno();
            this.ctmmnyCd = entity.getCtmmny().getId();
            this.isUsed = entity.getIsUsed();
            this.roles = roles;

        }
    }
}
