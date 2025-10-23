package com.example.toriaezu.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.toriaezu.model.entity.EventEntity;

public interface EventRepository extends JpaRepository<EventEntity, Long>{
    
}
