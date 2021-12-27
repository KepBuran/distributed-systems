package com.tem.userservice.api;

import com.tem.userservice.api.dto.Event;
import com.tem.userservice.api.dto.Task;
import com.tem.userservice.repo.model.User;
import com.tem.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.sql.Timestamp;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {

    //############Communication with other services#############
    @GetMapping("/{id}/events")
    public ResponseEntity<List<Event>> showEvents(@PathVariable Long id) {
        try {
            final List<Event> userEvents = userService.fetchUserEvents(id);
            return ResponseEntity.ok(userEvents);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}/tasks")
    public ResponseEntity<List<Task>> showTasks(@PathVariable Long id) {
        try {
            final List<Task> userTasks = userService.fetchUserTasks(id);
            return ResponseEntity.ok(userTasks);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
    //#####################################################


    //###################CRUD##############################
    @Autowired
    public final UserService userService;

    @GetMapping
    public ResponseEntity<List<User>> index() {
        final List<User> users = userService.fetchAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> show(@PathVariable long id) {
        try {
            final User user = userService.fetchUserById(id);
            return ResponseEntity.ok(user);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }

    }

    @PostMapping("/role")
    public ResponseEntity<Void> create_role(@RequestBody com.tem.userservice.api.dto.Role role) {
        String name = role.getName();

        long id = userService.createRole(name);
        final String roleUri = String.format("/events/role/%d", id);

        return ResponseEntity.created(URI.create(roleUri)).build();
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody com.tem.userservice.api.dto.User user) {
        String username = user.getUsername();
        String name = user.getName();
        String surname = user.getSurname();
        String email = user.getEmail();
        Long roleId = user.getRoleId();

        long id = userService.createUser(username, name, surname, email, roleId);
        final String eventUri = String.format("/events/%d", id);

        return ResponseEntity.created(URI.create(eventUri)).build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable long id, @RequestBody com.tem.userservice.api.dto.User user) {
        String username = user.getUsername();
        String name = user.getName();
        String surname = user.getSurname();
        String email = user.getEmail();
        Long roleId = user.getRoleId();

        try {
            userService.updateUser(id, username, name, surname, email, roleId);

            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        userService.deleteUser(id);

        return ResponseEntity.noContent().build();
    }
    //#####################################################

}
