package com.example.practice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.practice.entity.ReserveEntity;

public interface ReserveRepository extends JpaRepository<ReserveEntity, Long>{

}
