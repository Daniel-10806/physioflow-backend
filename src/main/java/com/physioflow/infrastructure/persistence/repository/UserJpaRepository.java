package com.physioflow.infrastructure.persistence.repository;

import com.physioflow.domain.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserJpaRepository extends JpaRepository<User, Long> {
}