package com.physioflow.infrastructure.persistence.entity;

import com.physioflow.domain.model.enumtype.Role;
import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "therapists")
public class TherapistJpaEntity {

    @Id
    private UUID id;

    @Column(nullable = false)
    private String fullName;

    private String area;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String passwordHash;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Column(nullable = false)
    private String gender; // 🔥 agregado

    protected TherapistJpaEntity() {
    }

    public TherapistJpaEntity(UUID id,
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

    public String getArea() {
        return area;
    }

    public void SetArea(String area) {
        this.area = area;
    }
}