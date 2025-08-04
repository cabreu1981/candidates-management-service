package com.cabreu.candidatesmanagementservice.domain.service;


import com.cabreu.candidatesmanagementservice.domain.model.Client;
import com.cabreu.candidatesmanagementservice.domain.ports.ClientRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.OptionalDouble;


public class ClientService {

    private final ClientRepository clientRepository;

    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }

    public Client registerClient(Client client) {
        return clientRepository.save(client);
    }

    public double getAverageAge() {
        return clientRepository
                .findAll().stream()
                .mapToDouble(Client::getAge)
                .average()
                .orElse(0);
    }

    public double getStandardDeviationAge() {
        List<Client> clients = clientRepository.findAll();

        OptionalDouble average = clients.stream()
                .mapToDouble(Client::getAge)
                .average();

        if(average.isEmpty()) {
            return 0;
        }
        double mean = average.getAsDouble();

        double variance = clients.stream()
                .mapToDouble(client -> Math.pow(client.getAge() - mean, 2))
                .average()
                .orElse(0);

        return Math.sqrt(variance);

    }


}
