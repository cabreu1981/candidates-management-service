package com.cabreu.candidatesmanagementservice.application.service;


import com.cabreu.candidatesmanagementservice.adapter.jpa.entity.RoleEntity;
import com.cabreu.candidatesmanagementservice.adapter.jpa.entity.UserEntity;
import com.cabreu.candidatesmanagementservice.adapter.jpa.repository.UserRepository;
import com.cabreu.candidatesmanagementservice.adapter.kafka.AuthEventProducer;
import com.cabreu.candidatesmanagementservice.adapter.rest.dto.AuthRequest;
import com.cabreu.candidatesmanagementservice.config.JwtUtil;
import com.cabreu.candidatesmanagementservice.infrastructure.exception.BusinessException;
import org.springframework.security.authentication.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final AuthEventProducer authEventProducer;

    public AuthService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       AuthenticationManager authenticationManager,
                       JwtUtil jwtUtil, AuthEventProducer authEventProducer) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.authEventProducer = authEventProducer;
    }

    public String register(AuthRequest request) {
        if (userRepository.findByUsername(request.username()).isPresent()) {
            throw new BusinessException("Username already exists");
        }

        UserEntity user = new UserEntity();
        user.setUsername(request.username());
        user.setPassword(passwordEncoder.encode(request.password()));
        userRepository.save(user);

        try {
            authEventProducer.sendAuthEvent("auth.events", "User registered successfully");
        } catch (Exception e) {
            // Log y continuar, ya que el registro sí fue exitoso
            System.err.println("⚠ Error sending Kafka event: " + e.getMessage());
            // O puedes usar un logger:
            // log.warn("Kafka unavailable, skipping event", e);
        }

        return "User registered successfully";
    }


    public String login(AuthRequest request) {
        try {
            authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.username(), request.password())
            );
        } catch (Exception e) {
            throw new BadCredentialsException("Invalid username or password");
        }

        UserEntity user = userRepository.findByUsername(request.username())
                .orElseThrow(() -> new BusinessException("User not found"));

        List<String> roles = user.getRoles().stream()
                .map(RoleEntity::getName)
                .toList();

        try {
            authEventProducer.sendAuthEvent("auth-events", "User logged in: " + request.username());
        } catch (Exception e) {
            System.err.println("⚠ Error sending Kafka event: " + e.getMessage());
        }

        return jwtUtil.generateToken(user.getUsername(), roles);
    }
}
