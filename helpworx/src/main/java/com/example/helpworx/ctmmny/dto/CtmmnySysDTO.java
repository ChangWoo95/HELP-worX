package com.example.helpworx.ctmmny.dto;

import com.example.helpworx.ctmmny.domain.Ctmmny;
import com.example.helpworx.ctmmny.domain.CtmmnySys;
import com.example.helpworx.ctmmny.domain.Sys;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;


@Getter
@Builder
@AllArgsConstructor
public class CtmmnySysDTO {
    private Long id;
    private Ctmmny ctmmny;
    private Sys sys;

    public CtmmnySysDTO(CtmmnySys entity) {
        this.id = entity.getCtmmnySysId();
        this.ctmmny = entity.getCtmmny();
        this.sys = entity.getSys();
    }
}
