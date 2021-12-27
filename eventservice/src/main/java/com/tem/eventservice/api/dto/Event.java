package com.tem.eventservice.api.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
public final class Event {
    private String name;
    private String location;
    private Timestamp date;
    private Long statusId;


}