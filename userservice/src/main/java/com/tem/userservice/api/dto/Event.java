package com.tem.userservice.api.dto;

import lombok.Data;
import lombok.NoArgsConstructor;


import java.sql.Timestamp;

@Data
@NoArgsConstructor
public class Event {
    private String name;
    private String location;
    private Timestamp date;
    private EventStatus status;

}