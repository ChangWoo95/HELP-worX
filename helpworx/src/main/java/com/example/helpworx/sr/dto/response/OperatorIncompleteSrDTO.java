package com.example.helpworx.sr.dto.response;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class OperatorIncompleteSrDTO {
    private Long id;
    private String sysNm;
    private String sbjct;
    private String reqrNm;
    private LocalDateTime reqTim;
    private String status;

    @QueryProjection
    public OperatorIncompleteSrDTO(Long id, String sysNm, String sbjct, String reqrNm, LocalDateTime reqTim, String status){
        this.id = id;
        this.sysNm = sysNm;
        this.sbjct = sbjct;
        this.reqrNm = reqrNm;
        this.reqTim = reqTim;
        this.status = status;
    }
}
