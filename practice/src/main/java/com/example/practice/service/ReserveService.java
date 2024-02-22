package com.example.practice.service;

import org.springframework.stereotype.Service;

import com.example.practice.entity.ReserveEntity;
import com.example.practice.entity.UserEntity;
import com.example.practice.model.Member;
import com.example.practice.model.Reserve;
import com.example.practice.model.User;
import com.example.practice.repository.ReserveRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ReserveService {
	private final ReserveRepository reserveRepository;
	
	@Transactional
	public void create(Reserve reserve) {
		
		ReserveEntity reserveEntity = ReserveEntity.builder()
				.id(reserve.getId())
				.person(reserve.getPerson())
				.date(reserve.getDate())
				.time(reserve.getTime())
				.member(reserve.getMember())
				.build();				
		
		reserveRepository.save(reserveEntity);
	}
}
