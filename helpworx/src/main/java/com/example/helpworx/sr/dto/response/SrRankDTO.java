package com.example.helpworx.sr.dto.response;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

@Getter
public class SrRankDTO {
    private String sysNm;
    private Long srCounts;

    @QueryProjection
    public SrRankDTO(String sysNm, Long srCounts){
        this.sysNm = sysNm;
        this.srCounts = srCounts;
    }
}
