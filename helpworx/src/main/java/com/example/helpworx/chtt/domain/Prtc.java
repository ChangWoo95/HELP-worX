package com.example.helpworx.chtt.domain;

import com.example.helpworx.users.domain.Users;
import lombok.Getter;
import javax.persistence.*;

@Entity
@Getter
public class Prtc {
    @Id @GeneratedValue
    @Column(name="PRTC_ID")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="CHTT_ROOM_ID")
    private ChttRoom chttRoom;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="USER_ID")
    private Users users;
}
