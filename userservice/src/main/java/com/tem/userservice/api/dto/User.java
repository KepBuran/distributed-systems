package com.tem.userservice.api.dto;

import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class User {

    private String username;
    private String name;
    private String surname;
    private String email;
    private long roleId;
}
