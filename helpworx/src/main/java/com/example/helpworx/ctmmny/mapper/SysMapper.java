package com.example.helpworx.ctmmny.mapper;

import com.example.helpworx.ctmmny.domain.Sys;
import com.example.helpworx.ctmmny.dto.SysDto;
import com.example.helpworx.ctmmny.resource.SysResource;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface SysMapper {

    SysMapper INSTANCE = Mappers.getMapper(SysMapper.class);

//    @Mapping(target ="jobs",ignore = true)
    Sys DtoToEntity(SysDto sysDto);
//    @Mapping(target ="jobs",ignore = true)
    SysDto EntityToDto(Sys sys);
    SysDto ResourceToDto(SysResource sysResource);
//    @Mapping(target ="jobs",ignore = true)
    SysResource DtoToResource(SysDto sysDto);

}
