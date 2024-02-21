package com.example.practice.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.example.practice.model.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "users")
public class UserEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column
	private Long id;
	@Column(unique = true)
	private String username;
	@Column
	private String password;
	@Column
	private String name;
	@Column
	private String email;
	private LocalDate birth;
	
	@OneToMany(mappedBy = "user")
	private List<PostEntity> posts = new ArrayList<>();
	
	@Builder(toBuilder = true)
	public UserEntity(Long id, String username, String password
			, String name, String email, LocalDate birth) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.name = name;
		this.email = email;
		this.birth = birth;
	}
	
	public User toUser() {
		User build = User.builder()
				.id(id)
				.username(username)
				.password(password)
				.name(name)
				.email(email)
				.birth(birth)
				.build();
		return build;
	}
	
	public void update(Long id, String username, String password
			, String name, String email, LocalDate birth) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.name = name;
		this.email = email;
		this.birth = birth;
	}

}
