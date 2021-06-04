package com.example.helpworx.sr.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SrCountsResponseDTO {
    private Long totalCounts;
    private Long dataModificationCounts;
    private Long improvingCounts;
    private Long errorCounts;
    private Long questionCounts;
    private Long currentMonthCounts;
    private Long prevMonthCounts;
}
