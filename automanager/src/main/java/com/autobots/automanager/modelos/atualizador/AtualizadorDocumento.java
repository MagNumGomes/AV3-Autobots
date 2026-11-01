package com.autobots.automanager.modelos.atualizador;

import com.autobots.automanager.entitades.Documento;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class AtualizadorDocumento {
    public Documento atualizarDocumento(Documento documento,Documento setDocumento) {
        documento.setTipo(setDocumento.getTipo());
        documento.setNumero(setDocumento.getNumero());
        documento.setDataEmissao(new Date());
        return documento;
    }
}
