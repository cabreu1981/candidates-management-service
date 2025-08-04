package com.cabreu.candidatesmanagementservice.adapter.rest;

import com.cabreu.candidatesmanagementservice.adapter.rest.dto.AuthRequest;
import com.cabreu.candidatesmanagementservice.application.service.AuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class AuthControllerTest {

    private AuthService authService;
    private AuthController authController;

    @BeforeEach
    void setUp() {
        authService = mock(AuthService.class);
        authController = new AuthController(authService);
    }

    @Test
    void testRegister_shouldReturnToken() {
        // Given
        AuthRequest request = new AuthRequest("testuser", "password");
        String expectedToken = "registered-token";
        when(authService.register(request)).thenReturn(expectedToken);

        // When
        String actualToken = authController.register(request);

        // Then
        assertEquals(expectedToken, actualToken);
        verify(authService, times(1)).register(request);
    }

    @Test
    void testLogin_shouldReturnResponseEntityWithToken() {
        // Given
        AuthRequest request = new AuthRequest("testuser", "password");
        String expectedToken = "jwt-token";
        when(authService.login(request)).thenReturn(expectedToken);

        // When
        ResponseEntity<?> response = authController.login(request);

        // Then
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(Map.of("token", expectedToken), response.getBody());
        verify(authService, times(1)).login(request);
    }
}
