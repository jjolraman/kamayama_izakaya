package com.izakaya.website.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.izakaya.website.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long>{
	Optional<UserEntity> findByEmail(String email);
}
