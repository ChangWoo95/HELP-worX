package com.example.helpworx.ctmmny.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.ToString;
import javax.persistence.*;
@ToString
@Entity
@Getter
public class Job {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "JOB_ID")
    private Long id;

    @JsonIgnore
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="SYS_ID")
    private Sys sys;

    @Column(name="JOB_GB")
    private String JobGb;

}
