package com.example.helpworx.ctmmny.dto;

import com.example.helpworx.ctmmny.domain.Ctmmny;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegistCtmmnyRequestDTO {
    private String ctmmnyCd;
    private String ctmmnyNm;
    private String ctmmnyDscr;
    private Boolean isUsed;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime contBeginDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime contEndDate;
    private List<Long> systemIdList;

    public Ctmmny toEntity() {
        return Ctmmny.builder()
                .id(ctmmnyCd)
                .ctmmnyNm(ctmmnyNm)
                .ctmmnyDscr(ctmmnyDscr)
                .contBeginDate(contBeginDate)
                .contEndDate(contEndDate)
                .isUsed(isUsed)
                .build();
    }
}
