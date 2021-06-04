package com.example.helpworx.ctmmny.repository;

import com.example.helpworx.ctmmny.dto.SystemInfoDTO;

import java.util.List;

public interface CtmmnySysRepositoryCustom {
    List<SystemInfoDTO> findSysNameListbyCtmmnyCd(String ctmmnyCd);
    Long deleteByCtmmnyCd(String ctmmnyCd);

    List<Long> findIdListByCtmmnyCd(String ctmmnyCd);
}
