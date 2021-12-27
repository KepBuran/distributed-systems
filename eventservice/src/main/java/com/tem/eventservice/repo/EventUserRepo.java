package com.tem.eventservice.repo;

import com.tem.eventservice.repo.model.EventUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface EventUserRepo extends JpaRepository<EventUser, Long> {
    List<EventUser> findByUserId(Long id);
}