package com.cabreu.candidatesmanagementservice.application.port.in;


import com.cabreu.candidatesmanagementservice.domain.model.ClientWithEstimation;

import java.util.List;

public interface GetAllClientsUseCase {
    List<ClientWithEstimation> getAll();
}
