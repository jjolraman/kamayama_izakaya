package com.izakaya.website.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.izakaya.website.entity.ReserveEntity;

public interface ReserveRepository extends JpaRepository<ReserveEntity, Long>{

}
