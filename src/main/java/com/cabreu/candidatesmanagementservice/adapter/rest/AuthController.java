package com.cabreu.candidatesmanagementservice.adapter.rest;

import com.cabreu.candidatesmanagementservice.adapter.rest.dto.AuthRequest;
import com.cabreu.candidatesmanagementservice.application.service.AuthService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/auth")
@Tag(name = "Auth", description = "Operaciones de autenticación")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @Operation(summary = "Registro de nuevo usuario")
     @ApiResponses(value = {
         @ApiResponse(responseCode = "200", description = "Usuario registrado exitosamente"),
         @ApiResponse(responseCode = "400", description = "Nombre de usuario ya existente")
     })
    @PostMapping("/register")
    public String register(@Valid @RequestBody AuthRequest request) {
        return authService.register(request);
    }


    @Operation(summary = "Inicio de sesión")
       @ApiResponses(value = {
           @ApiResponse(responseCode = "200", description = "Inicio de sesión exitoso"),
           @ApiResponse(responseCode = "401", description = "Credenciales inválidas")
       })
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody AuthRequest request) {
        return ResponseEntity.ok(Map.of("token", authService.login(request)));
    }
}
