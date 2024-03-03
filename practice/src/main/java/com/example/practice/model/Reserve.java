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
//	private Menu menu;
	private String date;
	private String time;
	private Member member;
	
	public ReserveEntity toEntity() {
		ReserveEntity build = ReserveEntity.builder()
				.id(id)
				.user(user.toEntity())
				.person(person)
//				.menu(menu)
				.date(date)
				.time(time)
				.member(member)
				.build();
		return build;
	}
	
	@Builder
	public Reserve(Long id, int person, User user,
			String date, String time, Member member) {
		this.id = id;
		this.user = user;
		this.person = person;
//		this.menu = menu;
		this.date = date;
		this.time = time;
		this.member = member;
	}
}


