package com.example.helpworx.ctmmny.service;

import com.example.helpworx.auth.service.UserService;
import com.example.helpworx.ctmmny.mapper.SysMapper;
import com.example.helpworx.ctmmny.domain.Sys;
import com.example.helpworx.ctmmny.dto.SysDto;
import com.example.helpworx.ctmmny.repository.SysRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SysService {

    private SysRepository sysRepository;
    private UserService userService;

    public Optional<Sys> getSys(Long id) {
        return sysRepository.findById(id);
    }

    public SysService(SysRepository sysRepository, UserService userService){
        this.sysRepository = sysRepository;
        this.userService = userService;
    }

    public List<SysDto> getSysList() {

        List<Sys> sysList = sysRepository.findAll();
        List<SysDto> sysDtoList = sysList
                .stream()
                .map(s->SysMapper.INSTANCE.EntityToDto(s))
                .collect(Collectors.toList());

        return sysDtoList;
    }

    public List<SysDto> getMySysList(){

        return userService.getMyUserWithAuthorities().get()

                    .getCtmmny().getCtmmnySysList()
                    .stream().map(ctmmnySys -> ctmmnySys.getSys())
                    .collect(Collectors.toList())

                    .stream().map(sys -> SysMapper.INSTANCE.EntityToDto(sys))
                    .collect(Collectors.toList());

    }

}
