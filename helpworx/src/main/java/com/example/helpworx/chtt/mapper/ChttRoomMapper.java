package com.example.helpworx.chtt.mapper;

import com.example.helpworx.chtt.domain.ChttRoom;
import com.example.helpworx.chtt.dto.ChttRoomDto;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ChttRoomMapper {
    ChttRoomMapper INSTANCE = Mappers.getMapper(ChttRoomMapper.class);

    ChttRoom DtoToEntity(ChttRoomDto chttRoomDto);
    @Mapping(target = "userNm" , expression ="java(chttRoom.getUser().getUserNm())" )
    @Mapping(target = "ctmmnyNm" , expression ="java(chttRoom.getUser().getCtmmny().getCtmmnyNm())" )
    ChttRoomDto EntityToDto(ChttRoom chttRoom);

}
