package com.example.helpworx;

import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InitController {

    @ApiOperation(value = "swagger test url", notes = "swagger test url")
    @GetMapping("/api/hello")
    public String hello() {
        return "hello1";
    }
}
