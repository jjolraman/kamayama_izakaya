package com.example.practice.model;

import java.time.LocalDate;

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
	private Member member;
	
	public ReserveEntity toEntity() {
		ReserveEntity build = ReserveEntity.builder()
				.id(id)
				.person(person)
//				.menu(menu)
				.date(date)
				.member(member)
				.build();
		return build;
	}
	
	@Builder
	public Reserve(Long id, int person,
			LocalDate date, Member member) {
		this.id = id;
		this.person = person;
//		this.menu = menu;
		this.date = date;
		this.member = member;
	}
}


