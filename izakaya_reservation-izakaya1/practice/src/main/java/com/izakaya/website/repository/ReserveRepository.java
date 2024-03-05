package com.izakaya.website.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.izakaya.website.entity.ReserveEntity;
import com.izakaya.website.entity.UserEntity;
import com.izakaya.website.model.Reserve;
import java.util.List;
import java.time.LocalDate;


public interface ReserveRepository extends JpaRepository<ReserveEntity, Long>{
	List<ReserveEntity> findByDate(String date);
	List<ReserveEntity> findByUserId(Long userId);
	List<ReserveEntity> findByEmail(String email);
}
