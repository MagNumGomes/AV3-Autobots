package com.autobots.automanager.modelos.atualizador;

import com.autobots.automanager.entitades.Telefone;
import org.springframework.stereotype.Component;

@Component
public class AtualizadorTelefone {
    public Telefone atualizarTelefone(Telefone telefone, Telefone setTelefone){
        telefone.setDdd(setTelefone.getDdd());
        telefone.setNumero(setTelefone.getNumero());
        return telefone;
    }
}
