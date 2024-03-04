package com.example.practice.model;

import com.example.practice.entity.ReserveEntity;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class Reserve {
	private Long id;
	private User user;
	private int person;
	private String menu;
	private String date;
	private String time;
	private Member member;
	private String email;
	
	public ReserveEntity toEntity() {
		ReserveEntity build = ReserveEntity.builder()
				.id(id)
				.user(user.toEntity())
				.person(person)
				.menu(menu)
				.date(date)
				.time(time)
				.member(member)
				.email(email)
				.build();
		return build;
	}
	
	@Builder
	public Reserve(Long id, User user, int person,
			String menu, String date, String time, Member member,
			String email) {
		this.id = id;
		this.user = user;
		this.person = person;
		this.menu = menu;
		this.date = date;
		this.time = time;
		this.member = member;
		this.email = email;
	}
}


