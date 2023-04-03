package com.api.microservice.repositories;

import com.api.microservice.models.Tabela;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<Tabela, UUID> {

    Tabela findByNameAndSenha(String name, String senha );




}


