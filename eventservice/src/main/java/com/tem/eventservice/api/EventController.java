package com.tem.eventservice.api;

import com.tem.eventservice.api.dto.EventTasks;
import com.tem.eventservice.api.dto.Task;
import com.tem.eventservice.repo.model.Event;
import com.tem.eventservice.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.sql.Timestamp;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/events")
public class EventController {

    @Autowired
    public final EventService eventService;

    //############Communication with other services#############
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Event>> getAllUserEvents(@PathVariable Long userId) {
        final List<Event> events = eventService.fetchAllByUserId(userId);

        return ResponseEntity.ok(events);
    }

    @GetMapping("/{id}/full_info")
    public ResponseEntity<EventTasks> showInfo(@PathVariable Long id) {
        try {
            final EventTasks eventTasks = eventService.fetchEventAndTasks(id);
            return ResponseEntity.ok(eventTasks);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}/tasks")
    public ResponseEntity<List<Task>> showTasks(@PathVariable Long id) {
        try {
            final List<Task> tasks = eventService.fetchEventTasks(id);
            return ResponseEntity.ok(tasks);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
    //#####################################################

    @PostMapping("/{id}/users/{userId}")
    public ResponseEntity<Void> addUser(@PathVariable Long id, @PathVariable Long userId) {

        long newId = eventService.addUser(id, userId);
        final String eventUserUri = String.format("/events/users/%d", newId);

        return ResponseEntity.created(URI.create(eventUserUri)).build();
    }

    //############Crud#############
    @GetMapping
    public ResponseEntity<List<Event>> index() {
        final List<Event> events = eventService.fetchAllEvents();
        return ResponseEntity.ok(events);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Event> show(@PathVariable long id) {
        try {
            final Event event = eventService.fetchEventById(id);
            return ResponseEntity.ok(event);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }

    }

    @PostMapping("/status")
    public ResponseEntity<Void> create_status(@RequestBody com.tem.eventservice.api.dto.EventStatus eventStatus) {
        String name = eventStatus.getName();
        String color = eventStatus.getColor();

        long id = eventService.createEventStatus(name, color);
        final String eventUri = String.format("/events/status/%d", id);

        return ResponseEntity.created(URI.create(eventUri)).build();
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody com.tem.eventservice.api.dto.Event event) {
        String name = event.getName();
        String location = event.getLocation();
        Timestamp date =  event.getDate();
        Long statusId = event.getStatusId();


        long id = eventService.createEvent(name, location, date, statusId);
        final String eventUri = String.format("/events/%d", id);

        return ResponseEntity.created(URI.create(eventUri)).build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable long id, @RequestBody com.tem.eventservice.api.dto.Event event) {
        String name = event.getName();
        String location = event.getLocation();
        Timestamp date =  event.getDate();
        Long statusId = event.getStatusId();
        try {
            eventService.updateEvent(id, name, location, date, statusId);

            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        eventService.deleteEvent(id);

        return ResponseEntity.noContent().build();
    }

}