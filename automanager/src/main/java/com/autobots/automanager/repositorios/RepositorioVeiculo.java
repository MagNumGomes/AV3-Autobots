package com.autobots.automanager.repositorios;

import com.autobots.automanager.entitades.Veiculo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepositorioVeiculo extends JpaRepository<Veiculo, Long> {
}
