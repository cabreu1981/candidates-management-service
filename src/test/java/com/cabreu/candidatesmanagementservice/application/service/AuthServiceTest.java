package com.cabreu.candidatesmanagementservice.application.service;

import com.cabreu.candidatesmanagementservice.adapter.jpa.entity.RoleEntity;
import com.cabreu.candidatesmanagementservice.adapter.jpa.entity.UserEntity;
import com.cabreu.candidatesmanagementservice.adapter.jpa.repository.UserRepository;
import com.cabreu.candidatesmanagementservice.adapter.kafka.AuthEventProducer;
import com.cabreu.candidatesmanagementservice.adapter.rest.dto.AuthRequest;
import com.cabreu.candidatesmanagementservice.config.JwtUtil;
import com.cabreu.candidatesmanagementservice.infrastructure.exception.BusinessException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtUtil jwtUtil;

    @InjectMocks
    private AuthService authService;

    @Mock
    private AuthEventProducer authEventProducer;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void register_shouldSaveNewUser() {
        AuthRequest request = new AuthRequest("carlos", "123456");
        when(userRepository.findByUsername("carlos")).thenReturn(Optional.empty());
        when(passwordEncoder.encode("123456")).thenReturn("encrypted");

        String result = authService.register(request);
        authEventProducer.sendAuthEvent("auth.events", "User registered successfully");

        assertEquals("User registered successfully", result);
        verify(userRepository).save(any(UserEntity.class));
    }


    @Test
    void register_shouldThrowIfUsernameExists() {
        AuthRequest request = new AuthRequest("admin", "abc");
        when(userRepository.findByUsername("admin")).thenReturn(Optional.of(new UserEntity()));
        authEventProducer.sendAuthEvent("auth.events", "User registered successfully");
        assertThrows(BusinessException.class, () -> authService.register(request));
    }


    @Test
    void login_shouldReturnJwtToken() {
        AuthRequest request = new AuthRequest("admin", "123456");

        UserEntity user = new UserEntity();
        user.setUsername("admin");
        user.setPassword("encrypted");
        RoleEntity role = new RoleEntity();
        role.setName("ROLE_ADMIN");
        user.setRoles(Set.of(role));

        when(userRepository.findByUsername("admin")).thenReturn(Optional.of(user));
        when(jwtUtil.generateToken(eq("admin"), any())).thenReturn("fake-jwt");

        when(authenticationManager.authenticate(any()))
            .thenReturn(mock(Authentication.class));

        authEventProducer.sendAuthEvent("auth.events", "User registered successfully");

        String token = authService.login(request);

        assertEquals("fake-jwt", token);
        verify(jwtUtil).generateToken("admin", List.of("ROLE_ADMIN"));
    }

    @Test
    void login_shouldThrowOnInvalidCredentials() {
        AuthRequest request = new AuthRequest("admin", "wrong");

        doThrow(new BadCredentialsException("Invalid"))
                .when(authenticationManager)
                .authenticate(any());

        assertThrows(BadCredentialsException.class, () -> authService.login(request));
    }

    @Test
    void login_shouldThrowIfUserNotFound() {
        AuthRequest request = new AuthRequest("ghost", "123");

        when(userRepository.findByUsername("ghost")).thenReturn(Optional.empty());
        when(authenticationManager.authenticate(any()))
            .thenReturn(mock(Authentication.class));
        authEventProducer.sendAuthEvent("auth.events", "User registered successfully");

        assertThrows(BusinessException.class, () -> authService.login(request));
    }

}
