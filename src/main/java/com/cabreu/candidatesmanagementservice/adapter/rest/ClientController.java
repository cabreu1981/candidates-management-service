package com.cabreu.candidatesmanagementservice.adapter.rest;

import com.cabreu.candidatesmanagementservice.application.port.in.*;

import com.cabreu.candidatesmanagementservice.domain.model.Client;
import com.cabreu.candidatesmanagementservice.domain.model.ClientMetrics;
import com.cabreu.candidatesmanagementservice.domain.model.ClientWithEstimation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.List;

@RestController
@RequestMapping("/clients")
public class ClientController {

    private final CreateClientUseCase createClientUseCase;
    private final GetAllClientsUseCase getAllClientsUseCase;
    private final GetClientMetricsUseCase getClientMetricsUseCase;

    public ClientController(CreateClientUseCase createClientUseCase,
                            GetAllClientsUseCase getAllClientsUseCase,
                            GetClientMetricsUseCase getClientMetricsUseCase) {
        this.createClientUseCase = createClientUseCase;
        this.getAllClientsUseCase = getAllClientsUseCase;
        this.getClientMetricsUseCase = getClientMetricsUseCase;
    }

    @Operation(summary = "Crear nuevo cliente")
       @ApiResponses(value = {
           @ApiResponse(responseCode = "201", description = "Cliente creado exitosamente"),
           @ApiResponse(responseCode = "400", description = "Datos inválidos")
       })
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    public ResponseEntity<Client> createClient(@RequestBody Client client) {
        Client created = createClientUseCase.create(client);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }


    @Operation(summary = "Listar todos los clientes")
       @ApiResponses(value = {
           @ApiResponse(responseCode = "200", description = "Clientes listados exitosamente"),
           @ApiResponse(responseCode = "401", description = "No autorizado")
       })
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @GetMapping
    public ResponseEntity<List<ClientWithEstimation>> getAllClients() {
        List<ClientWithEstimation> clients = getAllClientsUseCase.getAll();
        return ResponseEntity.ok(clients);
    }


    @Operation(summary = "Obtener métricas de clientes")
        @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Métricas de clientes exitosamente"),
            @ApiResponse(responseCode = "401", description = "No autorizado")
        })
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/metrics")
    public ResponseEntity<ClientMetrics> getMetrics() {
        ClientMetrics metrics = getClientMetricsUseCase.getMetrics();
        return ResponseEntity.ok(metrics);
    }
}

