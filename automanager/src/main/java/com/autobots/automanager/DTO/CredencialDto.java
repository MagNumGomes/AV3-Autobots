package com.autobots.automanager.DTO;

import java.util.Optional;

public record CredencialDto(
        Optional<String> nomeUsuario,
        Optional<String> senha,
        Optional<Long> codigo
) {
}
