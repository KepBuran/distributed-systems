package com.tem.eventservice.repo.model;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.sql.Timestamp;

@Entity
@Table(name = "events")

public final class Event{

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotEmpty(message = "Event name cannot be empty")
    @Column(name = "sname")
    private String name;

    @Column(name = "slocation")
    private String location;

    @Column(name = "devent_date")
    private Timestamp date;

    @JoinColumn(name = "status_id")
    @OneToOne(cascade = CascadeType.ALL)
    private EventStatus status;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Event() {
    }

    public Event(String name, String location, Timestamp date, EventStatus status) {
        this.name = name;
        this.location = location;
        this.date = date;
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public EventStatus getStatus() {
        return status;
    }

    public void setStatus(EventStatus status) {
        this.status = status;
    }
}
