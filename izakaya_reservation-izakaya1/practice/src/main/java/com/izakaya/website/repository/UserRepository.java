package com.izakaya.website.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.izakaya.website.entity.UserEntity;


public interface UserRepository extends JpaRepository<UserEntity, Long> {
//    Optional<UserEntity> findByUsername(String username);
	Optional<UserEntity> findByEmail(String email);
	List<UserEntity> findByName(String name);
    Optional<UserEntity> findByVerificationToken(String verificationToken);
}
