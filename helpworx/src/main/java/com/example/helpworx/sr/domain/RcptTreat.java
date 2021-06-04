package com.example.helpworx.sr.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;
@Builder
@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Table(name="RCPT_TREAT")
public class RcptTreat {

    @Id @GeneratedValue
    @Column(name="RCPT_TREAT_ID")
    private long id;

    @Column(name="RECIVR_NM")
    private String recivrNm;

    @Column(name="RCPT_DSCR", columnDefinition="TEXT")
    private String rcptDscr;

    @Column(name="RCPT_TIM")
    private LocalDateTime rcptTim;

    @Temporal(TemporalType.DATE)
    @Column(name="CMPL_PRDT")
    private Date cmplPrdt;

    @Column(name="OPETR_NM")
    private String opetrNm;

    @Column(name="TREAT_DSCR", columnDefinition="TEXT")
    private String treatDscr;

    @Column(name="TREAT_START_TIM")
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone="Asia/Seoul")
    private Date treatStartTim;

    @Column(name="TREAT_END_TIM")
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone="Asia/Seoul")
    private Date treatEndTim;

    @Column(name="TREAT_TIM")
    private String treatTim;

}
