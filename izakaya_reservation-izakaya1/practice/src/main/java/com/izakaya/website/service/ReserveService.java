package com.izakaya.website.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.izakaya.website.entity.PostEntity;
import com.izakaya.website.entity.ReserveEntity;
import com.izakaya.website.entity.UserEntity;
import com.izakaya.website.model.Member;
import com.izakaya.website.model.Post;
import com.izakaya.website.model.Reserve;
import com.izakaya.website.repository.ReserveRepository;
import com.izakaya.website.repository.UserRepository;

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
			UserEntity userEntity = null;
			
			if(reserve.getUser() != null) {
				Optional<UserEntity> byId = userRepository.findById(reserve.getUser().getId());
		        if (byId.isPresent()) {
		        	userEntity = byId.get(); // 사용자 정보가 있으면 userEntity를 업데이트합니다.
		        }
			}    

	    // ReserveEntity를 생성할 때, userEntity가 null일 수 있음을 고려합니다.
	    ReserveEntity reserveEntity = ReserveEntity.builder()
	            .id(reserve.getId())
	            .user(userEntity)
	            .person(reserve.getPerson())
	            .menu(reserve.getMenu())
	            .date(reserve.getDate())
	            .time(reserve.getTime())
	            .member(userEntity != null ? Member.Member : Member.NonMember)
	            .email(reserve.getEmail())
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
	
	/*비회원의 예약조회*/
	public List<Reserve> findReservesByEmail(String email) {
	    List<ReserveEntity> reserveEntities = reserveRepository.findByEmail(email);
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
	
	/*비회원의 예약취소(삭제)*/
	@Transactional
	public void deleteReserves(String email) {
		List<ReserveEntity> byEmail = reserveRepository.findByEmail(email);
		reserveRepository.deleteAll(byEmail);
	}
}
