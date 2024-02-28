package com.example.practice.entity;

import java.time.LocalDate;

import org.antlr.v4.runtime.misc.NotNull;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.example.practice.model.Post;
import com.example.practice.model.User;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name="posts")
@NoArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
public class PostEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name="post_id")
	private Long id;
	@Column
	private String title;
	@Column
	private String content;
	@Column(name = "create_time")
	@CreatedDate
	private LocalDate createTime;
	
	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "users_id")
	private UserEntity user;
	
	@Builder(toBuilder = true)
	public PostEntity(Long id, String title, String content, LocalDate createTime,
			UserEntity user) {
		this.id = id;
		this.title = title;
		this.content = content;
		this.createTime = createTime;
		this.user = user;
	}
	
	public Post toPost() {
		Post build = Post.builder()
				.id(id)
				.title(title)
				.content(content)
				.createTime(createTime)
				.user(user.toUser())
				.build();
		return build;
	}
	
	/*게시물 수정(제목하고 내용만 바뀌니까)*/
	public void update(String title, String content) {
		this.title = title;
		this.content = content;
	}
	
}
