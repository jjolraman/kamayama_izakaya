package com.example.practice.security;

import java.util.ArrayList;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.practice.entity.UserEntity;
import com.example.practice.service.UserService;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserService userService;

    public SecurityConfig(UserService userService) {
        this.userService = userService;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        // 간단한 패스워드 인코더
        return new PasswordEncoder() {
            @Override
            public String encode(CharSequence rawPassword) {
                // TODO: 실제 사용 시 보안 강도 높은 인코딩 사용 권장
                return rawPassword.toString();
            }

            @Override
            public boolean matches(CharSequence rawPassword, String encodedPassword) {
                return rawPassword.toString().equals(encodedPassword);
            }
        };
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(username -> {
            UserEntity userEntity = userService.findByUsername(username);
            if (userEntity != null) {
                return new org.springframework.security.core.userdetails.User(
                        userEntity.getUsername(),
                        userEntity.getPassword(),
                        new ArrayList<>() // 권한 리스트
                );
            } else {
                throw new UsernameNotFoundException("User not found");
            }
        });
    }

    // HttpSecurity 설정은 애플리케이션의 요구사항에 맞춰 구현하세요.
}
