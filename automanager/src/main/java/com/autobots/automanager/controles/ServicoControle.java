package com.autobots.automanager.controles;

import com.autobots.automanager.entitades.Servico;
import com.autobots.automanager.servicos.ServicoServico;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/servicos")
public class ServicoControle {

    @Autowired
    private ServicoServico servicoServico;

    @PostMapping("/cadastrar")
    public ResponseEntity<?> cadastrarServico(@RequestBody Servico servico) {
        try {
            servicoServico.cadastrarServico(servico);
            return ResponseEntity.created(null).build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/cadastrar/empresa/{idEmpresa}")
    public ResponseEntity<?> cadastrarServicoEmpresa(@RequestBody Servico servico, @PathVariable Long idEmpresa) {
        try {
            servicoServico.cadastrarServicoEmpresa(servico, idEmpresa);
            return ResponseEntity.created(null).build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/cadastrar/venda/{idVenda}")
    public ResponseEntity<?> cadastrarServicoVenda(@RequestBody Servico servico, @PathVariable Long idVenda) {
        try {
            servicoServico.cadastrarServicoVenda(servico, idVenda);
            return ResponseEntity.created(null).build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<?> deletarServico(@PathVariable Long id) {
        try {
            servicoServico.deletarServico(id);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<?> atualizarServico(@PathVariable Long id, @RequestBody Servico servico) {
        try {
            servicoServico.atualizarServico(id, servico);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/listar")
    public ResponseEntity<List<Servico>> listarServicos() {
        List<Servico> servicos =  servicoServico.listarServicos();
        if (servicos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(servicos);
    }

    @GetMapping("/visualizar/{id}")
    public ResponseEntity<Servico> visualizarServico(@PathVariable Long id) {
        Servico servico = servicoServico.visualizarServico(id);
        if (servico == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(servico);
    }

    @GetMapping("/visualizar/venda/{idVenda}")
    public ResponseEntity<?> visualizarServicosVenda(@PathVariable Long idVenda) {
        try {
            List<Servico> servicos = servicoServico.visualizarServicosVenda(idVenda);
            if (servicos.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(servicos);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/visualizar/empresa/{idEmpresa}")
    public ResponseEntity<?> visualizarServicosEmpresa(@PathVariable Long idEmpresa) {
        try {
            List<Servico> servicos = servicoServico.visualizarServicosEmpresa(idEmpresa);
            if (servicos.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(servicos);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/vincular/{idServico}/venda/{idVenda}")
    public ResponseEntity<?> vincularServicoVenda(@PathVariable Long idServico, @PathVariable Long idVenda) {
        try {
            servicoServico.vincularServicoVenda(idServico, idVenda);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/desvincular/{idServico}/venda/{idVenda}")
    public ResponseEntity<?> desvincularServicoVenda(@PathVariable Long idServico, @PathVariable Long idVenda) {
        try {
            servicoServico.desvincularServicoVenda(idServico, idVenda);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/vincular/{idServico}/empresa/{idEmpresa}")
    public ResponseEntity<?> vincularServicoEmpresa(@PathVariable Long idServico, @PathVariable Long idEmpresa) {
        try {
            servicoServico.vincularServicoEmpresa(idServico, idEmpresa);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/desvincular/{idServico}/empresa/{idEmpresa}")
    public ResponseEntity<?> desvincularServicoEmpresa(@PathVariable Long idServico, @PathVariable Long idEmpresa) {
        try {
            servicoServico.desvincularServicoEmpresa(idServico, idEmpresa);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
