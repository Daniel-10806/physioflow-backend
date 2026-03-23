package com.physioflow.application.service;

import com.physioflow.domain.model.entity.User;
import com.physioflow.infrastructure.persistence.repository.UserJpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserJpaRepository repo;

    public UserService(UserJpaRepository repo) {
        this.repo = repo;
    }

    public List<User> getAll() {
        return repo.findAll();
    }

    public User create(User u) {
        return repo.save(u);
    }

    public User update(Long id, User data) {

        User u = repo.findById(id).orElseThrow();

        u.setActive(data.isActive());

        return repo.save(u);
    }

    public void delete(Long id) {

        repo.deleteById(id);

    }

}