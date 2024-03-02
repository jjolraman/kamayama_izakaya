package com.izakaya.website.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.izakaya.website.entity.PostEntity;
import com.izakaya.website.entity.UserEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class User {
    private Long id;
    private String username;
    private String password;
    private String name;
    private String email;
    private LocalDate birth;
    private String verificationToken; // 인증 토큰 추가

    public UserEntity toEntity() {
        UserEntity build = UserEntity.builder()
                .id(id)
                .username(username)
                .password(password)
                .name(name)
                .email(email)
                .verificationToken(verificationToken) // 인증 토큰 추가
                .build();
        return build;
    }

    @Builder
    public User(Long id, String username, String password
            , String name, String email, LocalDate birth, String verificationToken) { // 생성자에 인증 토큰 추가
        this.id = id;
        this.username = username;
        this.password = password;
        this.name = name;
        this.email = email;
        this.verificationToken = verificationToken; // 생성자에 인증 토큰 추가
    }
}
