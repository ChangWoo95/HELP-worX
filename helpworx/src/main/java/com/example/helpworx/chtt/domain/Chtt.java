package com.example.helpworx.chtt.domain;


import com.example.helpworx.users.domain.Users;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.Date;
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Chtt {

    @Id @GeneratedValue
    @Column(name = "CHTT_ID")
    private Long id;

    @Enumerated(EnumType.STRING)
    private ConvrsType convrsType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="USER_ID")
    private Users user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="CHTT_ROOM_ID")
    @JsonBackReference
    private ChttRoom chttRoom;

    @Column(columnDefinition="TEXT")
    private String convrs;

    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone="Asia/Seoul")
    @Column(name="CHTT_CREAT_DATE")
    private Date chttCreatDate;
}
