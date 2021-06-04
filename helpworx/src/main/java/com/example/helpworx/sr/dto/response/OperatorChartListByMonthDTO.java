package com.example.helpworx.sr.dto.response;

import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@Builder
public class OperatorChartListByMonthDTO {
    private String  dateExpression;
    private Long srCountsByMonth;

    @QueryProjection
    public OperatorChartListByMonthDTO(String dateExpression, Long srCountsByMonth){
        this.dateExpression = dateExpression;
        this.srCountsByMonth = srCountsByMonth;
    }
}
