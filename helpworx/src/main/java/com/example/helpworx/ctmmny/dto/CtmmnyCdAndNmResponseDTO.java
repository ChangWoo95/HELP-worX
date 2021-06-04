package com.example.helpworx.ctmmny.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CtmmnyCdAndNmResponseDTO {
    private String CtmmnyCd;
    private String CtmmnyNm;

    @QueryProjection
    public CtmmnyCdAndNmResponseDTO(String ctmmnyCd, String ctmmnyNm) {
        this.CtmmnyCd = ctmmnyCd;
        this.CtmmnyNm = ctmmnyNm;
    }
}
