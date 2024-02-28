package com.izakaya.website.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.izakaya.website.entity.PostEntity;
import com.izakaya.website.entity.ReserveEntity;
import com.izakaya.website.model.Reserve;
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
	
	public List<Reserve> findReserve() {
		List<ReserveEntity> reserveEntityList = reserveRepository.findAll();
		List<Reserve> reserveList = new ArrayList<>();
		for(ReserveEntity reserveEntity : reserveEntityList) {
			reserveList.add(reserveEntity.toReserve());
		}
		return reserveList;
	}

}