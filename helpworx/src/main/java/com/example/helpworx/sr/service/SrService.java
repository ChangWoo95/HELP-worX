package com.example.helpworx.sr.service;

import com.example.helpworx.auth.service.UserService;
import com.example.helpworx.ctmmny.domain.Ctmmny;
import com.example.helpworx.ctmmny.repository.CtmmnyRepository;
import com.example.helpworx.sr.domain.RcptTreat;
import com.example.helpworx.sr.domain.Sr;
import com.example.helpworx.sr.dto.RcptTreatDto;
import com.example.helpworx.sr.dto.response.*;
import com.example.helpworx.sr.dto.SrDto;
import com.example.helpworx.sr.mapper.SrMapper;
import com.example.helpworx.sr.repository.SrRepository;

import com.example.helpworx.users.domain.Users;
import com.example.helpworx.users.dto.UsersDto;
import com.example.helpworx.users.mapper.UsersMapper;
import lombok.AllArgsConstructor;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class SrService {

    private final SrRepository srRepository;
    private final CtmmnyRepository ctmmnyRepository;
    private final UserService userService;
    private RcptTreatService rcptTreatService;

    public SrDto.ListResponse saveSr(SrDto.ListRequest srRequestDto) throws Exception{

        Sr sr = SrMapper.INSTANCE.DtoToEntity(srRequestDto);
        RcptTreat emptyRcptTreat = rcptTreatService.saveEmptyRcptTreat();
        UsersDto usersDto = userService.getMyUserWithAuthorities().get();
        Users users = UsersMapper.INSTANCE.DtoToEntity(usersDto);

        sr.setReqr(users);
        sr.setCtmmny(users.getCtmmny());
        sr.setReqTim(LocalDateTime.now());
        sr.setRcptTreat(emptyRcptTreat);
        Sr savedSr = srRepository.save(sr);

        return  new SrDto.ListResponse(savedSr); // EntityToDto가 잘안됨
    }

    public Page<SrDto.ListResponse> getSrList(Pageable pageable) {
        Page<Sr> srPages = srRepository.findAll(pageable);
        return new PageImpl<SrDto.ListResponse>(
                srPages.getContent().stream()
                .map(SrDto.ListResponse ::new)
                .collect(Collectors.toList()));
    }
    public Page<SrDto.DetailResponse> getSrListForRcptTreat(Pageable pageable) {
        return new PageImpl<SrDto.DetailResponse>(srRepository.findAll(pageable).getContent().stream()
                .map(sr ->SrMapper.INSTANCE.EntityToDto(sr))
                .collect(Collectors.toList()));
    }

    public List<SrDto.ListResponse> getSrListWithCondition(Pageable pageable,String ctmmnyCd , String sysNm, String jobGb, String id, String reqGb, String status, String sbjct, String dscr) {
        final Long realId = userService.getMyUserWithAuthorities().get().getId();

        final SrDto.SrsListRequest srListRequestDto = SrDto.SrsListRequest.builder()
                .sysNm(sysNm)
                .jobGb(jobGb)
                .id( id.equals("나의 SR") ? realId : null )
                .reqGb(reqGb)
                .status(status)
                .sbjct(sbjct)
                .dscr(dscr)
                .ctmmnyCd(ctmmnyCd)
                .build();

        return srRepository.findSrListWithCondition(pageable,srListRequestDto);
    }

    public List<OperatorIncompleteSrDTO> getIncompleteSrList() {
        Optional<UsersDto> usersDto = userService.getMyUserWithAuthorities();
        final String ctmmnyCd = usersDto.get().getCtmmny().getId();
        return srRepository.findIncompleteSrList(ctmmnyCd);
    }

    public List<CustomerIncompleteUserSrDTO> getCustomerIncompleteUserSrList() {
        Optional<UsersDto> usersDto = userService.getMyUserWithAuthorities();
        final Long userId = usersDto.get().getId();
        return srRepository.findIncompleteSrListByUserId(userId);
    }

    public List<CustomerIncompleteCtmmnySrDTO> getCustomerIncompleteCtmmnySrList() {
        Optional<UsersDto> usersDto = userService.getMyUserWithAuthorities();
        final String ctmmnyCd = usersDto.get().getCtmmny().getId();
        return srRepository.findIncompleteSrListByCtmmnyId(ctmmnyCd);
    }

    public List<OperatorProceedingSrDTO> getProceedingSrList() {
        Optional<UsersDto> usersDto = userService.getMyUserWithAuthorities();
        final String ctmmnyCd = usersDto.get().getCtmmny().getId();
        return srRepository.findProceedingSrList(ctmmnyCd);
    }

    public List<SystemPairResponseDTO> getSystemNameList(){
        return srRepository.findIdAndNameByName();
    }

    public TreatTimesResponseDTO findAverageRcptTreatTime(String ctmmnyCd, String name) {
        Long averageRcptTime = srRepository.findAverageRcptTime(ctmmnyCd, name);
        Long averageTreatTime = srRepository.findAverageTreatTime(ctmmnyCd, name);
        return TreatTimesResponseDTO.builder()
                .averageRcptTime(averageRcptTime)
                .averageTreatTime(averageTreatTime)
                .build();
    }
    public SrCountsResponseDTO findOperCtmmnyStatusByReqGbAndCtmmnyCd(String ctmmnyCd, String name) {

        final Long dataModificationCounts = srRepository.countByCtmmnyCdAndSysNmAndReqGb(ctmmnyCd, name, "데이터수정");
        final Long improvingCounts = srRepository.countByCtmmnyCdAndSysNmAndReqGb(ctmmnyCd, name, "개선");
        final Long errorCounts = srRepository.countByCtmmnyCdAndSysNmAndReqGb(ctmmnyCd, name, "오류");
        final Long questionCounts = srRepository.countByCtmmnyCdAndSysNmAndReqGb(ctmmnyCd, name,"문의");
        final Long totalReqGbCounts = srRepository.countByCtmmnyCdAndSysNmAndReqGb(ctmmnyCd, name, "");

        final Long currentMonthCounts = srRepository.countByCtmmnyCdAndSysNmAndMonth(ctmmnyCd, name, LocalDateTime.now().getMonthValue());
        final Long prevMonthCounts = srRepository.countByCtmmnyCdAndSysNmAndMonth(ctmmnyCd, name, LocalDateTime.now().minusMonths(1).getMonthValue());

        return SrCountsResponseDTO.builder()
                .dataModificationCounts(dataModificationCounts)
                .improvingCounts(improvingCounts)
                .errorCounts(errorCounts)
                .questionCounts(questionCounts)
                .totalCounts(totalReqGbCounts)
                .currentMonthCounts(currentMonthCounts)
                .prevMonthCounts(prevMonthCounts)
                .build();
    }
    public SrCountsResponseDTO findCtmmnySrCountsByReqGbAndCurrentMonthAndPrevMonth(String name){
        final Optional<UsersDto> usersDto = userService.getMyUserWithAuthorities();
        final Ctmmny ctmmny = usersDto.get().getCtmmny();

        final Long dataModificationCounts = srRepository.countByCtmmnyAndSysNmAndReqGb(ctmmny, name, "데이터수정");
        final Long improvingCounts = srRepository.countByCtmmnyAndSysNmAndReqGb(ctmmny, name, "개선");
        final Long errorCounts = srRepository.countByCtmmnyAndSysNmAndReqGb(ctmmny,name, "오류");
        final Long questionCounts = srRepository.countByCtmmnyAndSysNmAndReqGb(ctmmny,name, "문의");
        final Long totalReqGbCounts = srRepository.countByCtmmnyAndSysNmAndReqGb(ctmmny, name, "");

        final Long currentMonthCounts = srRepository.countByCtmmnyAndSysNmAndMonth(ctmmny, name, LocalDateTime.now().getMonthValue());
        final Long prevMonthCounts = srRepository.countByCtmmnyAndSysNmAndMonth(ctmmny, name, LocalDateTime.now().minusMonths(1).getMonthValue());

        return SrCountsResponseDTO.builder()
                .dataModificationCounts(dataModificationCounts)
                .improvingCounts(improvingCounts)
                .errorCounts(errorCounts)
                .questionCounts(questionCounts)
                .totalCounts(totalReqGbCounts)
                .currentMonthCounts(currentMonthCounts)
                .prevMonthCounts(prevMonthCounts)
                .build();
    }

    public SrDto.DetailResponse getSrById(Long id) {
        return SrMapper.INSTANCE.EntityToDto(srRepository.findById(id).get());
    }

    public List<SrRankDTO> getRankList(String name) {
        return srRepository.findRankListBySysNm(name);
    }

    public SrDto.DetailResponseForOper getSrForRcptTreat(Long id) {
        return SrMapper.INSTANCE.EntityToDetailDtoForOper(srRepository.findById(id).get());

    }

    public SrDto.DetailResponseForOper updateSrWithRcptTreat(Long srId,RcptTreatDto.Request rcptTreatRequestDto,String status) throws Exception {
        Sr sr = srRepository.findById(srId).get();
        sr.setStatus(status);
        LoggerFactory.getLogger(this.getClass()).info(String.valueOf(srId +" "+ rcptTreatRequestDto.toString() + " " +status));
        if (sr.getRcptTreat() == null ) {
            RcptTreat rcptTreat = rcptTreatService.saveRcptTreat(rcptTreatRequestDto);
            sr.setRcptTreat(rcptTreat);
        }else{
            Long rcptTreatId = sr.getRcptTreat().getId();
            sr.setRcptTreat(rcptTreatService.updateById(rcptTreatId,rcptTreatRequestDto).get());
        }

        srRepository.save(sr);
        return SrMapper.INSTANCE.EntityToDetailDtoForOper(sr);
    }

    public List<OperatorCtmmnyChartListDTO.srResponse> getCtmmnySrListByMonthWithSomeCondition(String ctmmnyCd, String sysNm) {
        List<OperatorCtmmnyChartListDTO.srResponse> operatorCtmmnyChartListDTOList = new ArrayList<>();
        if(ctmmnyCd.length() == 0 ){
            List<Ctmmny> ctmmnys = ctmmnyRepository.findAll();

            for(Ctmmny ctmmny : ctmmnys) {
                String ctmmnyNm = ctmmny.getCtmmnyNm();
                List<OperatorChartListByMonthDTO> monthList = srRepository.findSrListByCtmmnyAndSysNm(ctmmny.getId(), sysNm);
                OperatorCtmmnyChartListDTO.srResponse dtoElement = OperatorCtmmnyChartListDTO.srResponse.builder().ctmmnyNm(ctmmnyNm).monthList(monthList).build();
                operatorCtmmnyChartListDTOList.add(dtoElement);
            }
        } else {
            String ctmmnyNm = ctmmnyRepository.findById(ctmmnyCd).get().getCtmmnyNm();
            List<OperatorChartListByMonthDTO> monthList = srRepository.findSrListByCtmmnyAndSysNm(ctmmnyCd, sysNm);
            OperatorCtmmnyChartListDTO.srResponse dtoElement = OperatorCtmmnyChartListDTO.srResponse.builder().ctmmnyNm(ctmmnyNm).monthList(monthList).build();
            operatorCtmmnyChartListDTOList.add(dtoElement);
        }
        return operatorCtmmnyChartListDTOList;

    }


    public List<OperatorCtmmnyChartListDTO.treatResponse> getTreatListByMonth(String ctmmnyCd, String sysNm) {
        List<OperatorCtmmnyChartListDTO.treatResponse> operatorCtmmnyChartListDTOList = new ArrayList<>();
        if(ctmmnyCd.length() == 0 ){
            List<Ctmmny> ctmmnys = ctmmnyRepository.findAll();

            for(Ctmmny ctmmny : ctmmnys) {
                String ctmmnyNm = ctmmny.getCtmmnyNm();
                List<OperatorTreatListByMonthDTO> monthList = srRepository.findTreatListByCtmmnyAndSysNm(ctmmny.getId(), sysNm);
                OperatorCtmmnyChartListDTO.treatResponse dtoElement = OperatorCtmmnyChartListDTO.treatResponse.builder().ctmmnyNm(ctmmnyNm).monthList(monthList).build();
                operatorCtmmnyChartListDTOList.add(dtoElement);
            }
        } else {
            String ctmmnyNm = ctmmnyRepository.findById(ctmmnyCd).get().getCtmmnyNm();
            List<OperatorTreatListByMonthDTO> monthList = srRepository.findTreatListByCtmmnyAndSysNm(ctmmnyCd, sysNm);
            OperatorCtmmnyChartListDTO.treatResponse dtoElement = OperatorCtmmnyChartListDTO.treatResponse.builder().ctmmnyNm(ctmmnyNm).monthList(monthList).build();
            operatorCtmmnyChartListDTOList.add(dtoElement);
        }
        return operatorCtmmnyChartListDTOList;
    }
}
