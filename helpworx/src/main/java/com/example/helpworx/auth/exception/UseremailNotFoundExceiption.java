package com.example.helpworx.auth.exception;

import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class UseremailNotFoundExceiption extends UsernameNotFoundException  {
    public UseremailNotFoundExceiption(String msg) {
        super(msg);
    }
}
