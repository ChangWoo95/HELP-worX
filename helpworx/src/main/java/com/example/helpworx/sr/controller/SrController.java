package com.example.helpworx.sr.controller;

import com.example.helpworx.sr.dto.response.*;
import com.example.helpworx.sr.dto.SrDto;
import com.example.helpworx.sr.mapper.RcptTreatMapper;
import com.example.helpworx.sr.mapper.SrMapper;
import com.example.helpworx.sr.resource.RcptTreatResource;
import com.example.helpworx.sr.resource.SrResource;
import com.example.helpworx.sr.service.SrService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class SrController {
    private final SrService srService;

    @ApiOperation(value = "SR 상세 조회", notes ="고객 SR 조회 페이지")
    @GetMapping("/cust/srs/{id}")
    public SrResource.DetailResponse findSr(@PathVariable Long id) {
        return SrMapper.INSTANCE.DtoToResource(srService.getSrById(id));
    }

//    @ApiOperation(value = "SR 리스트 조회", notes ="고객 SR 조회 페이지")
//    @GetMapping("/cust/srs")
//    public List<SrDto.ListResponse> findAllSrList() {
//        return srService.getSrList();
//    }
    @ApiOperation(value = "SR 리스트 조회", notes ="고객 SR 조회 페이지")
    @GetMapping("/cust/srs")
    public Page<SrDto.ListResponse> findAllSrList(Pageable pageable) {
        return srService.getSrList(pageable);
    }

    @ApiOperation(value = "SR 리스트 조회", notes ="운영자 SR 접수,처리 페이지")
    @GetMapping("/oper/srs")
    public Page<SrResource.DetailResponse> findAllSrListForRcptTreat(Pageable pageable) {
        return new PageImpl<>( srService.getSrListForRcptTreat(pageable).getContent().stream()
                .map(detailResponse -> SrMapper.INSTANCE.DtoToResource(detailResponse))
                .collect(Collectors.toList()));
    }

    @ApiOperation(value = "SR 상세 조회", notes ="운영자 SR 접수,처리 페이지")
    @GetMapping("/oper/srs/{id}")
    public SrResource.DetailResponseForOper findSrForRcptTreat(@PathVariable Long id) {
        return  SrMapper.INSTANCE.DtoToResource(srService.getSrForRcptTreat(id));
    }

    @ApiOperation(value = "SR 접수/처리 ", notes ="운영자 SR 접수,처리 페이지")
    @PostMapping("/oper/srs/{id}")
    public ResponseEntity<SrResource.DetailResponseForOper> addRcptTreat(@PathVariable Long id, @RequestBody RcptTreatResource.Request requestResource) throws Exception {
        LoggerFactory.getLogger(this.getClass()).info("-------------SR 접수 처리----------------");

        SrDto.DetailResponseForOper updatedSr =
                srService.updateSrWithRcptTreat(id, RcptTreatMapper.INSTANCE.ResourceToDto(requestResource),requestResource.getStatus());

        return new ResponseEntity<>(SrMapper.INSTANCE.DtoToResource(updatedSr),HttpStatus.OK);
    }


    @ApiOperation(value = "SR 등록", notes ="고객 SR 등록 페이지")
    @PostMapping("/cust/srs/reg")
    public ResponseEntity<SrResource.ResponseResource> registration (@RequestBody SrResource.RequestResource srRequestResource) throws Exception {

        LoggerFactory.getLogger(this.getClass()).info("-------------SR 등록----------------");

        SrDto.ListResponse savedSr = srService.saveSr(SrMapper.INSTANCE.ResourceToDto(srRequestResource));

        return new ResponseEntity<SrResource.ResponseResource>(SrMapper.INSTANCE.DtoToResource(savedSr), HttpStatus.CREATED);
    }

    @ApiOperation(value = "SR 리스트 조회", notes ="고객 SR 리스트 페이지")
    @GetMapping("/cust/srs/condition")
    public Page<SrDto.ListResponse> findSrListWithCondition( @RequestParam("page") int page,
          @RequestParam("ctmmny") String ctmmnyCd, @RequestParam("sysNm") String sysNm, @RequestParam("jobGb") String jobGb, @RequestParam("id") String id, @RequestParam("reqGb") String reqGb, @RequestParam("status") String status, @RequestParam("sbjct") String sbjct, @RequestParam("dscr") String dscr
    ) {
        LoggerFactory.getLogger(this.getClass()).info("-------------SR 리스트 조회---------------");
        LoggerFactory.getLogger(this.getClass()).info("여기!!!!!!" + page);
        Pageable pageable = PageRequest.of(page, 5);

        return new PageImpl<SrDto.ListResponse> (srService.getSrListWithCondition(pageable,ctmmnyCd,sysNm, jobGb, id, reqGb, status, sbjct, dscr));
    }
    @ApiOperation(value = "SR 건수 콤보박스", notes ="고객 SR 메인 페이지")
    @GetMapping("/cust/srs/mainComboBox")
    public List<SystemPairResponseDTO> getSystemNames() {
        LoggerFactory.getLogger(this.getClass()).info("-------------SR 건수 콤보박스----------------");
        return srService.getSystemNameList();
    }

    @ApiOperation(value = "접속한 고객의 SR 건수 조회", notes ="고객 SR 메인 페이지")
    @GetMapping("/cust/srs/mainSrCounts")
    public SrCountsResponseDTO getUserSrCounts(@RequestParam String name){
        LoggerFactory.getLogger(this.getClass()).info("-------------SR 건수 조회----------------");
        return srService.findCtmmnySrCountsByReqGbAndCurrentMonthAndPrevMonth(name);
    }

    @ApiOperation(value = "고객사 SR 건수 조회", notes ="고객 SR 메인 페이지")
    @GetMapping("/oper/srs/SrCounts")
    public SrCountsResponseDTO getOperSrCounts(@RequestParam String ctmmnyCd, @RequestParam String name){
        LoggerFactory.getLogger(this.getClass()).info("-------------SR 건수 조회----------------");
        return srService.findOperCtmmnyStatusByReqGbAndCtmmnyCd(ctmmnyCd, name);
    }


    @ApiOperation(value = "평균 처리시간 조회", notes ="운영자 sr현황 페이지")
    @GetMapping("/oper/srs/SrTreatTimes")
    public TreatTimesResponseDTO getRcptTreatAverageTime(@RequestParam("ctmmnyCd") String ctmmnyCd, @RequestParam("name") String name){
        LoggerFactory.getLogger(this.getClass()).info("-------------SR 평균 처리시간 조회----------------");
        return srService.findAverageRcptTreatTime(ctmmnyCd, name);
    }


    @ApiOperation(value = "SR 미완료 유저에 대한 list 조회",notes ="고객 SR 메인 페이지")
    @GetMapping("/cust/srs/mainIncompleteUserSrList")
    public List<CustomerIncompleteUserSrDTO> getCustomerIncompleteSrListByUser() {
        LoggerFactory.getLogger(this.getClass()).info("-------------SR 미완료 유저에 대한 list 조회----------------");
        return srService.getCustomerIncompleteUserSrList();
    }

    @ApiOperation(value = "고객사별 rank 조회",notes ="고객 SR 랭크 페이지")
    @GetMapping("/oper/sr/rank")
    public List<SrRankDTO> getSrRanks(@RequestParam String name) {
        LoggerFactory.getLogger(this.getClass()).info("-------------SR Rank List 조회----------------");
        return srService.getRankList(name);
    }
    @ApiOperation(value = "SR 미완료 고객사에 대한 list 조회",notes ="고객 SR 메인 페이지")
    @GetMapping("/cust/srs/mainIncompleteCtmmnySrList")
    public List<CustomerIncompleteCtmmnySrDTO> getCustomerIncompleteSrListByCtmmny() {
        LoggerFactory.getLogger(this.getClass()).info("-------------SR 미완료 고객사에 대한 list 조회----------------");
        return srService.getCustomerIncompleteCtmmnySrList();
    }

    @ApiOperation(value = "미완료 SR list 조회",notes ="시스템 운영자 SR 메인 페이지")
    @GetMapping("/oper/srs/mainIncompleteUserSrList")
    public List<OperatorIncompleteSrDTO> getOperatorIncompleteSrList() {
        LoggerFactory.getLogger(this.getClass()).info("-------------SR 미완료 유저에 대한 list 조회----------------");
        return srService.getIncompleteSrList();
    }
    @ApiOperation(value = "SR 미완료 고객사에 대한 list 조회",notes ="시스템 운영자 SR 메인 페이지")
    @GetMapping("/oper/srs/mainProceedingSrList")
    public List<OperatorProceedingSrDTO> getOperatorProceedingSrList() {
        LoggerFactory.getLogger(this.getClass()).info("-------------SR 미완료 고객사에 대한 list 조회----------------");
        return srService.getProceedingSrList();
    }

    @ApiOperation(value = "고객사, 시스템 별 sr건수 list",notes ="시스템 운영자 SR 현황 sr건수")
    @GetMapping("/oper/srs/status/ctmmnyNm")
    public List<OperatorCtmmnyChartListDTO.srResponse> getSrListByCtmmnyNmAndSysNm(@RequestParam("ctmmnyCd") String ctmmnyCd, @RequestParam("name") String name) {
        LoggerFactory.getLogger(this.getClass()).info("-------------고객사, 시스템 별 sr건수 list----------------");
        return srService.getCtmmnySrListByMonthWithSomeCondition(ctmmnyCd, name);
    }

    @ApiOperation(value = "고객사, 시스템 별 처리시간 건수 list",notes ="시스템 운영자 SR 현황 처리시간")
    @GetMapping("/oper/treat/status/ctmmnyNm")
    public List<OperatorCtmmnyChartListDTO.treatResponse> getTreatListByCtmmnyNmAndSysNm(@RequestParam("ctmmnyCd") String ctmmnyCd, @RequestParam("name") String name) {
        LoggerFactory.getLogger(this.getClass()).info("-------------고객사, 시스템 별 처리시간 list----------------");
        return srService.getTreatListByMonth(ctmmnyCd, name);
    }
}
