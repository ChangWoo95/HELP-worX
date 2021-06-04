package com.example.helpworx.ctmmny.service;

import com.example.helpworx.auth.service.UserService;
import com.example.helpworx.ctmmny.domain.Ctmmny;
import com.example.helpworx.ctmmny.domain.CtmmnySys;
import com.example.helpworx.ctmmny.domain.Sys;
import com.example.helpworx.ctmmny.dto.*;
import com.example.helpworx.ctmmny.mapper.CtmmnyMapper;
import com.example.helpworx.ctmmny.repository.CtmmnyRepository;
import com.example.helpworx.ctmmny.repository.CtmmnySysRepository;
import com.example.helpworx.ctmmny.repository.SysRepository;
import com.example.helpworx.sr.repository.SrRepository;
import com.example.helpworx.users.domain.Users;
import com.example.helpworx.users.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class CtmmnyService {
    private final SrRepository srRepository;
    private final CtmmnyRepository ctmmnyRepository;
    private final SysRepository sysRepository;
    private final UsersRepository usersRepository;
    private final CtmmnySysRepository ctmmnySysRepository;
    private final UserService userService;

    public Ctmmny getMyCtmmny(){
        return userService.getMyUserWithAuthorities().get().getCtmmny();
    }

    @Transactional
    public Ctmmny saveCtmmny(Ctmmny ctmmny) {
        return ctmmnyRepository.save(ctmmny);
    }

    @Transactional
    public List<CtmmnySysDTO> registerCtmmny(RegistCtmmnyRequestDTO requestDTO){
        final Ctmmny ctmmny = saveCtmmny(requestDTO.toEntity()); // 고객사 등록
        List<CtmmnySys> ctmmnySysList = getCtmmnySysList(ctmmny, requestDTO.getSystemIdList());
        return  ctmmnySysRepository.saveAll(ctmmnySysList).stream()
                .map(CtmmnySysDTO ::new)
                .collect(Collectors.toList());
    }

    public List<CtmmnySys> getCtmmnySysList(Ctmmny ctmmny, List<Long> systemIdList) {
        List<CtmmnySys> ctmmnySysList = new ArrayList<>();

        for(Long id : systemIdList) {
            final Sys sys = sysRepository.findById(id).get();
            CtmmnySys ctmmnySys = CtmmnySys.builder()
                                           .ctmmny(ctmmny)
                                            .sys(sys)
                                            .build();
            ctmmnySysList.add(ctmmnySys);
        }
        return ctmmnySysList;
    }

    public List<CtmmnyDto.SrSearchBarResponse> getCtmmnyList(){
        return ctmmnyRepository.findAll().stream().map(
                ctmmny -> CtmmnyMapper.INSTANCE.EntityToSrSearchBarResponseDto(ctmmny)
        ).collect(Collectors.toList());
    }


    public List<CtmmnyListResponseDTO> getCtmmnyListByCtmmnyCdAndCtmmnyNm(Pageable pageable, String ctmmnyCd, String ctmmnyNm, Boolean isUsed){
        return ctmmnyRepository.findByCtmmnyCdAndCtmmnyNm(pageable, ctmmnyCd, ctmmnyNm, isUsed).stream()
                .map(CtmmnyListResponseDTO ::new)
                .distinct()
                .collect(Collectors.toList());
    }
    public RegistCtmmnyResponseDTO getCtmmnyInfoByCtmmnyCd(String ctmmnyCd) {
        final Ctmmny result = ctmmnyRepository.findById(ctmmnyCd).get();
        List<Long> systemIdList = ctmmnySysRepository.findIdListByCtmmnyCd(ctmmnyCd);
        List<SystemInfoDTO> systemInfoList = ctmmnySysRepository.findSysNameListbyCtmmnyCd(ctmmnyCd);
        return RegistCtmmnyResponseDTO.builder()
                .ctmmnyCd(result.getId())
                .ctmmnyNm(result.getCtmmnyNm())
                .ctmmnyDscr(result.getCtmmnyDscr())
                .contBeginDate(result.getContBeginDate())
                .contEndDate(result.getContEndDate())
                .systemIdList(systemIdList).build();
    }
    public List<CtmmnyCdAndNmResponseDTO> getCtmmnyIdAndNmList() {
        return ctmmnyRepository.findListByCtmmnyCdAndCtmmnyNm();
    }

    @Transactional
    public String updateByCtmmnyCd(String ctmmnyCd) {
        Ctmmny ctmmny = ctmmnyRepository.findById(ctmmnyCd).get();
        ctmmny.setIsUsed(false);
        List<Users> userList = usersRepository.findAllByCtmmnyCd(ctmmnyCd);
        List<Users> changeUserList = new ArrayList<>();

        for(Users user: userList) {
            user.setIsUsed(false);
            changeUserList.add(user);
        }
        String id = ctmmnyRepository.save(ctmmny).getId();
        usersRepository.saveAll(changeUserList);

//        return sysId;
        return id;
    }
}
