package com.cabreu.candidatesmanagementservice.adapter.jpa.repository;

import com.cabreu.candidatesmanagementservice.adapter.jpa.entity.ClientEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaClientRepository extends JpaRepository<ClientEntity, Long> {
}
