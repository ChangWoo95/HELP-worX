package com.example.helpworx.ctmmny.controller;

import com.example.helpworx.ctmmny.dto.*;
import com.example.helpworx.ctmmny.mapper.CtmmnyMapper;
import com.example.helpworx.ctmmny.resource.CtmmnyResource;
import com.example.helpworx.ctmmny.service.CtmmnyService;
import com.example.helpworx.users.dto.UserInfoResponseDTO;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CtmmnyController {
    private final CtmmnyService ctmmnyService;

    @ApiOperation(value = "Regist Ctmmny & CtmmnySys", notes = "고객사 등록")
    @PostMapping("/admin/ctmmny/registing")
    public List<CtmmnySysDTO> registCtmmny(@RequestBody RegistCtmmnyRequestDTO registCtmmnyRequestDTO) {
        LoggerFactory.getLogger(this.getClass()).info("-------------고객사 등록----------------");
        return ctmmnyService.registerCtmmny(registCtmmnyRequestDTO);
    }

    @ApiOperation(value = "Get Ctmmny List", notes = "고객사 전체 조회")
    @GetMapping("/oper/ctmmny")
    public List<CtmmnyResource.SrSearchBarResponse> getCtmmnyList(){
        LoggerFactory.getLogger(this.getClass()).info("-------------고객사 전체 조회----------------");
        return ctmmnyService.getCtmmnyList().stream().map(
                srSearchBarResponse ->
                    CtmmnyMapper.INSTANCE.SrSearchBarResponseDtoToSrSearchBarResponseResource(srSearchBarResponse)
        ).collect(Collectors.toList());
    }
    @ApiOperation(value = "Get Admin Ctmmny List", notes = "관리자 고객사 관리")
    @GetMapping("/admin/ctmmny/list")
    public Page<CtmmnyListResponseDTO> getAdminCtmmnyList(@RequestParam("page") int page, @RequestParam("ctmmnyCd") String ctmmnyCd, @RequestParam("ctmmnyNm") String ctmmnyNm, @RequestParam("isUsed") Boolean isUsed){
        LoggerFactory.getLogger(this.getClass()).info("-------------관리자 고객사 페이지: 조건에 맞는 리스트----------------");
          Pageable pageable = PageRequest.of(page, 5);
        return new PageImpl<CtmmnyListResponseDTO> (ctmmnyService.getCtmmnyListByCtmmnyCdAndCtmmnyNm(pageable, ctmmnyCd, ctmmnyNm, isUsed));
    }

    @ApiOperation(value = "Get registed Ctmmny", notes = "선택한 고객사 정보 가져오기")
    @GetMapping("/admin/ctmmnys/{ctmmnyCd}")
    public RegistCtmmnyResponseDTO getAdminCtmmny(@PathVariable String ctmmnyCd){
        LoggerFactory.getLogger(this.getClass()).info("-------------선택한 고객사 정보 가져오기----------------");
        return ctmmnyService.getCtmmnyInfoByCtmmnyCd(ctmmnyCd);
    }

    @ApiOperation(value = "update Ctmmny", notes = "선택한 고객사관련 정보 수정하기")
    @PostMapping("/admin/ctmmnys")
    public String updateCtmmnyInfo( @RequestBody updateCtmmnyRequestDTO updateCtmmnyRequestDTO){
        LoggerFactory.getLogger(this.getClass()).info("-------------고객사 정보 수정----------------");
        return ctmmnyService.updateByCtmmnyCd(updateCtmmnyRequestDTO.getCtmmnyCd());
    }

    @ApiOperation(value = "get ctmmnyCd & ctmmnyNm", notes = "등록된 고객사의 이름과 id 가져오기")
    @GetMapping("/admin/ctmmnys")
    public List<CtmmnyCdAndNmResponseDTO> getCtmmnyIdList(){
        LoggerFactory.getLogger(this.getClass()).info("-------------고객사 정보 조회----------------");
        return ctmmnyService.getCtmmnyIdAndNmList();
    }
}
