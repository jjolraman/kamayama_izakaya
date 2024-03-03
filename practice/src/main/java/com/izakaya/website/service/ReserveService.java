package com.izakaya.website.service;

import org.springframework.stereotype.Service;

import com.izakaya.website.entity.ReserveEntity;
import com.izakaya.website.entity.UserEntity;
import com.izakaya.website.model.Member;
import com.izakaya.website.model.Reserve;
import com.izakaya.website.model.User;
import com.izakaya.website.repository.ReserveRepository;

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
