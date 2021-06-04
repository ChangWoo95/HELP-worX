package com.example.helpworx.auth.config;

import com.example.helpworx.auth.jwt.JwtAccessDeniedHandler;
import com.example.helpworx.auth.jwt.JwtAuthenticationEntryPoint;
import com.example.helpworx.auth.jwt.JwtSecurityConfig;
import com.example.helpworx.auth.jwt.TokenProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true) //메소드 단위로 @PreAuthorize 검증 어노테이션을 사용하기 위해서
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final TokenProvider tokenProvider;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

    public SecurityConfig(
            TokenProvider tokenProvider,
            JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint,
            JwtAccessDeniedHandler jwtAccessDeniedHandler
    ) {
        this.tokenProvider = tokenProvider;
        this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
        this.jwtAccessDeniedHandler = jwtAccessDeniedHandler;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void configure(WebSecurity web) {
                web.ignoring().antMatchers(
             "/v2/api-docs",
                        "/configuration/ui",
                        "/swagger-resources/**",
                        "/configuration/security",
                        "/swagger-ui.html",
                        "/webjars/**")

                .and().ignoring()
                .antMatchers(
                        "/h2-console/**"
                        , "/favicon.ico"
                );


    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                //Token 방식을 사용하기 위해
                .csrf().disable()
                //예외처리 (401 403)
                .exceptionHandling()
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .accessDeniedHandler(jwtAccessDeniedHandler)
                //데이터 확인
                .and()
                .headers()
                .frameOptions()
                .sameOrigin()
                //세션을 사용하지 않기 위해서 세션 설정
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                //토큰없이 로그인 가능하도록
                .and()
                    .formLogin()
                    .loginPage("/login")
                    .usernameParameter("userEmail")
                    .permitAll()

                .and()
                    .logout()
                    .permitAll()

                //권한 설정
                .and()
                    .authorizeRequests()
                    .antMatchers("/api/hello").permitAll()
                    .antMatchers("/api/authenticate").permitAll()
                    .antMatchers("/api/signup").permitAll()
                    .antMatchers("/api/login").permitAll()
                    .antMatchers("/api/cust/srs/**").permitAll()
                    .antMatchers("api/**").permitAll()
                    .antMatchers("/cust/srs/main/**").permitAll()
                    .antMatchers("/admin/**").permitAll()
                    .antMatchers("/oper/**").permitAll()
                    .antMatchers("/ws/info", "/ws/**", "/topic/**").permitAll()

                .anyRequest().authenticated()
                //필터 등록
                .and()
                .apply(new JwtSecurityConfig(tokenProvider));

    }

}
