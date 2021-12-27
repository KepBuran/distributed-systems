package com.tem.taskservice.repo.model;

import javax.persistence.*;

@Entity
@Table(name = "task_user")

public class TaskUser {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "task_id")
    private long taskId;

    @Column(name = "user_id")
    private long userId;

    public TaskUser() {
    }

    public TaskUser(long taskId, long userId) {
        this.taskId = taskId;
        this.userId = userId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getTaskId() {
        return taskId;
    }

    public void setTaskId(long taskId) {
        this.taskId = taskId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }
}
