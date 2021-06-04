package com.example.helpworx.sr.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

public class OperatorCtmmnyChartListDTO {
    @Getter
    @Builder
    public static class srResponse{
        private String ctmmnyNm;
        private List<OperatorChartListByMonthDTO> monthList;
    }
    @Getter
    @Builder
    public static class treatResponse{
        private String ctmmnyNm;
        private List<OperatorTreatListByMonthDTO> monthList;
    }
}
