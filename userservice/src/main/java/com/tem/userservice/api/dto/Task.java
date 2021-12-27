package com.tem.userservice.api.dto;

import lombok.Data;
import lombok.NoArgsConstructor;


import java.sql.Timestamp;

@Data
@NoArgsConstructor
public class Task {
    private Long eventId;
    private String name;
    private String description;
    private Timestamp deadline;
    private TaskStatus taskStatus;

}
