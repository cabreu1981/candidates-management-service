package com.cabreu.candidatesmanagementservice.application.service;


import com.cabreu.candidatesmanagementservice.application.port.in.CreateClientUseCase;
import com.cabreu.candidatesmanagementservice.domain.model.Client;
import com.cabreu.candidatesmanagementservice.domain.ports.ClientRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CreateClientService implements CreateClientUseCase {

    private final ClientRepository clientRepository;

    public CreateClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public Client create(Client client) {
        return clientRepository.save(client);
    }
}
