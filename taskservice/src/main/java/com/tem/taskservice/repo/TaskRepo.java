package com.tem.taskservice.repo;

import com.tem.taskservice.repo.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepo extends JpaRepository<Task, Long> {
    List<Task> findByEventId(Long id);

}
