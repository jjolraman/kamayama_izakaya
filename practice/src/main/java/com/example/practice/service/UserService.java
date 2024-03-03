package com.example.practice.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.practice.entity.UserEntity;
import com.example.practice.model.Member;
import com.example.practice.model.User;
import com.example.practice.repository.UserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserService {
	
	private final UserRepository userrepository;
	
	/*회원가입*/
	@Transactional
	public void create(User user) {
		
	Member memberStatus = Member.Member; // 기본값을 MEMBER로 설정

    if (user.getPassword() == null || user.getPassword().isEmpty()) {
	        memberStatus = Member.NonMember; // password가 없으면 NONMEMBER로 설정
    }

		UserEntity userEntity = UserEntity.builder()
				.id(user.getId())
				.email(user.getEmail())
				.password(user.getPassword())
				.name(user.getName())
				.tel(user.getTel())
				.member(memberStatus)
				.build();
		userrepository.save(userEntity);
	}
	
	/*로그인*/
	public User login(User user) {
		Optional<UserEntity> byuserEmail = userrepository.findByEmail(user.getEmail());
		
		if(byuserEmail.isPresent()) {
			UserEntity userEntity = byuserEmail.get();
			if(userEntity.getPassword().equals(user.getPassword())) {
				return userEntity.toUser();
			} else {
				return null;
			}
		} else {
			return null;
		}
	}
	
	/*회원목록*/
	public List<User> findAll() {
		List<UserEntity> userEntityList = userrepository.findAll();
		List<User> userList = new ArrayList<>();
		for(UserEntity userEntity : userEntityList) {
			userList.add(userEntity.toUser());
		}
		return userList;
	}
	
	/*회원조회*/
	public User findById(Long id) {
		Optional<UserEntity> userEntity = userrepository.findById(id);
		
		if(userEntity.isPresent()) {
			return userEntity.get().toUser();
		}
		return null;
	}
	
	/*이메일로 회원조회(회원가입 아이디 중복방지)*/
	public User findByEmail(String email){
		Optional<UserEntity> userEntity = userrepository.findByEmail(email);
		
		if(userEntity.isPresent()) {
			return userEntity.get().toUser();
		}
		return null;
	}
	
	/*회원정보수정*/
	@Transactional
	public void update(User user) {
		Optional<UserEntity> byId = userrepository.findById(user.getId());
		
		if(byId.isPresent()) {
			UserEntity userEntity = byId.get();
			userEntity.update(user.getId(),
							  user.getEmail(),
							  user.getPassword(), 
							  user.getName(), 
							  user.getTel());
		}
	} 

	/*회원탈퇴*/
	@Transactional
	public void deleteById(Long id) {
		userrepository.deleteById(id);
	}

}
