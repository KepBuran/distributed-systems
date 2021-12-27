package com.tem.eventservice.repo;

import com.tem.eventservice.repo.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepo extends JpaRepository<Event, Long> {
}

