package com.example.practice.entity;

import com.example.practice.model.Member;
import com.example.practice.model.Reserve;
import com.example.practice.model.User;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "users_id")
	private UserEntity user;
	@Column
	private int person;
	@Column
	private String menu;
	@Column(name="reserve_date")
	private String date;
	@Column
	private String time;
	@Column
	@Enumerated(EnumType.STRING)
	private Member member;
	@Column
	private String email;
	
	@Builder
	public ReserveEntity(Long id, UserEntity user, int person,
			String menu, String date, String time, Member member
			, String email) {
		this.id = id;
		this.user = user;
		this.person = person;
		this.menu = menu;
		this.date = date;
		this.time = time;
		this.member = member;
		this.email = email;
	}
	
	public Reserve toReserve() {
		User convertedUser = null;
		if (user != null) {
	        convertedUser = user.toUser(); // user가 null이 아닐 때만 변환 실행
	    }
		Reserve build = Reserve.builder()
				.id(id)
				.user(convertedUser)
				.person(person)
				.menu(menu)
				.date(date)
				.time(time)
				.member(member)
				.email(email)
				.build();
		return build;
	}
}
