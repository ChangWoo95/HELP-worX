package com.example.helpworx.sr.repository;

import com.example.helpworx.ctmmny.domain.Ctmmny;
import com.example.helpworx.sr.dto.SrDto;
import com.example.helpworx.sr.dto.response.*;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface SrRepositoryCustom {
    List<SrDto.ListResponse> findSrListWithCondition(Pageable pageable, SrDto.SrsListRequest srListRequestDto);
    Long countByCtmmnyAndSysNmAndMonth(Ctmmny ctmmny, String name, Integer month);
    Long countByCtmmnyAndSysNmAndReqGb(Ctmmny ctmmny, String name, String reqGb);
    Long countByCtmmnyCdAndSysNmAndReqGb(String ctmmnyCd, String name, String reqGb);
    Long countByCtmmnyCdAndSysNmAndMonth(String ctmmnyCd, String name, Integer month);


    List<SystemPairResponseDTO>  findIdAndNameByName();
    List<CustomerIncompleteUserSrDTO> findIncompleteSrListByUserId(Long id);
    List<CustomerIncompleteCtmmnySrDTO> findIncompleteSrListByCtmmnyId(String id);
    List<OperatorProceedingSrDTO> findProceedingSrList(String id);
    List<OperatorIncompleteSrDTO> findIncompleteSrList(String id);


    Long findAverageRcptTime(String ctmmnyCd, String name);
    Long findAverageTreatTime(String ctmmnyCd, String name);


    // 데이트 포맷 관련된 쿼리 dsl 함수 짜기!!!
    List<OperatorChartListByMonthDTO> findSrListByCtmmnyAndSysNm(String CtmmnyNm, String SysNm);
    List<OperatorTreatListByMonthDTO> findTreatListByCtmmnyAndSysNm(String CtmmnyNm, String SysNm);

    List<SrRankDTO> findRankListBySysNm(String name);

    Long deleteByCtmmnyCd(String ctmmnyCd);

}
