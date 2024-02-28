package com.example.practice.entity;

import com.example.practice.model.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
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
	private String email;
	@Column
	private String password;
	@Column
	private String name;
	@Column
	private String tel;
	
/*	@OneToMany(mappedBy = "user")
	private List<PostEntity> posts = new ArrayList<>();
*/	
	@Builder(toBuilder = true)
	public UserEntity(Long id, String email, String password
			, String name, String tel) {
		this.id = id;
		this.email = email;
		this.password = password;
		this.name = name;
		this.tel = tel;
	}
	
	public User toUser() {
		User build = User.builder()
				.id(id)
				.email(email)
				.password(password)
				.name(name)
				.tel(tel)
				.build();
		return build;
	}
	
	public void update(Long id, String email, String password
			, String name, String tel) {
		this.id = id;
		this.email = email;
		this.password = password;
		this.name = name;
		this.tel = tel;
	}

}
