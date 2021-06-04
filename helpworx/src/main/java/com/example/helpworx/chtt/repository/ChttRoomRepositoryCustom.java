package com.example.helpworx.chtt.repository;

import com.example.helpworx.chtt.domain.ChttRoom;
import com.example.helpworx.chtt.dto.ChttRoomDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ChttRoomRepositoryCustom {
    List<ChttRoom> findChttRoomListWithCondition(Pageable pageable, ChttRoomDto chttRoomDto);
}
