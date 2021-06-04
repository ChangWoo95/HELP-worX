package com.example.helpworx.sr.mapper;

import com.example.helpworx.users.domain.Users;
import com.example.helpworx.users.repository.UsersRepository;
import org.mapstruct.Mapper;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public class SrGetIDMapper {
    @Autowired
    private  UsersRepository usersRepository;

    public Long userToUserID(Users reqr) {
        LoggerFactory.getLogger(this.getClass()).info("예:"+reqr.getId());

        return reqr.getId();
    }
    public Users userIdToUser(Long reqr) {
        return usersRepository.findById(reqr).get();
    }

}