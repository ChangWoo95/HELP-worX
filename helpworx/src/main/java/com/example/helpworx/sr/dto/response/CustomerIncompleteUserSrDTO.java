package com.example.helpworx.sr.dto.response;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CustomerIncompleteUserSrDTO {
    private Long id;
    private String sysNm;
    private String sbjct;
    private LocalDateTime reqTim;
    private String status;

    @QueryProjection
    public CustomerIncompleteUserSrDTO(Long id, String sysNm, String sbjct, LocalDateTime reqTim, String status){
        this.id = id;
        this.sysNm = sysNm;
        this.sbjct = sbjct;
        this.reqTim = reqTim;
        this.status = status;
    }
}
