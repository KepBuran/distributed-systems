package com.tem.eventservice.repo;

import com.tem.eventservice.repo.model.EventStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventStatusRepo extends JpaRepository<EventStatus, Long> {

}