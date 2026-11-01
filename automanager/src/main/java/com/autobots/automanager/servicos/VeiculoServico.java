package com.autobots.automanager.servicos;

import com.autobots.automanager.entitades.Usuario;
import com.autobots.automanager.entitades.Veiculo;
import com.autobots.automanager.entitades.Venda;
import com.autobots.automanager.modelos.adicionadores.AdicionadorLinkVeiculo;
import com.autobots.automanager.modelos.cadastrador.CadastradorVeiculo;
import com.autobots.automanager.repositorios.RepositorioUsuario;
import com.autobots.automanager.repositorios.RepositorioVeiculo;
import com.autobots.automanager.repositorios.RepositorioVenda;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class VeiculoServico {

    @Autowired
    private RepositorioVeiculo repositorioVeiculo;

    @Autowired
    private RepositorioUsuario repositorioUsuario;

    @Autowired
    private CadastradorVeiculo cadastradorVeiculo;

    @Autowired
    private RepositorioVenda repositorioVenda;

    @Autowired
    private AdicionadorLinkVeiculo adicionadorLinkVeiculo;

    public ResponseEntity<?> cadastrarVeiculo(Veiculo veiculo) {
        cadastradorVeiculo.cadastrarVeiculo(veiculo);
        return ResponseEntity.created(null).build();
    }

    public ResponseEntity<?> excluirVeiculo(Long id) {
        Veiculo veiculo = repositorioVeiculo.findById(id).orElse(null);
        if (veiculo != null) {
            if (veiculo.getProprietario() != null) {
                veiculo.getProprietario().getVeiculos().removeIf(v -> v.getId().equals(id));
                repositorioUsuario.save(veiculo.getProprietario());
            }
            for (Usuario usuario : repositorioUsuario.findAll()) {
                if (usuario.getVeiculos().removeIf(v -> v.getId().equals(id))) {
                    repositorioUsuario.save(usuario);
                }
            }

            for (Venda venda : repositorioVenda.findAll()) {
                if (venda.getVeiculo() != null && venda.getVeiculo().getId().equals(id)) {
                    venda.setVeiculo(null);
                    repositorioVenda.save(venda);
                }
            }

            veiculo.setProprietario(null);
            veiculo.getVendas().clear();
            repositorioVeiculo.save(veiculo);
            repositorioVeiculo.delete(veiculo);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public ResponseEntity<?> atualizarVeiculo(Long veiculoId, Veiculo veiculo) {
        Veiculo veiculoAtualizado = repositorioVeiculo.findById(veiculoId).orElse(null);
        if (veiculoAtualizado != null) {
            if (veiculo.getModelo() != null) {
                veiculoAtualizado.setModelo(veiculo.getModelo());
            }
            if (veiculo.getPlaca() != null) {
                veiculoAtualizado.setPlaca(veiculo.getPlaca());
            }
            if (veiculo.getTipo() != null) {
                veiculoAtualizado.setTipo(veiculo.getTipo());
            }
            if (veiculo.getProprietario() != null) {
                if (veiculo.getProprietario().getId() != null) {
                    Usuario proprietario = repositorioUsuario.findById(veiculo.getProprietario().getId())
                            .orElseThrow(() -> new IllegalArgumentException("Proprietário não encontrado"));
                    veiculoAtualizado.setProprietario(proprietario);
                } else {
                    veiculoAtualizado.setProprietario(veiculo.getProprietario());
                }
            }
            if (veiculo.getVendas() != null && !veiculo.getVendas().isEmpty()) {
                veiculoAtualizado.getVendas().addAll(veiculo.getVendas());
            }
            repositorioVeiculo.save(veiculoAtualizado);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public List<Veiculo> listarVeiculos() {
        List<Veiculo> veiculos = repositorioVeiculo.findAll();
        adicionadorLinkVeiculo.adicionarLink(veiculos);
        return veiculos;
    }

    public Veiculo visualizarVeiculo(Long id) {
        Veiculo veiculo = repositorioVeiculo.findById(id).orElse(null);
        if (veiculo != null) {
            adicionadorLinkVeiculo.adicionarLink(veiculo);
        }
        return veiculo;
    }

    public List<Veiculo> listarVeiculosUsuario(Long idUsuario) {
        Usuario usuario = repositorioUsuario.findById(idUsuario).orElse(null);
        if (usuario == null) {
            throw new IllegalArgumentException("Usuário não encontrado.");
        }
        Set<Veiculo> veiculos = usuario.getVeiculos();
        List<Veiculo> veiculosLista = new ArrayList<>(veiculos);
        adicionadorLinkVeiculo.adicionarLink(veiculosLista);
        return veiculosLista;
    }

    public ResponseEntity<?> vincularVeiculoUsuario(Long veiculoId, Long usuarioId) {
        Veiculo veiculo = repositorioVeiculo.findById(veiculoId).orElse(null);
        if (veiculo != null) {
            Usuario usuario = repositorioUsuario.findById(usuarioId).orElse(null);
            if (usuario == null) {
                return ResponseEntity.notFound().build();
            }
            veiculo.setProprietario(usuario);
            repositorioVeiculo.save(veiculo);
            usuario.getVeiculos().add(veiculo);
            repositorioUsuario.save(usuario);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public ResponseEntity<?> desvincularVeiculoUsuario(Long veiculoId, Long usuarioId) {
        Veiculo veiculo = repositorioVeiculo.findById(veiculoId).orElse(null);
        if (veiculo != null) {
            Usuario usuario = repositorioUsuario.findById(usuarioId).orElse(null);
            if (usuario == null) {
                return ResponseEntity.notFound().build();
            }
            veiculo.setProprietario(null);
            repositorioVeiculo.save(veiculo);
            usuario.getVeiculos().removeIf(v -> v.getId().equals(veiculoId));
            repositorioUsuario.save(usuario);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }


}
