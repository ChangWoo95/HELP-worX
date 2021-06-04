package com.example.helpworx.sr.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TreatTimesResponseDTO {
    private Long averageRcptTime;
    private Long averageTreatTime;
}
