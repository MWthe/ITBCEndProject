package com.example.EndProjectITBC.repository;

import com.example.EndProjectITBC.models.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface ClientRepository extends JpaRepository<Client, UUID> {

    @Query("SELECT c FROM Client c WHERE c.id = ?1")
    Optional<Client> getClientById(UUID id);

    @Query("SELECT c FROM Client c WHERE c.id = ?1")
    Client getClientByUUId(UUID id);

    @Query("SELECT c FROM Client c WHERE c.username = ?1")
    Optional<Client> findClientByUsername(String username);

    @Query("SELECT c FROM Client c WHERE c.password = ?1")
    Optional<Client> findClientByPassword(String password);

    @Query("SELECT c FROM Client c WHERE c.email = ?1")
    Optional<Client> findClientByEmail(String email);

}
