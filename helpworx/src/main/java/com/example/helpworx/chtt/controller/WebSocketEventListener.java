package com.example.helpworx.chtt.controller;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.security.core.session.SessionCreationEvent;
import org.springframework.security.core.session.SessionDestroyedEvent;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.*;


@Component
public class WebSocketEventListener {
    //event listner를 이용하여 소켓 연결(socket connect) 그리고 소켓 연결 끊기(disconnect) 이벤트를 수신하여
    //사용자가 채팅방을 참여(JOIN)하거나 떠날때(LEAVE)의 이벤트를 logging 하거나 broadcast 할 수 있습니다.


    @Autowired
    private SimpMessageSendingOperations messagingTemplate;
    //소켓 연결시
    @EventListener
    public void handleWebSocketConnectListener(SessionConnectEvent event) {

        LoggerFactory.getLogger(this.getClass()).info("-------------새로운 소켓 연결 시도----------------");
        LoggerFactory.getLogger(this.getClass()).info(event.toString());
    }
    @EventListener
    public void handleWebSocketConnectListener(SessionSubscribeEvent event) {

        LoggerFactory.getLogger(this.getClass()).info("-------------구독 이벤트----------------");
        LoggerFactory.getLogger(this.getClass()).info(event.toString());
    }
    @EventListener
    public void handleWebSocketConnectListener(SessionUnsubscribeEvent event) {

        LoggerFactory.getLogger(this.getClass()).info("-------------구독 취소 이벤트----------------");
        LoggerFactory.getLogger(this.getClass()).info(event.toString());
    }
    @EventListener
    public void handleWebSocketConnectListener(SessionDestroyedEvent event) {

        LoggerFactory.getLogger(this.getClass()).info("-------------세션 Destroy 이벤트----------------");
        LoggerFactory.getLogger(this.getClass()).info(event.toString());
    }
    @EventListener
    public void handleWebSocketConnectListener(SessionCreationEvent event) {

        LoggerFactory.getLogger(this.getClass()).info("-------------세션 Creation 이벤트----------------");
        LoggerFactory.getLogger(this.getClass()).info(event.toString());
    }

    //소켓 연결시
    @EventListener
    public void handleWebSocketConnectListener(SessionConnectedEvent event) {

        LoggerFactory.getLogger(this.getClass()).info("-------------새로운 소켓 연결 받음----------------");
        LoggerFactory.getLogger(this.getClass()).info(event.toString());
    }
// 소켓 종료시 
    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
//        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
//
//        String username = (String) headerAccessor.getSessionAttributes().get("username");
//        if(username != null) {
//            logger.info("User Disconnected : " + username);
//
//            ChatDto chatDto = new ChatDto();
//            chatDto.setType(TrayIcon.MessageType.LEAVE);
//            chatDto.setSender(username);
//
//            messagingTemplate.convertAndSend("/topic/public", chatDto); //연결된 모든 클라이언트들에게 퇴장 이벤트 broadcast 함
//        }
        LoggerFactory.getLogger(this.getClass()).info("-------------소켓 종료----------------");
        LoggerFactory.getLogger(this.getClass()).info(event.toString());

    }
}
