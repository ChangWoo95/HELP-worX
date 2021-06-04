package com.example.helpworx.sr.domain;

import com.example.helpworx.ctmmny.domain.Ctmmny;
import com.example.helpworx.users.domain.Users;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor
@Setter
public class Sr {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="SR_ID")
    private Long id;

    @Column(columnDefinition = "varchar(10) default '접수 대기'")
    private String status;

    private String sbjct;
    @Column(columnDefinition="TEXT")
    private String dscr;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="REQR")
    private Users reqr;

    @Column(name="REQ_TIM")
    private LocalDateTime reqTim;

    @Column(name="REQ_GB")
    private String reqGb;

    @Column(name="SYS_NM")
    private String sysNm;

    @Column(name="JOB_GB")
    private String jobGb;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="CTMMNY")
    private Ctmmny ctmmny;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="RCPT_TREAT_ID")
    @Setter
    private RcptTreat rcptTreat;
    @Builder
    Sr(Long id, String status, String sbjct, String dscr, Users reqr, LocalDateTime reqTim, String reqGb, String sysNm, String jobGb, Ctmmny ctmmny){
        this.id = id;
        this.status = status;
        this.sbjct = sbjct;
        this.dscr = dscr;
        this.reqr = reqr;
        this.reqTim = reqTim;
        this.reqGb = reqGb;
        this.sysNm = sysNm;
        this.jobGb = jobGb;
        this.ctmmny = ctmmny;
    }

    @Builder
    Sr(Long id, String status, String sbjct, String dscr, Users reqr, LocalDateTime reqTim, String reqGb, String sysNm, String jobGb, Ctmmny ctmmny,RcptTreat rcptTreat){
        this.id = id;
        this.status = status;
        this.sbjct = sbjct;
        this.dscr = dscr;
        this.reqr = reqr;
        this.reqTim = reqTim;
        this.reqGb = reqGb;
        this.sysNm = sysNm;
        this.jobGb = jobGb;
        this.ctmmny = ctmmny;
        this.rcptTreat = rcptTreat;
    }
    }
