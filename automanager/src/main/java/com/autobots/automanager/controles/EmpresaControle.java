package com.autobots.automanager.controles;

import com.autobots.automanager.entitades.Empresa;
import com.autobots.automanager.modelos.cadastrador.CadastradorEmpresa;
import com.autobots.automanager.servicos.EmpresaServico;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/empresas")
public class EmpresaControle {
    @Autowired
    private EmpresaServico empresaServico;

    @PostMapping("/cadastrar")
    public ResponseEntity<?> cadastrarEmpresa(@RequestBody Empresa empresa) {
        try {
            empresaServico.cadastrarEmpresa(empresa);
            return ResponseEntity.created(null).build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<?> deletarEmpresa(@PathVariable Long id) {
        try {
            empresaServico.deletarEmpresa(id);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<?> atualizarEmpresa(@PathVariable Long id, @RequestBody Empresa empresa) {
        ResponseEntity<?> resposta = empresaServico.atualizarEmpresa(id, empresa);
        return resposta;
    }

    @GetMapping("/listar")
    public ResponseEntity<List<Empresa>> listarEmpresas() {
        List<Empresa> empresas = empresaServico.listarEmpresas();
        if (empresas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(empresas);
    }

    @GetMapping("/visualizar/{id}")
    public ResponseEntity<Empresa> visualizarEmpresa(@PathVariable Long id) {
        Empresa empresa = empresaServico.visualizarEmpresa(id);
        if (empresa == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(empresa);
    }
}
