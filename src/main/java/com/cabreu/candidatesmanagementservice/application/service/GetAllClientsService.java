package com.cabreu.candidatesmanagementservice.application.service;


import com.cabreu.candidatesmanagementservice.application.port.in.GetAllClientsUseCase;
import com.cabreu.candidatesmanagementservice.domain.model.Client;
import com.cabreu.candidatesmanagementservice.domain.model.ClientWithEstimation;
import com.cabreu.candidatesmanagementservice.domain.ports.ClientRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GetAllClientsService implements GetAllClientsUseCase {

    private final ClientRepository clientRepository;

    public GetAllClientsService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public List<ClientWithEstimation> getAll() {
        return clientRepository.findAll().stream()
                .map(this::toClientWithEstimation)
                .collect(Collectors.toList());
    }

    private ClientWithEstimation toClientWithEstimation(Client client) {
        LocalDate estimatedRetirementDate = client.getBirthDate().plusYears(65);
        return new ClientWithEstimation(
                client.getId(),
                client.getFirstName(),
                client.getLastName(),
                client.getAge(),
                client.getBirthDate(),
                estimatedRetirementDate
        );
    }
}
