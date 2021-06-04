package com.example.helpworx.ctmmny.controller;

import com.example.helpworx.auth.service.UserService;
import com.example.helpworx.ctmmny.domain.CtmmnySys;
import com.example.helpworx.ctmmny.domain.Sys;
import com.example.helpworx.ctmmny.dto.SysDto;
import com.example.helpworx.ctmmny.mapper.SysMapper;
import com.example.helpworx.ctmmny.repository.CtmmnyRepository;
import com.example.helpworx.ctmmny.resource.SysResource;
import com.example.helpworx.ctmmny.service.SysService;
import io.swagger.annotations.ApiOperation;
import org.mapstruct.factory.Mappers;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class SysController {
    private final SysService sysService;
    private final CtmmnyRepository ctmmnyRepository;
    private final UserService userService;

    public SysController(SysService sysService, CtmmnyRepository ctmmnyRepository, UserService userService){
        this.sysService = sysService;
        this.ctmmnyRepository = ctmmnyRepository;
        this.userService = userService;
    }

    @ApiOperation(value = "Get My Sys Info", notes = "로그인된 고객의 고객사가 사용중인 시스템 정보 조회")
    @GetMapping("/cust/sys")
    public ResponseEntity<List<SysResource>> findMyCtmmnySys() {
        LoggerFactory.getLogger(this.getClass()).info("-------------로그인된 고객의 고객사가 사용중인 시스템 정보 조회----------------");

        List<SysResource> sysResourceList = sysService.getMySysList()
                .stream().map(sysDto -> SysMapper.INSTANCE.DtoToResource(sysDto))
                .collect(Collectors.toList());

        return new ResponseEntity<>(sysResourceList,HttpStatus.OK);

    }
}


//        //Dto to Resource 예시
//        List<SysDto> sysDtoList =  sysService.getSysList();
//        List<SysResource> sysResourceList = sysDtoList.stream()
//                .map(sysDto->SysMapper.INSTANCE.DtoToResource(sysDto))
//                .collect(Collectors.toList());
