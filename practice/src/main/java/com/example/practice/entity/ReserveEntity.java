package com.example.practice.entity;

import java.time.LocalDate;
import java.time.LocalTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import com.example.practice.model.Member;
import com.example.practice.model.Menu;
import com.example.practice.model.Reserve;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatTypes;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Table(name="reserve")
@NoArgsConstructor
@Entity
//@EntityListeners(AuditingEntityListener.class)
public class ReserveEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name="reserve_id")
	private Long id;
//	private User user;
	@Column
	private int person;
//	@Column
//	private Menu menu;
	@Column(name="reserve_date")
	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDate date;
	@Column
	private String time;
	@Column
	@Enumerated(EnumType.STRING)
	private Member member;
	
	@Builder
	public ReserveEntity(Long id, int person, 
			LocalDate date, String time, Member member) {
		this.id = id;
		this.person = person;
//		this.menu = menu;
		this.date = date;
		this.time = time;
		this.member = member;
	}
	
	public Reserve toReserve() {
		Reserve build = Reserve.builder()
				.id(id)
				.person(person)
//				.menu(menu)
				.date(date)
				.time(time)
				.member(member)
				.build();
		return build;
	}
}
