package com.example.helpworx.sr.mapper;

import com.example.helpworx.sr.domain.Sr;
import com.example.helpworx.sr.dto.SrDto;
import com.example.helpworx.sr.resource.SrResource;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
@Mapper(componentModel = "spring", uses = {SrGetIDMapper.class})
public interface SrMapper {
    SrMapper INSTANCE = Mappers.getMapper(SrMapper.class);

    Sr DtoToEntity(SrDto.ListRequest srRequestDto);
    Sr DtoToEntity(SrDto.DetailRequest srRequestDto);

    @Mapping(target = "reqr" , expression ="java(sr.getReqr().getUserNm())" )
    SrDto.DetailResponse EntityToDto(Sr sr);
    @Mapping(target = "reqr" , expression ="java(sr.getReqr().getUserNm())" )
    SrDto.DetailResponseForOper EntityToDetailDtoForOper(Sr sr);

    SrDto.ListRequest ResourceToDto(SrResource.RequestResource srRequestResource);
    SrDto.DetailRequest ResourceToDto(SrResource.DetailRequest srRequestResource);
    SrDto.DetailRequestForOper ResourceToDto(SrResource.DetailRequestForOper srRequestResource);

    SrResource.ResponseResource DtoToResource(SrDto.ListResponse srResponseDto);
    SrResource.DetailResponse DtoToResource(SrDto.DetailResponse srResponseDto);
    SrResource.DetailResponseForOper DtoToResource(SrDto.DetailResponseForOper srResponseDto);
}
