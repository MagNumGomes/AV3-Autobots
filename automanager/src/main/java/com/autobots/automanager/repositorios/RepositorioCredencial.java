package com.autobots.automanager.repositorios;

import com.autobots.automanager.entitades.Credencial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RepositorioCredencial extends JpaRepository<Credencial, Long> {
    @Query("SELECT c FROM Credencial c WHERE c.inativo = false ")
    List<Credencial> findActiveCredentials();

    @Query("SELECT c FROM Credencial c WHERE c.id = :id AND c.inativo = false")
    Optional<Credencial> findActiveCredentialById(@Param("id")Long id);
}
