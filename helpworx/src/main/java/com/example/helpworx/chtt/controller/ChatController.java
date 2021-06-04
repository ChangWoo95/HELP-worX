package com.example.helpworx.chtt.controller;

import com.example.helpworx.chtt.domain.Chtt;
import com.example.helpworx.chtt.domain.ConvrsType;
import com.example.helpworx.chtt.repository.ChttRepository;
import com.example.helpworx.users.domain.Users;
import com.example.helpworx.users.repository.UsersRepository;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {
    @Autowired
    private SimpMessageSendingOperations simpMessageSendingOperations;
    @Autowired
    private ChttRepository chttRepository;
    @Autowired
    private UsersRepository usersRepository;

    @MessageMapping("/chat.sendMessage")
    public Chtt sendMessage(Chtt chtt) {
        Chtt savedChtt;
        Users user = usersRepository.findById(chtt.getUser().getId()).get();
        user.setRoles(null);
        user.setChttRooms(null);
        user.setUserPwd(null);
        if(ConvrsType.JOIN.equals(chtt.getConvrsType())){
            LoggerFactory.getLogger(this.getClass()).info("-------------컨트롤러 sendMessage JOIN ----------------");
            chtt.setUser(user);
            savedChtt = chttRepository.save(chtt);

            simpMessageSendingOperations.convertAndSend("/topic/public/"+chtt.getChttRoom().getId().toString(),chtt);
        }else{
            LoggerFactory.getLogger(this.getClass()).info("-------------컨트롤러 sendMessage CHAT ----------------");
            chtt.setUser(user);
            savedChtt = chttRepository.save(chtt);

            this.simpMessageSendingOperations.convertAndSend("/topic/public/"+savedChtt.getChttRoom().getId().toString(),savedChtt);
        }
        return savedChtt;
    }

}




