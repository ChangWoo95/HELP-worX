package com.example.helpworx.ctmmny.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Sys {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "SYS_ID")
    private Long id;

    @Column(name="SYS_NM")
    private String sysNm;

    @OneToMany(mappedBy = "sys")
    @JsonIgnore
    private List<CtmmnySys> ctmmnySysList;

    @OneToMany(mappedBy = "sys")
    private List<Job> jobs;

}
