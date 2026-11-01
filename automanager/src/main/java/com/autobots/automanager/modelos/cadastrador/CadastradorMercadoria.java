package com.autobots.automanager.modelos.cadastrador;

import com.autobots.automanager.DTO.MercadoriaDto;
import com.autobots.automanager.entitades.Mercadoria;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class CadastradorMercadoria {
    public Mercadoria cadastrarMercadoria(Mercadoria mercadoria){
        Mercadoria setMercadoria = new Mercadoria();
        setMercadoria.setNome(mercadoria.getNome());
        setMercadoria.setValor(mercadoria.getValor());
        setMercadoria.setQuantidade(mercadoria.getQuantidade());
        setMercadoria.setCadastro(new Date());
        setMercadoria.setFabricao(new Date());
        setMercadoria.setValidade(new Date());
        if (mercadoria.getDescricao() != null) {
            setMercadoria.setDescricao(mercadoria.getDescricao());
        }

        return setMercadoria;
    }

    public Mercadoria cadastrarMercadoria (MercadoriaDto mercadoria) {
        Mercadoria setMercadoria = new Mercadoria();
        setMercadoria.setValidade(new Date());
        setMercadoria.setFabricao(new Date());
        setMercadoria.setCadastro(new Date());
        setMercadoria.setNome(mercadoria.nome());
        setMercadoria.setQuantidade(mercadoria.quantidade());
        setMercadoria.setValor(mercadoria.valor());
        if (mercadoria.descricao().isPresent()) {
            setMercadoria.setDescricao(mercadoria.descricao().get());
        }

        return setMercadoria;
    }
}