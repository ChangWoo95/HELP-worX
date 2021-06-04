package com.example.helpworx.ctmmny.repository;

import com.example.helpworx.ctmmny.domain.Ctmmny;
import com.example.helpworx.ctmmny.dto.CtmmnyCdAndNmResponseDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CtmmnyRepositoryCustom {

    List<Ctmmny> findByCtmmnyCdAndCtmmnyNm(Pageable pageable, String ctmmnyCd, String ctmmnyNm, Boolean isUsed);

    List<CtmmnyCdAndNmResponseDTO> findListByCtmmnyCdAndCtmmnyNm();
}
