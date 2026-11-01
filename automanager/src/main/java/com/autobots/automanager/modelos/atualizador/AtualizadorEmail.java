package com.autobots.automanager.modelos.atualizador;

import com.autobots.automanager.entitades.Email;
import org.springframework.stereotype.Component;

@Component
public class AtualizadorEmail {
    public Email atualizarEmail(Email email,Email setEmail){
        email.setEndereco(setEmail.getEndereco());
        return email;
    }
}
