package com.example.helpworx.chtt.dto;

import com.example.helpworx.chtt.domain.ChttRoom;
import com.example.helpworx.chtt.domain.ConvrsType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import java.util.Date;
@Getter
@Builder
@AllArgsConstructor
public class ChttDto {


    private Long id;
    private ConvrsType convrsType;
    private String userNm;
    private ChttRoom chttRoom;
    private String convrs;
    private Date chttCreatDate;
    private String ctmmnyNm;

}
