package com.example.practice.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.practice.entity.PostEntity;
import com.example.practice.entity.ReserveEntity;
import com.example.practice.entity.UserEntity;
import com.example.practice.model.Member;
import com.example.practice.model.Post;
import com.example.practice.model.Reserve;
import com.example.practice.repository.ReserveRepository;
import com.example.practice.repository.UserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ReserveService {
	private final ReserveRepository reserveRepository;
	private final UserRepository userRepository;
	
	/*예약하기*/
	@Transactional
	public void create(Reserve reserve) {
		UserEntity userEntity = null; // 사용자 엔티티 초기화

	    // reserve.getUser().getId() 호출 전에 reserve.getUser()가 null인지 확인합니다.
	    if (reserve.getUser() != null) {
	        Optional<UserEntity> byId = userRepository.findById(reserve.getUser().getId());
	        if (byId.isPresent()) {
	            userEntity = byId.get(); // 사용자 정보가 있으면 userEntity를 업데이트합니다.
	        }
	    }

	    // ReserveEntity를 생성할 때, userEntity가 null일 수 있음을 고려합니다.
	    ReserveEntity reserveEntity = ReserveEntity.builder()
	            .id(reserve.getId())
	            .user(userEntity) // userEntity가 null일 경우, 사용자 정보 없이 예약 생성
	            .person(reserve.getPerson())
	            .date(reserve.getDate())
	            .time(reserve.getTime())
	            .member(userEntity != null ? userEntity.getMember() : Member.NonMember) // userEntity가 null이면 member도 null로 설정
	            .build();

	    reserveRepository.save(reserveEntity);
	}
	    
/*		Optional<UserEntity> byId = userRepository.findById(reserve.getUser().getId());
		
		if(byId.isPresent()) {
			UserEntity userEntity = byId.get();
			
			ReserveEntity reserveEntity = ReserveEntity.builder()
					.id(reserve.getId())
					.user(userEntity)
					.person(reserve.getPerson())
					.date(reserve.getDate())
					.time(reserve.getTime())
					.member(userEntity.getMember())
					.build();				
			
			reserveRepository.save(reserveEntity);
		}*/

	
	/*날짜에 해당하는 모든예약조회*/
	public List<Reserve> findAllReserve(String date) { 
		List<ReserveEntity> byDate = reserveRepository.findByDate(date);
		List<Reserve> reserveList = new ArrayList<>();
		for(ReserveEntity reserveEntity : byDate) {
			reserveList.add(reserveEntity.toReserve());
		}
		return reserveList;
	}

	/*회원의 모든예약조회*/
	public List<Reserve> findReservesByUserId(Long userId) {
	    List<ReserveEntity> reserveEntities = reserveRepository.findByUserId(userId);
	    List<Reserve> reserves = new ArrayList<>();
	    for (ReserveEntity reserveEntity : reserveEntities) {
	        reserves.add(reserveEntity.toReserve());
	    }
	    return reserves;
	}
	
	/*회원의 예약취소(삭제)*/
	@Transactional
	public void deleteReserve(Long reserveId) {
		Optional<ReserveEntity> reserveById = reserveRepository.findById(reserveId);
		
		if(reserveById.isPresent()) {
			ReserveEntity reserveEntity = reserveById.get();
			reserveRepository.delete(reserveEntity);
		}
	}
}
