package com.tem.taskservice.service;

import com.tem.taskservice.repo.TaskStatusRepo;
import com.tem.taskservice.repo.TaskUserRepo;
import com.tem.taskservice.repo.model.Task;
import com.tem.taskservice.repo.TaskRepo;
import com.tem.taskservice.repo.model.TaskStatus;
import com.tem.taskservice.repo.model.TaskUser;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.net.URI;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service

public class TaskService {

    @Autowired
    private final TaskRepo taskRepo;
    private final TaskStatusRepo taskStatusRepo;
    private final TaskUserRepo taskUserRepo;

    //############Communication with other services#############

    public List<Task>fetchAllByEventId(Long eventId) {
        return taskRepo.findByEventId(eventId);
    }

    public List<Task> fetchAllByUserId(Long userId) {
        List<TaskUser> taskUserList = taskUserRepo.findByUserId(userId);

        List<Task> tasks = new ArrayList<>();

        for (int i = 0; i < taskUserList.size(); i++) {
            final Task task = taskRepo.findById(taskUserList.get(i).getTaskId()).orElseThrow(() -> new IllegalArgumentException("Invalid task id"));
            tasks.add(taskRepo.findById(taskUserList.get(i).getTaskId()).get());
        }

        return tasks;
    }

    //##########################################################

    public long addUser(Long id, Long userId) {
        final TaskUser taskUser = new TaskUser(id, userId);
        final TaskUser savedTaskUser = taskUserRepo.save(taskUser);
        return savedTaskUser.getId();
    }

    //#########################CRUD#######################
    public List<Task> fetchAllTasks() {
        return taskRepo.findAll();
    }

    public Task fetchTaskById(long id) throws IllegalArgumentException {
        final Optional<Task> maybeTask = taskRepo.findById(id);

        if (maybeTask.isPresent())
            return maybeTask.get();
        else
            throw new IllegalArgumentException("Invalid task ID");
    }

    public long createTask(long eventId, String name, String description, Timestamp deadline, long taskStatusId) {
        final Optional<TaskStatus> maybeTaskStatus = taskStatusRepo.findById(taskStatusId);
        if (maybeTaskStatus.isEmpty())
            throw new IllegalArgumentException("Invalid task status ID");

        final Task task = new Task(eventId, name, description, deadline, maybeTaskStatus.get());
        final Task savedTask = taskRepo.save(task);
        return savedTask.getId();
    }

    public long createTaskStatus(String name, String color) {
        final TaskStatus taskStatus = new TaskStatus(name, color);
        final TaskStatus savedTaskStatus = taskStatusRepo.save(taskStatus);
        long id = savedTaskStatus.getId();
        return savedTaskStatus.getId();
    }

    public void updateTask(long id, Long eventId, String name, String description, Timestamp deadline, Long taskStatusId) throws IllegalArgumentException {
        TaskStatus taskStatus = null;
        if (!(taskStatusId == null)) {
            final Optional<TaskStatus> maybeStatus = taskStatusRepo.findById(taskStatusId);
            if (!maybeStatus.isEmpty()) {
                taskStatus = maybeStatus.get();
            }
        }

        final Optional<Task> maybeTask = taskRepo.findById(id);
        final Optional<TaskStatus> maybeTaskStatus = taskStatusRepo.findById(taskStatusId);

        if (maybeTaskStatus.isEmpty())
            throw new IllegalArgumentException("Invalid task status ID");

        if (maybeTask.isEmpty())
            throw new IllegalArgumentException("Invalid task ID");

        final Task task = maybeTask.get();
        if (eventId == 0) task.setEventId(eventId);
        if (name != null && !name.isBlank()) task.setName(name);
        if (description != null && !description.isBlank()) task.setDescription(description);
        if (deadline != null) task.setDeadline(deadline);
        if (!(taskStatus == null)) task.setTaskStatus(taskStatus);;
        taskRepo.save(task);
    }

    public void deleteTask(long id) {
        taskRepo.deleteById(id);
    }
    //####################################################




}


