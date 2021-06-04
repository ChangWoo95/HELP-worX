package com.example.helpworx.ctmmny.dto;

import com.example.helpworx.ctmmny.domain.Ctmmny;
import com.example.helpworx.ctmmny.domain.CtmmnySys;
import lombok.Getter;
import java.util.List;

@Getter
public class CtmmnyListResponseDTO {
    private String ctmmnyCd;
    private String ctmmnyNm;
    private Integer usingClientCounts;
    private Boolean isUsed;
    private Long usingSystemCounts;

    public CtmmnyListResponseDTO(Ctmmny entity){
        this.ctmmnyCd = entity.getId();
        this.ctmmnyNm = entity.getCtmmnyNm();
        this.usingClientCounts = entity.getUsersList().size();
        this.usingSystemCounts = Long.valueOf(entity.getCtmmnySysList().size());
        this.isUsed = entity.getIsUsed();
    }


}
