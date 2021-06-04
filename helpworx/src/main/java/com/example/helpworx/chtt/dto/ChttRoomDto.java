package com.example.helpworx.chtt.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.Date;

@Getter
@Builder
@AllArgsConstructor
public class ChttRoomDto {
    private Long id;
    private String sysNm;
    private String jobGb;
    private String status;
    private String userNm;
    private String ctmmnyNm;
    private Date chttStartTim;
    private Date chttEndTim;

}
