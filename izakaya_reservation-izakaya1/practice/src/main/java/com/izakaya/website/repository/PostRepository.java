package com.izakaya.website.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.izakaya.website.entity.PostEntity;

public interface PostRepository extends JpaRepository<PostEntity, Long>{
	
}
