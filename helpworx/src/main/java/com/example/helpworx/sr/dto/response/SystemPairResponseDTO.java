package com.example.helpworx.sr.dto.response;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class SystemPairResponseDTO {
    private Long id;
    private String name;

    @QueryProjection
    public SystemPairResponseDTO(Long id, String name){
        this.id = id;
        this.name = name;
    }
}
