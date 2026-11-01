package com.autobots.automanager.modelos.atualizador;

import com.autobots.automanager.entitades.Mercadoria;
import org.springframework.stereotype.Component;

@Component
public class AtualizadorMercadoria {
    public Mercadoria atualizarMercadoria(Mercadoria mercadoria, Mercadoria setMercadoria){
        mercadoria.setNome(setMercadoria.getNome());
        mercadoria.setDescricao(setMercadoria.getDescricao());
        mercadoria.setValor(setMercadoria.getValor());
        mercadoria.setQuantidade(setMercadoria.getQuantidade());
        return mercadoria;
    }
}
