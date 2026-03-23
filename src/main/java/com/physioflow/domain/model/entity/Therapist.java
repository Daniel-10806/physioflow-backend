package com.physioflow.domain.model.entity;

import com.physioflow.domain.model.enumtype.Role;

import java.util.UUID;

public class Therapist {

    private final UUID id;
    private final String fullName;
    private final String email;
    private final String passwordHash;
    private final Role role;
    private final String gender;

    public Therapist(UUID id,
            String fullName,
            String email,
            String passwordHash,
            Role role,
            String gender) {

        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.passwordHash = passwordHash;
        this.role = role;
        this.gender = gender;
    }

    public UUID getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public String getEmail() {
        return email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public Role getRole() {
        return role;
    }

    public String getGender() {
        return gender;
    }
}