package com.example.practice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.practice.entity.ReserveEntity;
import com.example.practice.entity.UserEntity;
import com.example.practice.model.Reserve;
import java.util.List;
import java.time.LocalDate;


public interface ReserveRepository extends JpaRepository<ReserveEntity, Long>{
	List<ReserveEntity> findByDate(String date);
	List<ReserveEntity> findByUserId(Long userId);
}
