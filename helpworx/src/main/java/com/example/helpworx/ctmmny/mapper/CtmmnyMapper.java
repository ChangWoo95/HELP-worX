package com.example.helpworx.ctmmny.mapper;

import com.example.helpworx.ctmmny.domain.Ctmmny;
import com.example.helpworx.ctmmny.dto.CtmmnyDto;
import com.example.helpworx.ctmmny.resource.CtmmnyResource;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
@Mapper(componentModel = "spring")
public interface CtmmnyMapper {

    CtmmnyMapper INSTANCE = Mappers.getMapper(CtmmnyMapper.class);

    Ctmmny DtoToEntity(CtmmnyDto sysDto);
    CtmmnyDto.SrSearchBarResponse EntityToSrSearchBarResponseDto(Ctmmny sys);
    CtmmnyDto EntityToDto(Ctmmny sys);
    CtmmnyDto ResourceToDto(CtmmnyResource sysResource);
    CtmmnyResource DtoToResource(CtmmnyDto ctmmnyDto);
    CtmmnyResource.SrSearchBarResponse SrSearchBarResponseDtoToSrSearchBarResponseResource(CtmmnyDto.SrSearchBarResponse srSearchBarResponse);

}
