package com.ridesharingapp.controllers;

import com.ridesharingapp.dto.requests.UpdateUserRequest;
import com.ridesharingapp.models.User;
import com.ridesharingapp.services.interfaces.UserService;
import com.ridesharingapp.utils.exceptions.BadRequestException;
import com.ridesharingapp.utils.exceptions.NotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;


    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("{id}")
    public ResponseEntity<User> getById(@PathVariable(value = "id") int userId) throws NotFoundException {
        return ResponseEntity.ok(userService.getById(userId));
    }

    @GetMapping("all")
    public ResponseEntity<List<User>> getAll() {
        return ResponseEntity.ok(userService.getAll());
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping
    public ResponseEntity<User> editUserInfo(@Valid @RequestBody UpdateUserRequest request) throws BadRequestException, NotFoundException {
        userService.update(request);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("{id}")
    public ResponseEntity<Void> removeUser(@PathVariable(value = "id") int userId) throws NotFoundException {
        userService.delete(userId);
        return ResponseEntity.ok().build();

    }
}

