package com.example.helpworx.ctmmny.resource;

import com.example.helpworx.ctmmny.domain.Job;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import java.util.List;

@AllArgsConstructor
@Builder
@Getter
@ToString
public class SysResource {

    private Long id;

    private String sysNm;

//    private CtmmnySys ctmmnySys;
    private List<Job> jobs;

}
