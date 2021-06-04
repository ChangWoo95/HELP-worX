package com.example.helpworx.auth.service;

import com.example.helpworx.auth.exception.UseremailNotFoundExceiption;
import com.example.helpworx.users.domain.Users;
import com.example.helpworx.users.repository.UsersRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Component("userDetailsService")
public class CustomUserDetailsService implements UserDetailsService {
    private final UsersRepository usersRepository;
    public CustomUserDetailsService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(final String userEmail) {
        return usersRepository.findOneWithRolesByUserEmail(userEmail)
                .map(user -> createUsers(user))
                .orElseThrow(() -> new UseremailNotFoundExceiption(userEmail + "데이터 베이스에서 찾을 수 없습니다."));
    }

    private org.springframework.security.core.userdetails.User createUsers( Users user) {
        List<GrantedAuthority> grantedAuthorities = user.getRoles().stream()
                .map(userRoleMap -> new SimpleGrantedAuthority(userRoleMap.getRole().getRoleNm()))
                .collect(Collectors.toList());
        return new org.springframework.security.core.userdetails.User(user.getUserEmail(),
                user.getUserPwd(),
                grantedAuthorities);
    }


}