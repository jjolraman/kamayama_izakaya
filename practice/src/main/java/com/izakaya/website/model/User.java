package com.izakaya.website.model;

import com.izakaya.website.entity.UserEntity;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class User {
    private Long id;
    private String password;
    private String name;
    private String tel;
    private String email;
    private String verificationToken; 

    public UserEntity toEntity() {
        return UserEntity.builder()
                .id(id)
                .password(password)
                .name(name)
                .email(email)
                .tel(tel)
                .verificationToken(verificationToken) 
                .build();
    }

    @Builder
    public User(Long id, String password, String name, String email, String tel, String verificationToken) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.name = name;
        this.tel = tel;
        this.verificationToken = verificationToken; 
    }
}
