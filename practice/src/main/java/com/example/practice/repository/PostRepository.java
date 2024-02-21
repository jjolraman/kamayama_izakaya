package com.example.practice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.practice.entity.PostEntity;

public interface PostRepository extends JpaRepository<PostEntity, Long>{
	
}
