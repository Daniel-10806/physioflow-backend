package com.physioflow.application.service;

import com.physioflow.application.dto.request.LoginRequest;
import com.physioflow.application.dto.request.RegisterRequest;
import com.physioflow.application.dto.response.AuthResponse;
import com.physioflow.domain.model.entity.Therapist;
import com.physioflow.domain.model.enumtype.Role;
import com.physioflow.domain.model.exception.DomainException;
import com.physioflow.domain.repository.TherapistRepository;
import com.physioflow.infrastructure.security.JwtService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@Service
public class AuthApplicationService {

    private final TherapistRepository repository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthApplicationService(
            TherapistRepository repository,
            BCryptPasswordEncoder passwordEncoder,
            JwtService jwtService) {

        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public AuthResponse register(RegisterRequest request) {

        if (repository.findByEmail(request.getEmail()).isPresent()) {
            throw new DomainException("El correo ya está registrado");
        }

        Therapist therapist = new Therapist(
                UUID.randomUUID(),
                request.getFullName(),
                request.getEmail(),
                passwordEncoder.encode(request.getPassword()),
                Role.ROLE_THERAPIST,
                request.getGender());

        repository.save(therapist);

        String token = jwtService.generateToken(
                therapist.getId(),
                therapist.getFullName(),
                therapist.getGender(),
                therapist.getRole().name());

        return new AuthResponse(token);
    }

    public AuthResponse login(LoginRequest request) {

        Therapist therapist = repository.findByEmail(request.getEmail())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.UNAUTHORIZED,
                        "Credenciales inválidas"));

        if (!passwordEncoder.matches(
                request.getPassword(),
                therapist.getPasswordHash())) {

            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED,
                    "Credenciales inválidas");
        }

        String token = jwtService.generateToken(
                therapist.getId(),
                therapist.getFullName(),
                therapist.getGender(),
                therapist.getRole().name());

        return new AuthResponse(token);
    }
}