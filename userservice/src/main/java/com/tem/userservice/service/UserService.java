package com.tem.userservice.service;

import com.tem.userservice.api.dto.Event;
import com.tem.userservice.api.dto.Task;
import com.tem.userservice.repo.RoleRepo;
import com.tem.userservice.repo.UserRepo;
import com.tem.userservice.repo.model.Role;
import com.tem.userservice.repo.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {
    @Autowired
    private final UserRepo userRepo;
    private final RoleRepo roleRepo;

    private final RestTemplate restTemplate = new RestTemplate();

    private final String eventUrlAddress = "http://eventservice:8081/events";
    private final String taskUrlAddress = "http://taskservice:8082/tasks";
//    private final String eventUrlAddress = "http://localhost:8081/events";
//    private final String taskUrlAddress = "http://localhost:8082/tasks";


    //############Communication with other services#############
    public List<Event> fetchUserEvents(Long id) {
        final User user = userRepo.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid event id"));

        final String eventLocation = eventUrlAddress + String.format("/user/%d", id);

        final ResponseEntity<Event[]> responseEntity = restTemplate.getForEntity(eventLocation, Event[].class);
        final Event[] eventArray = responseEntity.getBody();

        final List<Event> events;

        if (eventArray != null)
            events = Arrays.asList(eventArray);
        else
            events = Collections.emptyList();

        return events;
    }

    public List<Task> fetchUserTasks(Long id) {
        final User user = userRepo.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid event id"));

        final String taskLocation = taskUrlAddress + String.format("/user/%d", id);

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



    //#########################CRUD#######################
    public List<User> fetchAllUsers() {
        return userRepo.findAll();
    }

    public User fetchUserById(long id) throws IllegalArgumentException {
        final Optional<User> maybeUser = userRepo.findById(id);

        if (maybeUser.isPresent())
            return maybeUser.get();
        else
            throw new IllegalArgumentException("Invalid event ID");
    }

    public long createUser(String username, String name, String surname, String email, Long roleId) {
        if (roleId == null)
            throw new IllegalArgumentException("You need to set role for user");

        final Optional<Role> maybeRole = roleRepo.findById(roleId);

        if (maybeRole.isEmpty())
            throw new IllegalArgumentException("Invalid role ID");

        final Role role = roleRepo.findById(roleId).get();
        final User user = new User(username, name, surname, email, role);
        final User savedUser = userRepo.save(user);
        long id = savedUser.getId();
        return savedUser.getId();
    }

    public long createRole(String name) {
        final Role role = new Role(name);
        final Role savedRole = roleRepo.save(role);
        long id = savedRole.getId();
        return savedRole.getId();
    }


    public void updateUser(long id, String username, String name, String surname, String email, Long roleId) throws IllegalArgumentException {
        Role role = null;
        if (!(roleId == null)) {
            final Optional<Role> maybeRole = roleRepo.findById(roleId);
            if (!maybeRole.isEmpty()) {
                role = maybeRole.get();
            }
        }

        final Optional<User> maybeUser= userRepo.findById(id);

        if (maybeUser.isEmpty())
            throw new IllegalArgumentException("Invalid user ID");

        final User user = maybeUser.get();
        if (username != null && !username.isBlank()) user.setUsername(username);
        if (name != null && !name.isBlank()) user.setName(name);
        if (surname != null && !surname.isBlank()) user.setSurname(surname);
        if (email != null && !email.isBlank()) user.setEmail(email);

        if (!(role == null)) user.setRole(role);
        userRepo.save(user);
    }

    public void deleteUser(long id) {
        userRepo.deleteById(id);
    }
    //############################################################
}
