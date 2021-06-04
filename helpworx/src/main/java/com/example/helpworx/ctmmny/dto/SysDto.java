package com.example.helpworx.ctmmny.dto;

import com.example.helpworx.ctmmny.domain.Job;
import lombok.*;
import java.util.List;

@Getter
@Builder
@ToString
@AllArgsConstructor
public class SysDto {

    private Long id;

    private String sysNm;

//    private CtmmnySys ctmmnySys;

    private List<Job> jobs;

}
