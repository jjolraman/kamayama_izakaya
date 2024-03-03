package com.example.practice.model;

import com.example.practice.entity.UserEntity;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class User {
	private Long id;
	private String email;
	private String password;
	private String name;
	private String tel;
	private Member member;
	
	public UserEntity toEntity() {
		UserEntity build = UserEntity.builder()
				.id(id)
				.email(email)
				.password(password)
				.name(name)
				.tel(tel)
				.build();
		return build;
	}
	
	@Builder
	public User(Long id, String email, String password
			, String name, String tel, Member member) {
		this.id = id;
		this.email = email;
		this.password = password;
		this.name = name;
		this.tel = tel;
		this.member = member;
	}
}
