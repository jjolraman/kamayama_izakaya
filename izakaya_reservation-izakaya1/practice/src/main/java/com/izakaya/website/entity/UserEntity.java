package com.izakaya.website.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.izakaya.website.model.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "users")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column
    private Long id;
//    @Column(unique = true)
//    private String username;
    @Column
    private String password;
    @Column
    private String name;
    @Column
    private String email;
    @Column
	private String tel;
    @Column
    private String verificationToken; // 이메일 인증을 위한 토큰 필드 추가
    @Column
    private boolean enabled; // 사용자의 활성화 상태를 나타내는 필드
    
//    @OneToMany(mappedBy = "user")
//    private List<PostEntity> posts = new ArrayList<>();
    
    @Builder(toBuilder = true)
    public UserEntity(Long id, String tel, String password
            , String name, String email, String verificationToken, boolean enabled) {
        this.id = id;
//        this.username = username;
        this.password = password;
        this.name = name;
        this.email = email;
        this.tel = tel;
        this.verificationToken = verificationToken; // 생성자에도 추가
        this.enabled = enabled; // 생성자에도 추가
    }
    
    public User toUser() {
        User build = User.builder()
                .id(id)
//                .username(username)
                .password(password)
                .name(name)
                .email(email)
                .tel(tel)
                .build();
        return build;
    }
    
    public void update(Long id, String username, String password
            , String name, String email) {
        this.id = id;
//        this.username = username;
        this.password = password;
        this.name = name;
        this.email = email;
        this.tel = tel;
    }
}
