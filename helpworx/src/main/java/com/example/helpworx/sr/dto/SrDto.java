package com.example.helpworx.sr.dto;

import com.example.helpworx.ctmmny.domain.Ctmmny;
import com.example.helpworx.sr.domain.RcptTreat;
import com.example.helpworx.sr.domain.Sr;
import com.example.helpworx.sr.mapper.RcptTreatMapper;
import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
public class SrDto {

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ListRequest {
        private String sysNm;
        private String jobGb;
        private Long id;
        private String reqGb;
        private String status;
        private String sbjct;
        private String dscr;
        private RcptTreat rcptTreat;
        private String ctmmnyCd;

    }
    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SrsListRequest {
        private String sysNm;
        private String jobGb;
        private Long id;
        private String reqGb;
        private String status;
        private String sbjct;
        private String dscr;
        private String ctmmnyCd;

    }
    @Getter
    @Builder
    public static class ListResponse {
        private Long id;
        private String status;
        private String sbjct;
        private String reqr;
        private LocalDateTime reqTim;
        private String sysNm;
        private String jobGb;
        private String reqGb;
        private RcptTreat rcptTreat;
        private String ctmmnyNm;

        @QueryProjection
        public ListResponse(Long id, String status, String sbjct, String reqr, LocalDateTime reqTim, String sysNm, String jobGb ,String reqGb ,RcptTreat rcptTreat, String ctmmnyNm) {
            this.id = id;
            this.status = status;
            this.sbjct = sbjct;
            this.reqr = reqr;
            this.reqTim = reqTim;
            this.sysNm = sysNm;
            this.jobGb = jobGb;
            this.reqGb = reqGb;
            this.rcptTreat = rcptTreat;
            this.ctmmnyNm = ctmmnyNm;

        }

        public ListResponse(Sr entity) {
            this.id = entity.getId();
            this.sysNm = entity.getSysNm();
            this.jobGb = entity.getJobGb();
            this.sbjct = entity.getSbjct();
            this.reqr = entity.getReqr().getUserNm();
            this.reqTim = entity.getReqTim();
            this.status = entity.getStatus();
            this.reqGb = entity.getReqGb();
            this.rcptTreat= entity.getRcptTreat();
            this.ctmmnyNm = entity.getCtmmny().getCtmmnyNm();

        }

    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DetailResponse {
        private Long id;
        private String status;
        private String sbjct;
        private LocalDateTime reqTim;
        private String sysNm;
        private String jobGb;
        private String reqGb;
        private String dscr;
        private Ctmmny ctmmny;
        private String reqr;
        private RcptTreat rcptTreat;
    }
    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
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
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DetailRequestForOper {
        private Long id;
        private String status;
        private String sbjct;
        private LocalDateTime reqTim;
        private String sysNm;
        private String jobGb;
        private String dscr;
        private String reqGb;
        private RcptTreat rcptTreat;
    }
    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DetailResponseForOper {
        private Long id;
        private String status;
        private String sbjct;
        private LocalDateTime reqTim;
        private String sysNm;
        private String jobGb;
        private String dscr;
        private String reqGb;
        private String reqr;
        private RcptTreat rcptTreat;
    }


}
