package com.example.practice.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.example.practice.entity.PostEntity;
import com.example.practice.entity.UserEntity;

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
	
	public UserEntity toEntity() {
		UserEntity build = UserEntity.builder()
				.id(id)
				.username(username)
				.password(password)
				.name(name)
				.email(email)
				.birth(birth)
				.build();
		return build;
	}
	
	@Builder
	public User(Long id, String username, String password
			, String name, String email, LocalDate birth) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.name = name;
		this.email = email;
		this.birth = birth;
	}
}
