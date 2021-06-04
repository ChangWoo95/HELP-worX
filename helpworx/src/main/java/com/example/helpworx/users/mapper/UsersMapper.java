package com.example.helpworx.users.mapper;

import com.example.helpworx.ctmmny.domain.Sys;
import com.example.helpworx.ctmmny.dto.SysDto;
import com.example.helpworx.ctmmny.resource.SysResource;
import com.example.helpworx.users.domain.Users;
import com.example.helpworx.users.dto.UsersDto;
import com.example.helpworx.users.resource.UsersResource;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UsersMapper {

    UsersMapper INSTANCE = Mappers.getMapper(UsersMapper.class);

    Users DtoToEntity(UsersDto usersDto);
    UsersDto EntityToDto(Users users);
    UsersDto ResourceToDto(UsersResource sysResource);
    UsersResource DtoToResource(UsersDto sysDto);

}