package com.example.helpworx.ctmmny.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

@Getter
public class SystemInfoDTO {
    private Long id;
    private String name;
    @QueryProjection
    public SystemInfoDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
