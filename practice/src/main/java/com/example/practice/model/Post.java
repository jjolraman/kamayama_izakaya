package com.example.practice.model;

import java.time.LocalDate;

import com.example.practice.entity.PostEntity;
import com.example.practice.entity.UserEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class Post {
	private Long id;
	private String title;
	private String content;
	private User user;
	private LocalDate createTime;
	
	public PostEntity toEntity() {
		PostEntity build = PostEntity.builder()
				.id(id)
				.title(title)
				.content(content)
				.createTime(createTime)
				.user(user.toEntity())
				.build();
		return build;
	}
	
	@Builder
	public Post(Long id, String title, String content,
			User user, LocalDate createTime) {
		this.id = id;
		this.title = title;
		this.content = content;
		this.createTime = createTime;
		this.user = user;
	}
	
}
