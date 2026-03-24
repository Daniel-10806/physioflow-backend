package com.physioflow.interfaces.rest.controller;

import com.physioflow.application.service.UserService;
import com.physioflow.infrastructure.persistence.entity.UserJpaEntity;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/settings/users")
@CrossOrigin
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping
    public List<UserJpaEntity> getAll() {
        return service.getAll();
    }

    @PostMapping
    public UserJpaEntity create(@RequestBody UserJpaEntity u) {
        return service.create(u);
    }

    @PutMapping("/{id}")
    public UserJpaEntity update(
            @PathVariable Long id,
            @RequestBody UserJpaEntity u) {

        return service.update(id, u);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}