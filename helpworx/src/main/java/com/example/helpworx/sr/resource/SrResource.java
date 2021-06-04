package com.example.helpworx.sr.resource;

import com.example.helpworx.ctmmny.domain.Ctmmny;
import com.example.helpworx.sr.domain.RcptTreat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

public class SrResource {

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class RequestResource {
        private String sysNm;
        private String jobGb;
        private Long id;
        private String reqGb;
        private String status;
        private String sbjct;
        private String dscr;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ResponseResource {
        private Long id;
        private String status;
        private String sbjct;
        private String reqGb;
        private String reqr;
        private LocalDateTime reqTim;
        private String sysNm;
        private String jobGb;
    }
    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class DetailResponse {
        private Long id;
        private String status;
        private String sbjct;
        private LocalDateTime reqTim;
        private String sysNm;
        private String jobGb;
        private String dscr;
        private String reqGb;
        private Ctmmny ctmmny;
        private String reqr;
        private RcptTreat rcptTreat;

    }
    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class DetailRequest {
        private Long id;
        private String status;
        private String sbjct;
        private LocalDateTime reqTim;
        private String sysNm;
        private String jobGb;
        private String dscr;
        private String reqGb;
        private Ctmmny ctmmny;
        private String reqr;
        private RcptTreat rcptTreat;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class DetailRequestForOper {
        private Long id;
        private String status;
        private String sbjct;
        private LocalDateTime reqTim;
        private String sysNm;
        private String jobGb;
        private String dscr;
        private String reqGb;
        private Ctmmny ctmmny;
        private String reqr;
        private RcptTreat rcptTreat;
    }
    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class DetailResponseForOper {
        private Long id;
        private String status;
        private String sbjct;
        private LocalDateTime reqTim;
        private String sysNm;
        private String jobGb;
        private String dscr;
        private String reqGb;
        private Ctmmny ctmmny;
        private String reqr;
        private RcptTreat rcptTreat;
    }

}
