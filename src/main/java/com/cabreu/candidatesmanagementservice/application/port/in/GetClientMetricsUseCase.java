package com.cabreu.candidatesmanagementservice.application.port.in;

import com.cabreu.candidatesmanagementservice.domain.model.ClientMetrics;

public interface GetClientMetricsUseCase {

    ClientMetrics getMetrics();

}
