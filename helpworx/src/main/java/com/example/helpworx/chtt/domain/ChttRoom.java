package com.example.helpworx.chtt.domain;

import com.example.helpworx.users.domain.Users;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import io.swagger.annotations.ApiParam;
import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
@Getter
@Entity
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name="CHTT_ROOM")
public class ChttRoom {

    @Id @GeneratedValue
    @Column(name = "CHTT_ROOM_ID")
    private Long id;

    @Column(name = "SYS_NM")
    private String sysNm;

    @Column(name = "JOB_GB")
    private String jobGb;

    @Column(name = "STATUS")
    private String status;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    @JsonIgnore
    private Users user;

    @ApiParam(value = "Chtt List in ChttRoom", required = false)
    @OneToMany(mappedBy = "chttRoom")
    @JsonManagedReference
    private List<Chtt> chttList;


    @Column(name="CHTT_START_TIM")
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone="Asia/Seoul")
    private Date chttStartTim;

    @Column(name="CHTT_END_TIM")
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone="Asia/Seoul")
    private Date chttEndTim;
}
