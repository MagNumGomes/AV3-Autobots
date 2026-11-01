package com.autobots.automanager.controles;

import com.autobots.automanager.DTO.MercadoriaDto;
import com.autobots.automanager.entitades.Mercadoria;
import com.autobots.automanager.servicos.MercadoriaServico;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/mercadorias")
public class MercadoriaControle {

    @Autowired
    private MercadoriaServico mercadoriaServico;

    @PostMapping("/cadastrar")
    public ResponseEntity<?> cadastrarMercadoria(@RequestBody MercadoriaDto mercadoria) {
        try {
            mercadoriaServico.cadastrarMercadoria(mercadoria);
            return ResponseEntity.created(null).build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/cadastrar/empresa/{idEmpresa}")
    public ResponseEntity<?> cadastrarMercadoriaEmpresa(@RequestBody MercadoriaDto mercadoria, @PathVariable Long idEmpresa) {
        try {
            mercadoriaServico.cadastrarMercadoriaEmpresa(mercadoria, idEmpresa);
            return ResponseEntity.created(null).build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/cadastrar/usuario/{idUsuario}")
    public ResponseEntity<?> cadastrarMercadoriaUsuario(@RequestBody MercadoriaDto mercadoria, @PathVariable Long idUsuario) {
        try {
            mercadoriaServico.cadastrarMercadoriaUsuario(mercadoria, idUsuario);
            return ResponseEntity.created(null).build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<?> deletarMercadoria(@PathVariable Long id) {
        try {
            mercadoriaServico.deletarMercadoria(id);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<?> atualizarMercadoria(@PathVariable Long id, @RequestBody Mercadoria mercadoria) {
        try {
            mercadoriaServico.atualizarMercadoria(id, mercadoria);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/listar")
    public ResponseEntity<List<Mercadoria>> listarMercadorias() {
        List<Mercadoria> mercadorias = mercadoriaServico.listarMercadorias();
        if (mercadorias.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(mercadorias);
    }

    @GetMapping("/visualizar/{id}")
    public ResponseEntity<Mercadoria> visualizarMercadoria(@PathVariable Long id) {
        Mercadoria mercadoria = mercadoriaServico.visualizarMercadoria(id);
        if (mercadoria == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(mercadoria);
    }

    @GetMapping("/visualizar/empresa/{idEmpresa}")
    public ResponseEntity<?> visualizarMercadoriaEmpresa(@PathVariable Long idEmpresa) {
        try {
            List<Mercadoria> mercadorias = mercadoriaServico.visualizarMercadoriaEmpresa(idEmpresa);
            if (mercadorias.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(mercadorias);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/visualizar/usuario/{idUsuario}")
    public ResponseEntity<?> visualizarMercadoriaUsuario(@PathVariable Long idUsuario) {
        try {
            List<Mercadoria> mercadorias = mercadoriaServico.visualizarMercadoriaUsuario(idUsuario);
            if (mercadorias.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(mercadorias);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/vincular/{idMercadoria}/empresa/{idEmpresa}")
    public ResponseEntity<?> vincularMercadoriaEmpresa(@PathVariable Long idMercadoria, @PathVariable Long idEmpresa) {
        try {
            mercadoriaServico.vincularMercadoriaEmpresa(idMercadoria, idEmpresa);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/vincular/{idMercadoria}/usuario/{idUsuario}")
    public ResponseEntity<?> vincularMercadoriaUsuario(@PathVariable Long idMercadoria, @PathVariable Long idUsuario) {
        try {
            mercadoriaServico.vincularMercadoriaUsuario(idMercadoria, idUsuario);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/desvincular/{idMercadoria}/empresa/{idEmpresa}")
    public ResponseEntity<?> desvincularMercadoriaEmpresa(@PathVariable Long idMercadoria, @PathVariable Long idEmpresa) {
        try {
            mercadoriaServico.desvincularMercadoriaEmpresa(idMercadoria, idEmpresa);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/desvincular/{idMercadoria}/usuario/{idUsuario}")
    public ResponseEntity<?> desvincularMercadoriaUsuario(@PathVariable Long idMercadoria, @PathVariable Long idUsuario) {
        try {
            mercadoriaServico.desvincularMercadoriaUsuario(idMercadoria, idUsuario);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

}
