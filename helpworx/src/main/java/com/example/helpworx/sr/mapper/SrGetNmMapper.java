package com.example.helpworx.sr.mapper;

import com.example.helpworx.users.domain.Users;
import com.example.helpworx.users.repository.UsersRepository;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public class SrGetNmMapper {
    @Autowired
    private UsersRepository usersRepository;

    public String userToUserName(Users reqr) {

        return reqr.getUserNm();
    }
//    public Users userNameToUser(String userNm) {
//        return usersRepository.findByUserNm(userNm).get();
//    }

}
