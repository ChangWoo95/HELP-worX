package com.example.helpworx.sr.mapper;

import com.example.helpworx.sr.domain.RcptTreat;
import com.example.helpworx.sr.dto.RcptTreatDto;
import com.example.helpworx.sr.dto.SrDto;
import com.example.helpworx.sr.resource.RcptTreatResource;
import com.example.helpworx.sr.resource.SrResource;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
@Mapper(componentModel = "spring")
public interface RcptTreatMapper {
    RcptTreatMapper INSTANCE = Mappers.getMapper(RcptTreatMapper.class);

    RcptTreatDto.Request ResourceToDto(RcptTreatResource.Request requestResource);
    RcptTreat DtoToEntity(RcptTreatDto.Request requestDto);
    RcptTreatDto.Response EntityToDto(RcptTreat rcptTreat);
    RcptTreatResource.Response DtoToResource(RcptTreatDto.Response responseDto);
}


