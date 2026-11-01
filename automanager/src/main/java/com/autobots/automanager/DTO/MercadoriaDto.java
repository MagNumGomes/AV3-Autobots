package com.autobots.automanager.DTO;

import java.util.Optional;

public record MercadoriaDto(
        String nome,
        long quantidade,
        double valor,
        Optional<String> descricao
){
}
