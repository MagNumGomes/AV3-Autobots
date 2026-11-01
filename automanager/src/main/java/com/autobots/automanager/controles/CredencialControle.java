package com.autobots.automanager.controles;

import com.autobots.automanager.DTO.CredencialDto;
import com.autobots.automanager.entitades.Credencial;
import com.autobots.automanager.servicos.CredencialServico;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/credenciais")
public class CredencialControle {

    @Autowired
    private CredencialServico credencialServico;

    @PostMapping("/cadastrar/{idUsuario}/{tipoCredencial}")
    public ResponseEntity<?> cadastrarCredencial(@PathVariable Long idUsuario, @PathVariable String tipoCredencial, @RequestBody CredencialDto credencial) {
        if (tipoCredencial.equalsIgnoreCase("usuariosenha")) {
            try {
                credencialServico.cadastrarCredencialUsuarioSenha(idUsuario, credencial);
                return ResponseEntity.created(null).build();
            } catch (IllegalArgumentException e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
            }
        }
        else if (tipoCredencial.equalsIgnoreCase("codigobarra")) {
            try {
                credencialServico.cadastrarCredencialCodigoBarra(idUsuario, credencial);
                return ResponseEntity.created(null).build();
            } catch (IllegalArgumentException e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
            }
        }
        else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Tipo de credencial inválido.");
        }
    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<?> deletarCredencial(@PathVariable Long id) {
        try {
            credencialServico.deletarCredencial(id);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<?> atualizarCredencial(@PathVariable Long id, @RequestBody CredencialDto credencial) {
        try {
            credencialServico.atualizarCredencial(id, credencial);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/listar")
    public ResponseEntity<List<Credencial>> listarCredenciais() {
        List<Credencial> credenciais = credencialServico.listarCredenciais();
        if (credenciais.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(credenciais);
    }

    @GetMapping("/visualizar/{id}")
    public ResponseEntity<?> visualizarCredencial(@PathVariable Long id) {
        Credencial credencial = credencialServico.visualizarCredencial(id);
        if (credencial == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(credencial);
    }

    @GetMapping("/usuario/{idUsuario}/listar")
    public ResponseEntity<?> listarCredenciaisUsuario(@PathVariable Long idUsuario) {
        try {
            List<Credencial> credenciais = credencialServico.listarCredenciaisUsuario(idUsuario);
            if (credenciais.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(credenciais);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
