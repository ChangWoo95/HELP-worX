package com.example.helpworx.chtt.mapper;

import com.example.helpworx.chtt.domain.Chtt;
import com.example.helpworx.chtt.domain.ChttRoom;
import com.example.helpworx.chtt.dto.ChttDto;
import com.example.helpworx.chtt.dto.ChttRoomDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ChttMapper {

    ChttMapper INSTANCE = Mappers.getMapper(ChttMapper.class);

    Chtt DtoToEntity(ChttDto chttDto);
    @Mapping(target = "userNm" , expression ="java(chtt.getUser().getUserNm())" )
    @Mapping(target = "ctmmnyNm" , expression ="java(chtt.getUser().getCtmmny().getCtmmnyNm())" )
    ChttDto EntityToDto(Chtt chtt);


}
