package com.example.helpworx.chtt.Service;

import com.example.helpworx.chtt.domain.ChttRoom;
import com.example.helpworx.chtt.dto.ChttRoomDto;
import com.example.helpworx.chtt.repository.ChttRoomRepository;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChattRoomService {
    private ChttRoomRepository chttRoomRepository;

    public ChattRoomService(ChttRoomRepository chttRoomRepository) {
        this.chttRoomRepository = chttRoomRepository;
    }

    public List<ChttRoom> getChttRoomListWithCondition(Pageable pageable, String sysNm, String status) {
        final ChttRoomDto chttRoomDto = ChttRoomDto.builder()
                .sysNm(sysNm)
                .status(status)
                .build();

        return chttRoomRepository.findChttRoomListWithCondition(pageable, chttRoomDto) ;
    }

}
