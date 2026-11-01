package com.autobots.automanager.controles;

import com.autobots.automanager.DTO.AtualizarVendaDto;
import com.autobots.automanager.entitades.Venda;
import com.autobots.automanager.servicos.VendaServico;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vendas")
public class VendaControle {

    @Autowired
    private VendaServico vendaServico;

    @PostMapping("/cadastrar")
    public ResponseEntity<?> cadastrarVenda(@RequestBody Venda venda) {
        try {
            vendaServico.cadastrarVenda(venda);
            return ResponseEntity.created(null).build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/cadastrar/empresa/{idEmpresa}")
    public ResponseEntity<?> cadastrarVendaEmpresa(@PathVariable Long idEmpresa, @RequestBody Venda venda) {
        try {
            vendaServico.cadastrarVendaEmpresa(idEmpresa, venda);
            return ResponseEntity.created(null).build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/cadastrar/usuario/{idUsuario}/{tipoUsuario}")
    public ResponseEntity<?> cadastrarVendaUsuario(@PathVariable Long idUsuario, @RequestBody Venda venda, @PathVariable String tipoUsuario) {
        try {
            vendaServico.cadastrarVendaUsuario(idUsuario, venda, tipoUsuario);
            return ResponseEntity.created(null).build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<?> deletarVenda(@PathVariable Long id) {
        try {
            vendaServico.deletarVenda(id);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<?> atualizarVenda(@PathVariable Long id, @RequestBody AtualizarVendaDto venda) {
        try {
            vendaServico.atualizarVenda(id, venda);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/listar")
    public ResponseEntity<List<Venda>> listarVendas() {
        List<Venda> vendas = vendaServico.listarVendas();
        if (vendas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(vendas);
    }

    @GetMapping("/visualizar/{id}")
    public ResponseEntity<Venda> visualizarVenda(@PathVariable Long id) {
        Venda venda = vendaServico.visualizarVenda(id);
        if (venda == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(venda);
    }

    @GetMapping("/visualizar/empresa/{idEmpresa}")
    public ResponseEntity<?> listarVendasEmpresa(@PathVariable Long idEmpresa) {
        try {
            List<Venda> vendas = vendaServico.visualizarVendasEmpresa(idEmpresa);
            if (vendas.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(vendas);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/visualizar/usuario/{idUsuario}")
    public ResponseEntity<?> listarVendasUsuario(@PathVariable Long idUsuario) {
        try {
            List<Venda> vendas = vendaServico.visualizarVendasUsuario(idUsuario);
            if (vendas.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(vendas);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/vincular/{idVenda}/usuario/{idUsuario}/{tipoUsuario}")
    public ResponseEntity<?> vincularVendaUsuario(@PathVariable Long idVenda, @PathVariable Long idUsuario, @PathVariable String tipoUsuario) {
        try {
            vendaServico.vincularVendaUsuario(idVenda, idUsuario, tipoUsuario);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/vincular/{idVenda}/empresa/{idEmpresa}")
    public ResponseEntity<?> vincularVendaEmpresa(@PathVariable Long idVenda, @PathVariable Long idEmpresa) {
        try {
            vendaServico.vincularVendaEmpresa(idVenda, idEmpresa);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/desvincular/{idVenda}/usuario/{idUsuario}/{tipoUsuario}")
    public ResponseEntity<?> desvincularVendaUsuario(@PathVariable Long idVenda, @PathVariable Long idUsuario, @PathVariable String tipoUsuario) {
        try {
            vendaServico.desvincularVendaUsuario(idVenda, idUsuario, tipoUsuario);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/desvincular/{idVenda}/empresa/{idEmpresa}")
    public ResponseEntity<?> desvincularVendaEmpresa(@PathVariable Long idVenda, @PathVariable Long idEmpresa) {
        try {
            vendaServico.desvincularVendaEmpresa(idVenda, idEmpresa);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
