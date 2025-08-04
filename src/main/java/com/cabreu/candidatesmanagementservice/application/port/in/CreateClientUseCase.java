package com.cabreu.candidatesmanagementservice.application.port.in;

/*
 * Use case interface
 * crete client
 * created by carlos abreu
 */


import com.cabreu.candidatesmanagementservice.domain.model.Client;

public interface CreateClientUseCase {

    Client create(Client client);


}
