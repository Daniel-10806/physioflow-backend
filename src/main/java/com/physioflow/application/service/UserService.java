package com.physioflow.application.service;

import com.physioflow.infrastructure.persistence.entity.UserJpaEntity;
import com.physioflow.infrastructure.persistence.repository.UserJpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserJpaRepository repo;

    public UserService(UserJpaRepository repo) {
        this.repo = repo;
    }

    public List<UserJpaEntity> getAll() {
        return repo.findAll();
    }

    public UserJpaEntity create(UserJpaEntity u) {
        return repo.save(u);
    }

    public UserJpaEntity update(Long id, UserJpaEntity data) {

        UserJpaEntity u = repo.findById(id).orElseThrow();

        u.setActive(data.isActive());

        return repo.save(u);
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }
}