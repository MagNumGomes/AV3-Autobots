package com.autobots.automanager.DTO;

import com.autobots.automanager.entitades.*;
import com.autobots.automanager.enumeracoes.PerfilUsuario;

import java.util.Set;

public record UsuarioDto(
        String nome,
        String nomeSocial,
        Set<PerfilUsuario> perfis,
        Set<Telefone> telefones,
        Endereco endereco,
        Set<Documento> documentos,
        Set<Email> emails,
        Set<Credencial> credenciais,
        Set<Mercadoria> mercadorias,
        Set<Venda> vendas,
        Set<Veiculo> veiculos
) {
}
