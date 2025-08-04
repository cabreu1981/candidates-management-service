package com.cabreu.candidatesmanagementservice.adapter;

import com.cabreu.candidatesmanagementservice.adapter.jpa.entity.ClientEntity;
import com.cabreu.candidatesmanagementservice.adapter.jpa.repository.JpaClientRepository;
import com.cabreu.candidatesmanagementservice.domain.model.Client;
import com.cabreu.candidatesmanagementservice.domain.ports.ClientRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class ClientRepositoryJpaAdapter implements ClientRepository {

    private final JpaClientRepository jpaClientRepository;

    public ClientRepositoryJpaAdapter(JpaClientRepository jpaClientRepository) {
        this.jpaClientRepository = jpaClientRepository;
    }

    @Override
    public Client save(Client client) {
        return toDomain(jpaClientRepository.save(toEntity(client)));
    }

    @Override
    public List<Client> findAll() {
        return jpaClientRepository.findAll().stream()
                .map(this::toDomain)
                .toList();
    }

    @Override
    public Optional<Client> findById(Long id) {
        return jpaClientRepository.findById(id).map(this::toDomain);
    }

    private ClientEntity toEntity(Client client) {
        ClientEntity entity = new ClientEntity();

        if (client.getId() != null) {
            entity.setId(client.getId());
        }
        entity.setFirstName(client.getFirstName());
        entity.setLastName(client.getLastName());
        entity.setAge(client.getAge());
        entity.setBirthDate(client.getBirthDate());
        return entity;
    }


    private Client toDomain(ClientEntity entity) {
        return new Client(
                entity.getId(),
                entity.getFirstName(),
                entity.getLastName(),
                entity.getAge(),
                entity.getBirthDate()
        );
    }
}
