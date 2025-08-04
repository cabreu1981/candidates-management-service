package com.cabreu.candidatesmanagementservice.domain.ports;

import com.cabreu.candidatesmanagementservice.domain.model.Client;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


public interface ClientRepository {

    Client save(Client client);
    List<Client> findAll();
    Optional<Client> findById(Long id);


}
