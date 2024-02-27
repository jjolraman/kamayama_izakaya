package com.example.practice.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.practice.entity.UserEntity;
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
	private final PasswordEncoder passwordEncoder;
	
	
	@Transactional
	public void create(User user) throws Exception {
		if(UserRepository.findByUsername(user.getUsername()).isPresent()) {
			throw new Exception("사용중인 이름입니다");
		}
		String encodedPassword = passwordEncoder.encode(user.getPassword());
		UserEntity userEntity = UserEntity.builder()
				.id(user.getId())
				.username(user.getUsername())
				.password(encodedPassword)
				.name(user.getName())
				.email(user.getEmail())
				.phoneNumber(user.getPhoneNumber())
				.build();
		userrepository.save(userEntity);
	}

	public User login(User user) {
		Optional<UserEntity> byuserName = userrepository.findByUsername(user.getUsername());
		
		if(byuserName.isPresent()) {
			UserEntity userEntity = byuserName.get();
			if(userEntity.getPassword().equals(user.getPassword())) {
				return userEntity.toUser();
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	public List<User> findAll() {
		List<UserEntity> userEntityList = userrepository.findAll();
		List<User> userList = new ArrayList<>();
		for(UserEntity userEntity : userEntityList) {
			userList.add(userEntity.toUser());
		}
		return userList;
	}

	public User findById(Long id) {
		Optional<UserEntity> userEntity = userrepository.findById(id);
		
		if(userEntity.isPresent()) {
			return userEntity.get().toUser();
		}
		return null;
	}
	
	@Transactional
	public void update(User user) {
		Optional<UserEntity> byId = userrepository.findById(user.getId());
		
		if(byId.isPresent()) {
			UserEntity userEntity = byId.get();
			userEntity.update(user.getId(),
							  user.getUsername(),
							  user.getPassword(), 
							  user.getName(), 
							  user.getEmail(), 
								user.getPhoneNumber());
							
		}
	} 

	@Transactional
	public void deleteById(Long id) {
		userrepository.deleteById(id);
	}

}
