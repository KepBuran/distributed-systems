package com.tem.taskservice.repo;


import com.tem.taskservice.repo.model.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import java.util.List;


public interface TaskStatusRepo extends JpaRepository<TaskStatus, Long> {

}
