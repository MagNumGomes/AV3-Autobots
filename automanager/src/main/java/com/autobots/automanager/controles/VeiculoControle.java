package com.autobots.automanager.controles;

import com.autobots.automanager.entitades.Veiculo;
import com.autobots.automanager.servicos.VeiculoServico;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/veiculos")
public class VeiculoControle {

    @Autowired
    private VeiculoServico veiculoServico;

    @PostMapping("/cadastrar")
    public ResponseEntity<?> cadastrarVeiculo(@RequestBody Veiculo veiculo) {
        try {
            veiculoServico.cadastrarVeiculo(veiculo);
            return ResponseEntity.created(null).build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<?> excluirVeiculo(@PathVariable Long id) {
        try {
            ResponseEntity<?> resposta = veiculoServico.excluirVeiculo(id);
            return resposta;
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<?> atualizarVeiculo(@PathVariable Long id, @RequestBody Veiculo veiculo) {
        try {
            return veiculoServico.atualizarVeiculo(id, veiculo);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/listar")
    public ResponseEntity<List<Veiculo>> listarVeiculos() {
        List<Veiculo> veiculos =  veiculoServico.listarVeiculos();
        if (veiculos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(veiculos);
    }

    @GetMapping("/visualizar/{id}")
    public ResponseEntity<Veiculo> visualizarVeiculo(@PathVariable Long id) {
        Veiculo veiculo = veiculoServico.visualizarVeiculo(id);
        if (veiculo == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(veiculo);
    }

    @GetMapping("/usuario/{idUsuario}/listar")
    public ResponseEntity<?> listarVeiculosUsuario(@PathVariable Long idUsuario) {
        try {
            List<Veiculo> veiculos = veiculoServico.listarVeiculosUsuario(idUsuario);
            if (veiculos.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(veiculos);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/vincular/{idVeiculo}/usuario/{idUsuario}")
    public ResponseEntity<?> vincularVeiculoUsuario(@PathVariable Long idVeiculo, @PathVariable Long idUsuario) {
        try {
            return veiculoServico.vincularVeiculoUsuario(idVeiculo, idUsuario);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/desvincular/{idVeiculo}/usuario/{idUsuario}")
    public ResponseEntity<?> desvincularVeiculoUsuario(@PathVariable Long idVeiculo, @PathVariable Long idUsuario) {
        try {
            return veiculoServico.desvincularVeiculoUsuario(idVeiculo, idUsuario);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
