package com.example.helpworx.sr.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

public class RcptTreatDto {

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Request {
        private long id;
        private String recivrNm;
        private String rcptDscr;
        private Date cmplPrdt;
        private String opetrNm;
        private LocalDateTime rcptTim;
        private String treatDscr;
        private Date treatStartTim;
        private Date treatEndTim;
        private String treatTim;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Response {
        private long id;
        private String recivrNm;
        private String rcptDscr;
        private Date cmplPrdt;
        private String opetrNm;
        private LocalDateTime rcptTim;
        private String treatDscr;
        private Date treatStartTim;
        private Date treatEndTim;
        private String treatTim;

    }
}
