package com.autobots.automanager.DTO;

import com.autobots.automanager.entitades.Mercadoria;
import com.autobots.automanager.entitades.Servico;
import com.autobots.automanager.entitades.Usuario;
import com.autobots.automanager.entitades.Veiculo;

import java.util.Optional;
import java.util.Set;

public record AtualizarVendaDto(
        Optional<String> identificacao,
        Optional<Usuario> cliente,
        Optional<Usuario> funcionario,
        Optional<Set<Mercadoria>> mercadorias,
        Optional<Set<Servico>> servicos,
        Optional<Veiculo> veiculo

) {
}