package com.example.practice.model;

import lombok.Getter;

@Getter
public enum Member {
	Member("회원"),
	NonMember("비회원");
	
	private final String description;
	
	Member(String desciption) {
		this.description = desciption;
	}
}
