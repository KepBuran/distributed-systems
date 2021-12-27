package com.tem.taskservice.api.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
public final class Task {
    private Long eventId;
    private String name;
    private String description;
    private Timestamp deadline;
    private Long statusId;


}

