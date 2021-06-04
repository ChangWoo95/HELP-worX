package com.example.helpworx.sr.resource;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import java.util.Date;

public class RcptTreatResource {

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Request {
        private long id;
        private String recivrNm;
        private String rcptDscr;
        private Date cmplPrdt;
        @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
        private LocalDateTime rcptTim;
        private String opetrNm;
        private String treatDscr;
        private Date treatStartTim;
        private Date treatEndTim;
        private String status;
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
        private LocalDateTime rcptTim;
        private Date cmplPrdt;
        private String opetrNm;
        private String treatDscr;
        private Date treatStartTim;
        private Date treatEndTim;
        private String status;
        private String treatTim;

    }
}
