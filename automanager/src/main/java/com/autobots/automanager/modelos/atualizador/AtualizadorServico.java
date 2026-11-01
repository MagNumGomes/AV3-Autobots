package com.autobots.automanager.modelos.atualizador;

import com.autobots.automanager.entitades.Servico;
import org.springframework.stereotype.Component;

@Component
public class AtualizadorServico {
    public Servico atualizarServico(Servico servico, Servico setServico){
        servico.setNome(setServico.getNome());
        servico.setDescricao(setServico.getDescricao());
        servico.setValor(setServico.getValor());
        return servico;
    }
}
