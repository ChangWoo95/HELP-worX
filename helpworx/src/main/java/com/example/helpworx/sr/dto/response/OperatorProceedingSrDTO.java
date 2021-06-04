package com.example.helpworx.sr.dto.response;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class OperatorProceedingSrDTO {
    private Long id;
    private String sysNm;
    private String reqrNm;
    private LocalDateTime reqTim;

    @QueryProjection
    public OperatorProceedingSrDTO(Long id, String sysNm, String reqrNm, LocalDateTime reqTim){
        this.id = id;
        this.sysNm = sysNm;
        this.reqrNm = reqrNm;
        this.reqTim = reqTim;
    }
}
