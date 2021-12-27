package com.tem.eventservice.service;

import com.tem.eventservice.api.dto.EventTasks;
import com.tem.eventservice.api.dto.Task;
import com.tem.eventservice.repo.EventRepo;
import com.tem.eventservice.repo.EventStatusRepo;
import com.tem.eventservice.repo.EventUserRepo;
import com.tem.eventservice.repo.model.Event;
import com.tem.eventservice.repo.model.EventStatus;
import com.tem.eventservice.repo.model.EventUser;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.sql.Timestamp;
import java.util.*;

@RequiredArgsConstructor
@Service
public class EventService {

    private final String userUrlAddress = "http://userservice:8080/users";
    private final String taskUrlAddress = "http://taskservice:8082/tasks";
//    private final String userUrlAddress = "http://localhost:8080/users";
//    private final String taskUrlAddress = "http://localhost:8082/tasks";

    private final RestTemplate restTemplate = new RestTemplate();

    private final EventRepo eventRepo;
    private final EventStatusRepo eventStatusRepo;
    private final EventUserRepo eventUserRepo;

    //############Communication with other services#############
    public List<Event>fetchAllByUserId(Long eventId) {
        List<EventUser> eventUserList = eventUserRepo.findByUserId(eventId);

        List<Event> events = new ArrayList<>();

        for (int i = 0; i < eventUserList.size(); i++) {
            final Event event = eventRepo.findById(eventUserList.get(i).getEventId()).orElseThrow(() -> new IllegalArgumentException("Invalid event id"));
            events.add(eventRepo.findById(eventUserList.get(i).getEventId()).get());
        }

        return events;
    }

    public EventTasks fetchEventAndTasks(Long id) {
        final Optional<Event> maybeEvent = eventRepo.findById(id);

        if (maybeEvent.isEmpty())
            throw new IllegalArgumentException("Invalid event ID");

        Event event = maybeEvent.get();

        final List<Task> tasks = fetchEventTasks(id);

        return new EventTasks(event.getName(), event.getLocation(), event.getDate(), event.getStatus(), tasks);

    }

    public List<Task> fetchEventTasks(Long id) {
        final Event event = eventRepo.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid event id"));

        final String taskLocation = taskUrlAddress + String.format("/event/%d", id);

        final ResponseEntity<Task[]> responseEntity = restTemplate.getForEntity(taskLocation, Task[].class);
        final Task[] TaskArray = responseEntity.getBody();

        final List<Task> tasks;

        if (TaskArray != null)
            tasks = Arrays.asList(TaskArray);
        else
            tasks = Collections.emptyList();

        return tasks;
    }
    //#####################################################

    public long addUser(Long id, Long userId) {
        final EventUser eventUser = new EventUser(id, userId);
        final EventUser savedEventUser = eventUserRepo.save(eventUser);
        return savedEventUser.getId();
    }


    //#########################CRUD#######################
    public List<Event> fetchAllEvents() {
        return eventRepo.findAll();
    }

    public Event fetchEventById(long id) throws IllegalArgumentException {
        final Optional<Event> maybeEvent = eventRepo.findById(id);

        if (maybeEvent.isPresent())
            return maybeEvent.get();
        else
            throw new IllegalArgumentException("Invalid event ID");
    }

    public long createEvent(String name, String location, Timestamp date, long eventStatusId) {
        final Optional<EventStatus> maybeStatus = eventStatusRepo.findById(eventStatusId);

        if (maybeStatus.isEmpty())
            throw new IllegalArgumentException("Invalid event status ID");

        final EventStatus status = eventStatusRepo.findById(eventStatusId).get();
        final Event event = new Event(name, location, date, status);
        final Event savedEvent = eventRepo.save(event);
        long id = savedEvent.getId();
        return savedEvent.getId();
    }

    public long createEventStatus(String name, String color) {
        final EventStatus eventStatus = new EventStatus(name, color);
        final EventStatus savedEventStatus = eventStatusRepo.save(eventStatus);
        long id = savedEventStatus.getId();
        return savedEventStatus.getId();
    }


    public void updateEvent(long id, String name, String location, Timestamp date, Long eventStatusId) throws IllegalArgumentException {
        EventStatus eventStatus = null;
        if (!(eventStatusId == null)) {
            final Optional<EventStatus> maybeStatus = eventStatusRepo.findById(eventStatusId);
            if (!maybeStatus.isEmpty()) {
                eventStatus = maybeStatus.get();
            }
        }

        final Optional<Event> maybeEvent= eventRepo.findById(id);


        if (maybeEvent.isEmpty())
            throw new IllegalArgumentException("Invalid event ID");

        final Event event = maybeEvent.get();
        if (name != null && !name.isBlank()) event.setName(name);
        if (location != null && !location.isBlank()) event.setLocation(location);
        if (date != null) event.setDate(date);
        if (!(eventStatus == null)) event.setStatus(eventStatus);
        eventRepo.save(event);
    }

    public void deleteEvent(long id) {
        eventRepo.deleteById(id);
    }
    //############################################################




}
