package com.example.helpworx.chtt.controller;

import com.example.helpworx.auth.service.UserService;
import com.example.helpworx.chtt.Service.ChattRoomService;
import com.example.helpworx.chtt.domain.ChttRoom;
import com.example.helpworx.chtt.dto.ChttRoomDto;
import com.example.helpworx.chtt.mapper.ChttRoomMapper;

import com.example.helpworx.chtt.repository.ChttRoomRepository;
import com.example.helpworx.users.domain.Users;
import com.example.helpworx.users.mapper.UsersMapper;
import io.swagger.annotations.ApiOperation;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class ChttRoomController {
    private ChttRoomRepository chttRoomRepository;
    private ChattRoomService chttRoomService;
    private UserService userService;

    private ChttRoomController(ChttRoomRepository chttRoomRepository,ChattRoomService chttRoomService, UserService userService){
        this.chttRoomRepository = chttRoomRepository;
        this.chttRoomService = chttRoomService;
        this.userService = userService;
    }



    @ApiOperation(value = "get user's Chtt Rooms ", notes = "유저의 채팅방 리스트 조회")
    @GetMapping("/cust/chttrooms")
    public List<ChttRoom> getUsersChttRooms() {
        LoggerFactory.getLogger(this.getClass()).info("-------------유저의 채팅방 리스트 조회----------------");
        Long usersId = userService.getMyUserWithAuthorities().get().getId();
        return chttRoomRepository.findByUserId(usersId).stream().filter(
                chttRoom -> chttRoom.getStatus().equals("진행중")
        ).collect(Collectors.toList());
    }


    @ApiOperation(value = "get ChttRoom by Id ", notes = "ID로 채팅방 조회")
    @GetMapping("/cust/chttrooms/{id}")
    public ChttRoom getChttRoomById(@PathVariable Long id) {
        LoggerFactory.getLogger(this.getClass()).info("-------------ID로 채팅방 조회----------------");
        return chttRoomRepository.findById(id).get();
    }

    @ApiOperation(value = "get All Chtt Rooms ", notes = "전체 채팅방 리스트 조회")
    @GetMapping("/oper/chttrooms")
    public Page<ChttRoomDto> getAllChttRooms(Pageable pageable/*,PagedResourcesAssembler assembler 넣어서 HATAEOS 로 고도화*/) {
        LoggerFactory.getLogger(this.getClass()).info("안녕"+pageable);

        Page<ChttRoom> chttRoomPages = chttRoomRepository.findAll(pageable);
        return new PageImpl<ChttRoomDto>(
                chttRoomPages.getContent().stream().map(chttRoom -> ChttRoomMapper.INSTANCE.EntityToDto(chttRoom)).collect(Collectors.toList()));
    }

    @GetMapping("/cust/chttrooms/condition")
    public Page<ChttRoomDto> getChttRoomByCondition( @RequestParam("page") int  page , @RequestParam("sysNm") String sysNm, @RequestParam("status") String status    ){
        Pageable pageable = PageRequest.of(page, 5);

        LoggerFactory.getLogger(this.getClass()).info("-------------CHAT 리스트 조회---------------");
        List<ChttRoomDto> chttRoomDtoList =
                chttRoomService.getChttRoomListWithCondition(pageable,sysNm, status)
                .stream().map(
                        chttRoom -> ChttRoomMapper.INSTANCE.EntityToDto(chttRoom))
                        .collect(Collectors.toList()
                        );
        return new PageImpl<ChttRoomDto>(chttRoomDtoList);

    }





    @ApiOperation(value = "create Chtt Rooms ", notes = "채팅방 생성")
    @PostMapping("/cust/chttrooms")
    public ChttRoom getUsersChttRooms(@RequestBody ChttRoom chttRoom) {
        LoggerFactory.getLogger(this.getClass()).info("-------------채팅방 생성----------------");
        Users users = UsersMapper.INSTANCE.DtoToEntity(userService.getMyUserWithAuthorities().get());
        chttRoom.setUser(users);

        ChttRoom savedChttRoom = chttRoomRepository.save(chttRoom);
        ChttRoom re = ChttRoom.builder()
                .user(savedChttRoom.getUser())
                .id(savedChttRoom.getId())
                .chttList(savedChttRoom.getChttList())
                .chttEndTim(savedChttRoom.getChttEndTim())
                .chttStartTim(savedChttRoom.getChttStartTim())
                .status(savedChttRoom.getStatus())
                .sysNm(savedChttRoom.getSysNm())
                .build();
        return re;
    }


    @ApiOperation(value = "update Chtt Rooms ", notes = "채팅방 수정")
    @PostMapping("/cust/chttrooms/{id}")
    public ChttRoom getUsersChttRooms(@RequestBody ChttRoom chttRoom,@PathVariable Long id) {
        LoggerFactory.getLogger(this.getClass()).info("-------------채팅방 수정----------------");
        ChttRoom updatedChttRoom = chttRoomRepository.findById(id).get();
        updatedChttRoom.setSysNm(chttRoom.getSysNm());
        updatedChttRoom.setStatus(chttRoom.getStatus());
        return chttRoomRepository.save(updatedChttRoom);
    }


    }
