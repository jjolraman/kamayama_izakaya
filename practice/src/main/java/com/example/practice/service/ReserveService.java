package com.example.practice.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.practice.entity.PostEntity;
import com.example.practice.entity.ReserveEntity;
import com.example.practice.model.Reserve;
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
	
	public List<Reserve> findReserve() {
		List<ReserveEntity> reserveEntityList = reserveRepository.findAll();
		List<Reserve> reserveList = new ArrayList<>();
		for(ReserveEntity reserveEntity : reserveEntityList) {
			reserveList.add(reserveEntity.toReserve());
		}
		return reserveList;
	}

}
