package com.example.helpworx.chtt.config;
//클라이언트가 메시지를 구독할 endpoint 핸드쉐이크 때 CORS 해제
//        (해제를 하지 않으면 다음과 같이 브라우저에서 CORS 에러가 발생합니다.)

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer  {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry){
        //app으로 시작 하는 메세지들을 controller에 있는 메세지 핸들링 메소드로 보내줍니다.
        registry.setApplicationDestinationPrefixes("/app");//메세지발행을요청

        //클라이언트가 메세지를 subscribe 할 endpoint 정의 //메세지구독을요청
        registry.enableSimpleBroker("/topic");   //send/subscribe 를 구독한 모든 클라이언트에게 메세지를 broadcast함
        //CORS 허용

    }
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry){
        registry
                .addEndpoint("/ws")
                .setAllowedOrigins("*").withSockJS();
    }

}
