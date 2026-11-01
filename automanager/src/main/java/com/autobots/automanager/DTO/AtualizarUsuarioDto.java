package com.autobots.automanager.DTO;

import com.autobots.automanager.entitades.*;
import com.autobots.automanager.enumeracoes.PerfilUsuario;

import java.util.Optional;
import java.util.Set;

public record AtualizarUsuarioDto(
        Optional<String> nome,
        Optional<String> nomeSocial,
        Optional<Set<PerfilUsuario>> perfis,
        Optional<Set<Telefone>> telefones,
        Optional<Endereco> endereco,
        Optional<Set<Documento>> documentos,
        Optional<Set<Email>> emails,
        Optional<Set<Credencial>> credenciais,
        Optional<Set<Mercadoria>> mercadorias,
        Optional<Set<Venda>> vendas,
        Optional<Set<Veiculo>> veiculos
){
}
