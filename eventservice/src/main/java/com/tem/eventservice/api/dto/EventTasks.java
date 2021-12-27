package com.tem.eventservice.api.dto;

import com.tem.eventservice.repo.model.EventStatus;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;

@Data
@NoArgsConstructor

public class EventTasks {
    private String name;
    private String location;
    private Timestamp date;
    private EventStatus eventStatus;

    public EventTasks(String name, String location, Timestamp date, EventStatus eventStatus, List<Task> tasks) {
        this.name = name;
        this.location = location;
        this.date = date;
        this.eventStatus = eventStatus;
        this.tasks = tasks;
    }

    private List<Task> tasks;

    public EventTasks(List<Task> tasks) {
        this.tasks = tasks;
    }
}
