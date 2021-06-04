package com.example.helpworx.ctmmny.resource;

import com.example.helpworx.ctmmny.domain.CtmmnySys;
import com.example.helpworx.users.domain.Users;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.List;

public class CtmmnyResource {
    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SrSearchBarResponse {
        private String id;
        private String ctmmnyNm;
        private String ctmmnyDscr;
        private List<CtmmnySys> ctmmnySysList;
    }


}
