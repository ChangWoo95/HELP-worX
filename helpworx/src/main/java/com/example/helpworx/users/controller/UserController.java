package com.example.helpworx.users.controller;
import com.example.helpworx.auth.service.UserService;
import com.example.helpworx.auth.dto.UserDto;
import com.example.helpworx.auth.dto.UserWithRoleDto;
import com.example.helpworx.ctmmny.dto.CtmmnySysDTO;
import com.example.helpworx.ctmmny.dto.RegistCtmmnyRequestDTO;

import com.example.helpworx.ctmmny.dto.updateCtmmnyRequestDTO;

import com.example.helpworx.sr.dto.SrDto;

import com.example.helpworx.users.domain.UserRoleMap;
import com.example.helpworx.users.domain.Users;
import com.example.helpworx.users.dto.*;
import io.swagger.annotations.ApiOperation;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signup")
    public ResponseEntity<Users> signup(
            @Valid @RequestBody UserDto userDto
    ) {
        return ResponseEntity.ok(userService.signup(userDto));
    }

    @GetMapping("/user")
    public ResponseEntity<UserWithRoleDto> getMyUserInfo() {
        LoggerFactory.getLogger(this.getClass()).info("-------------유저정보 조회 ----------------");

        UsersDto usersDto = userService.getMyUserWithAuthorities().get();

        List<UserRoleMap> userRoleMaps = usersDto.getRoles();
        List<String> roles = new ArrayList<>();

        for ( UserRoleMap userRoleMap : userRoleMaps ) {
            roles.add(userRoleMap.getRole().getRoleNm());
        }

        return ResponseEntity.ok(UserWithRoleDto.builder()
                .userId(usersDto.getId())
                .roles(roles)
                .userEmail(usersDto.getUserEmail())
                .userNm(usersDto.getUserNm())
                .userTeleno(usersDto.getUserTeleno())
                .ctmmny(usersDto.getCtmmny())
                .build());

    }

    @GetMapping("/user/{useremail}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<Users> getUserInfo(@PathVariable String useremail) {
        return ResponseEntity.ok(userService.getUserWithAuthorities(useremail).get());
    }


    @ApiOperation(value = "update User", notes = "선택한 고객관련 정보 수정하기")
    @PostMapping("/admin/users")
    public Long updateUserInfo( @RequestBody UpdateUserRequestDTO updateUserRequestDTO) {
        LoggerFactory.getLogger(this.getClass()).info("-------------고객사 정보 수정----------------");
        return userService.updateByUserId(updateUserRequestDTO.getUserId());
    }

    @GetMapping("/admin/users/list")
    public Page<UserInfoResponseDTO> getUserInfoList(@RequestParam("page") int page, @RequestParam("userId") Long UserId, @RequestParam("userNm") String userNm, @RequestParam("ctmmnyCd") String ctmmnyCd, @RequestParam("isUsed") Boolean isUsed) {
        LoggerFactory.getLogger(this.getClass()).info("-------------유저 리스트 조회 ----------------");
        Pageable pageable = PageRequest.of(page, 5);
        return new PageImpl<UserInfoResponseDTO>(userService.getUserInfoList(pageable, UserId, userNm, ctmmnyCd, isUsed));
    }

    @GetMapping("/admin/users/{userId}")
    public UserDetailResponseDTO getUserInfo(@PathVariable Long userId) {
        LoggerFactory.getLogger(this.getClass()).info("-------------고객 ID 조회 ----------------");

        return userService.getUserInfo(userId);
    }

    @ApiOperation(value = "Regist Users", notes = "고객 등록")
    @PostMapping("/admin/user/registing")
    public RegistingUser.ResponseDTO registUser(@RequestBody RegistingUser.RequestDTO requestDTO) {
        LoggerFactory.getLogger(this.getClass()).info("-------------고객 등록----------------");
        return userService.saveUser(requestDTO);
    }
}