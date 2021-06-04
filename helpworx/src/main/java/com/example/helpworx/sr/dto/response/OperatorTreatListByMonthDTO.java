package com.example.helpworx.sr.dto.response;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Builder
public class OperatorTreatListByMonthDTO {
    private String  dateExpression;
    private Long treatTimeByMonth;

    @QueryProjection
    public OperatorTreatListByMonthDTO(String dateExpression, Long treatTimeByMonth){
        this.dateExpression = dateExpression;
        this.treatTimeByMonth = treatTimeByMonth;
    }
}
