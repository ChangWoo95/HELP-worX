package com.example.helpworx.sr.service;

import com.example.helpworx.sr.domain.RcptTreat;
import com.example.helpworx.sr.dto.RcptTreatDto;
import com.example.helpworx.sr.mapper.RcptTreatMapper;
import com.example.helpworx.sr.repository.RcptTreatRepository;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RcptTreatService {
    private RcptTreatRepository rcptTreatRepository ;

    public RcptTreatService(RcptTreatRepository rcptTreatRepository){
        this.rcptTreatRepository = rcptTreatRepository;
    }
    public RcptTreat saveEmptyRcptTreat() throws Exception{
        return rcptTreatRepository.save(new RcptTreat());
    }

    public RcptTreat saveRcptTreat(RcptTreatDto.Request rcptTreatDto) throws Exception{
        return rcptTreatRepository.save ( RcptTreatMapper.INSTANCE.DtoToEntity(rcptTreatDto));
    }

    public Optional<RcptTreat> updateById(Long rcptTreatId, RcptTreatDto.Request rcptTreatRequestDto) {
        Optional<RcptTreat> rcptTreat = rcptTreatRepository.findById(rcptTreatId);
        rcptTreat.ifPresent(updatedRcptTreat->{
            updatedRcptTreat.setCmplPrdt(rcptTreatRequestDto.getCmplPrdt());
            updatedRcptTreat.setRcptDscr(rcptTreatRequestDto.getRcptDscr());
            updatedRcptTreat.setTreatDscr(rcptTreatRequestDto.getTreatDscr());
            updatedRcptTreat.setTreatEndTim(rcptTreatRequestDto.getTreatEndTim());
            updatedRcptTreat.setTreatStartTim(rcptTreatRequestDto.getTreatStartTim());
            updatedRcptTreat.setRcptTim(rcptTreatRequestDto.getRcptTim());
            updatedRcptTreat.setOpetrNm(rcptTreatRequestDto.getOpetrNm());
            updatedRcptTreat.setRecivrNm(rcptTreatRequestDto.getRecivrNm());
            updatedRcptTreat.setTreatTim(rcptTreatRequestDto.getTreatTim());
            rcptTreatRepository.save(updatedRcptTreat);
        });
        return rcptTreat;
    }
}
