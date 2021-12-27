package com.tem.taskservice.repo;

import com.tem.taskservice.repo.model.Task;
import com.tem.taskservice.repo.model.TaskUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface TaskUserRepo extends JpaRepository<TaskUser, Long> {
    List<TaskUser> findByUserId(Long id);

}