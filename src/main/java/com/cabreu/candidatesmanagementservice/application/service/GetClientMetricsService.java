package com.cabreu.candidatesmanagementservice.application.service;


import com.cabreu.candidatesmanagementservice.application.port.in.GetClientMetricsUseCase;
import com.cabreu.candidatesmanagementservice.domain.model.Client;
import com.cabreu.candidatesmanagementservice.domain.model.ClientMetrics;
import com.cabreu.candidatesmanagementservice.domain.ports.ClientRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetClientMetricsService implements GetClientMetricsUseCase {

    private final ClientRepository clientRepository;

    public GetClientMetricsService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public ClientMetrics getMetrics() {
        List<Client> clients = clientRepository.findAll();
        if (clients.isEmpty()) {
            return new ClientMetrics(0, 0);
        }

        double average = clients.stream().mapToInt(Client::getAge).average().orElse(0);

        double stdDev = Math.sqrt(
                clients.stream()
                        .mapToDouble(c -> Math.pow(c.getAge() - average, 2))
                        .average()
                        .orElse(0)
        );

        return new ClientMetrics(average, stdDev);
    }
}
