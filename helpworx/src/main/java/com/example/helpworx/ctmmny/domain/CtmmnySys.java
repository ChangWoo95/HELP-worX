package com.example.helpworx.ctmmny.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CtmmnySys {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long CtmmnySysId;

    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinColumn(name="CTMMNY_CD")
    @JsonIgnore
    private Ctmmny ctmmny;

    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinColumn(name="SYS_ID")
    private Sys sys;

}
