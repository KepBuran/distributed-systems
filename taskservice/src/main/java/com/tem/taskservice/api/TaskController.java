package com.tem.taskservice.api;

import com.tem.taskservice.repo.model.Task;
import com.tem.taskservice.service.TaskService;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.sql.Timestamp;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    public final TaskService taskService;

    //############Communication with other services#############

    @GetMapping("/event/{eventId}")
    public ResponseEntity<List<Task>> getAllEventTasks(@PathVariable Long eventId) {
        final List<Task> tasks = taskService.fetchAllByEventId(eventId);

        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Task>> getAllUserTasks(@PathVariable Long userId) {
        final List<Task> tasks = taskService.fetchAllByUserId(userId);

        return ResponseEntity.ok(tasks);
    }

    //##########################################################

    @PostMapping("/{id}/user/{userId}")
    public ResponseEntity<Void> addUser(@PathVariable Long id, @PathVariable Long userId) {

        long newId = taskService.addUser(id, userId);
        final String taskUserUri = String.format("/tasks/users/%d", newId);

        return ResponseEntity.created(URI.create(taskUserUri)).build();
    }

    //###################CRUD######################
    @GetMapping
    public ResponseEntity<List<Task>> index() {
        final List<Task> tasks = taskService.fetchAllTasks();
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Task> show(@PathVariable long id) {
        final Task task = taskService.fetchTaskById(id);
        return ResponseEntity.ok(task);
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody com.tem.taskservice.api.dto.Task task) {
        long eventId = task.getEventId();
        String name = task.getName();
        String description = task.getDescription();
        Timestamp deadline =  task.getDeadline();
        Long statusId = task.getStatusId();


        final long taskId = taskService.createTask(eventId, name, description, deadline, statusId);
        final String taskUri = String.format("/tasks/%d", taskId);

        return ResponseEntity.created(URI.create(taskUri)).build();
    }

    @PostMapping("/status")
    public ResponseEntity<Void> create_status(@RequestBody com.tem.taskservice.api.dto.TaskStatus taskStatus) {
        String name = taskStatus.getName();
        String color = taskStatus.getColor();

        long id = taskService.createTaskStatus(name, color);
        final String taskUri = String.format("/events/status/%d", id);

        return ResponseEntity.created(URI.create(taskUri)).build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable long id, @RequestBody com.tem.taskservice.api.dto.Task task) {
        Long eventId = task.getEventId();
        String name = task.getName();
        String description = task.getDescription();
        Timestamp deadline =  task.getDeadline();
        Long statusId = task.getStatusId();
        try {
            taskService.updateTask(eventId, id, name, description, deadline, statusId);

            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        taskService.deleteTask(id);

        return ResponseEntity.noContent().build();
    }
    //#########################################
}




