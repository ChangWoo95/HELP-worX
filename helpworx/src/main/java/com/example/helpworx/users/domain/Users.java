package com.example.helpworx.users.domain;

import com.example.helpworx.chtt.domain.ChttRoom;
import com.example.helpworx.ctmmny.domain.Ctmmny;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import io.swagger.annotations.ApiParam;
import lombok.*;
import lombok.Builder;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class Users {

    @ApiParam(value = "user ID", required = true)
    @Id  @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "USER_ID")
    private Long id;

    @ApiParam(value = "user email", required = true)
    @NotNull
    @Column(name = "USER_EMAIL")
    private String userEmail;

    @ApiParam(value = "user name", required = true)
    @NotNull
    @Column(name = "USER_NM")
    private String userNm;

    @NotNull
    @Column(name = "USER_PWD")
    private String userPwd;

    @NotNull
    @Column(name = "IS_USED")
    private Boolean isUsed;


    @ApiParam(value = "user teleno", required = false)
    @Column(name = "USER_TELENO")
    private String userTeleno;

    @ApiParam(value = "user roles", required = false)
    @OneToMany(mappedBy = "user")
    @JsonManagedReference
    private List<UserRoleMap> roles;

    @ApiParam(value = "user company code", required = false)
    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CTMMNY_CD")
    private Ctmmny ctmmny;

    @ApiParam(value = "users chttRooms", required = false)
    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<ChttRoom> chttRooms;

}
