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
//    private String username;
    private String password;
    private String name;
    private String tel;
    private String email;
    private Member member;
    private String verificationToken; // 인증 토큰 추가

    public UserEntity toEntity() {
        return UserEntity.builder()
                .id(id)
//                .username(username)
                .password(password)
                .name(name)
                .email(email)
                .tel(tel)
                .verificationToken(verificationToken) // 인증 토큰 추가
                .build();
    }

    @Builder
    public User(Long id, String password, String name, String email, String tel, Member member, String verificationToken) {
        this.id = id;
        this.email = email;
//        this.username = username;
        this.password = password;
        this.name = name;
        this.tel = tel;
        this.member = member;
        this.verificationToken = verificationToken; // 생성자에 인증 토큰 추가
    }
}
