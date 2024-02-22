package com.example.practice.model;

import java.time.LocalDate;
import java.time.LocalTime;

import com.example.practice.entity.ReserveEntity;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class Reserve {
	private Long id;
//	private User user;
	private int person;
//	private Menu menu;
	private LocalDate date;
	private String time;
	private Member member;
	
	public ReserveEntity toEntity() {
		ReserveEntity build = ReserveEntity.builder()
				.id(id)
				.person(person)
//				.menu(menu)
				.date(date)
				.time(time)
				.member(member)
				.build();
		return build;
	}
	
	@Builder
	public Reserve(Long id, int person,
			LocalDate date, String time, Member member) {
		this.id = id;
		this.person = person;
//		this.menu = menu;
		this.date = date;
		this.time = time;
		this.member = member;
	}
}


