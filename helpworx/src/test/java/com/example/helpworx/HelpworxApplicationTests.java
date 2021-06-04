package com.example.helpworx;

import com.example.helpworx.users.repository.UsersRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class HelpworxApplicationTests {

    @Autowired
    UsersRepository usersRepository;

    @Test
    public void insertTest(){
        for(int i = 0; i < 5; ++i){

        }
    }

    @Test
    void contextLoads() {
    }


}
