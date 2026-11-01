package com.autobots.automanager.servicos;

import com.autobots.automanager.DTO.MercadoriaDto;
import com.autobots.automanager.entitades.Empresa;
import com.autobots.automanager.entitades.Mercadoria;
import com.autobots.automanager.entitades.Usuario;
import com.autobots.automanager.entitades.Venda;
import com.autobots.automanager.modelos.adicionadores.AdicionadorLinkMercadoria;
import com.autobots.automanager.modelos.cadastrador.CadastradorMercadoria;
import com.autobots.automanager.repositorios.RepositorioEmpresa;
import com.autobots.automanager.repositorios.RepositorioMercadoria;
import com.autobots.automanager.repositorios.RepositorioUsuario;
import com.autobots.automanager.repositorios.RepositorioVenda;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class MercadoriaServico {

    @Autowired
    private RepositorioMercadoria repositorioMercadoria;

    @Autowired
    private CadastradorMercadoria cadastradorMercadoria;

    @Autowired
    private AdicionadorLinkMercadoria adicionadorLinkMercadoria;

    @Autowired
    private RepositorioUsuario repositorioUsuario;

    @Autowired
    private RepositorioVenda repositorioVenda;

    @Autowired
    private RepositorioEmpresa repositorioEmpresa;


    public void cadastrarMercadoria(MercadoriaDto mercadoria) {
        Mercadoria mercadoriaCadastrada = cadastradorMercadoria.cadastrarMercadoria(mercadoria);
        repositorioMercadoria.save(mercadoriaCadastrada);
    }

    public void cadastrarMercadoriaEmpresa(MercadoriaDto mercadoria, Long idEmpresa) {
        Empresa empresa = repositorioEmpresa.findById(idEmpresa).orElse(null);
        if (empresa == null) {
            throw new IllegalArgumentException("Empresa não encontrada");
        }
        Mercadoria mercadoriaCadastrada = cadastradorMercadoria.cadastrarMercadoria(mercadoria);
        repositorioMercadoria.save(mercadoriaCadastrada);
        empresa.getMercadorias().add(mercadoriaCadastrada);
        repositorioEmpresa.save(empresa);
    }

    public void cadastrarMercadoriaUsuario(MercadoriaDto mercadoria, Long idUsuario) {
        Usuario usuario  = repositorioUsuario.findById(idUsuario).orElse(null);
        if (usuario == null) {
            throw new IllegalArgumentException("Usuario não encontrado");
        }
        Mercadoria mercadoriaCadastrada = cadastradorMercadoria.cadastrarMercadoria(mercadoria);
        repositorioMercadoria.save(mercadoriaCadastrada);
        usuario.getMercadorias().add(mercadoriaCadastrada);
        repositorioUsuario.save(usuario);
    }

    public void deletarMercadoria(Long id) {
        Mercadoria mercadoria = repositorioMercadoria.findById(id).orElse(null);
        if (mercadoria == null) {
            throw new IllegalArgumentException("Mercadoria não encontrada");
        }

        List<Usuario> usuarios = repositorioUsuario.findAll();
        for (Usuario usuario : usuarios) {
            if (usuario.getMercadorias().removeIf(m -> m.getId().equals(id))) {
                repositorioUsuario.save(usuario);
            }
        }

        List<Empresa> empresas = repositorioEmpresa.findAll();
        for (Empresa empresa : empresas) {
            if (empresa.getMercadorias().removeIf(m -> m.getId().equals(id))) {
                repositorioEmpresa.save(empresa);
            }
        }

        List<Venda> vendas = repositorioVenda.findAll();
        for (Venda venda : vendas) {
            if (venda.getMercadorias().removeIf(m -> m.getId().equals(id))) {
                repositorioVenda.save(venda);
            }
        }

        repositorioMercadoria.deleteById(id);

    }

    public void atualizarMercadoria(Long id, Mercadoria mercadoria) {
        Mercadoria mercadoriaAtual = repositorioMercadoria.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Mercadoria não encontrada"));
        if (mercadoria.getValidade() != null) {
            mercadoriaAtual.setValidade(mercadoria.getValidade());
        }
        if (mercadoria.getFabricao() != null) {
            mercadoriaAtual.setFabricao(mercadoria.getFabricao());
        }
        if (mercadoria.getCadastro() != null) {
            mercadoriaAtual.setCadastro(mercadoria.getCadastro());
        }
        if (mercadoria.getNome() != null) {
            mercadoriaAtual.setNome(mercadoria.getNome());
        }
        if (mercadoria.getQuantidade() != 0) {
            mercadoriaAtual.setQuantidade(mercadoria.getQuantidade());
        }
        if (mercadoria.getValor() != 0) {
            mercadoriaAtual.setValor(mercadoria.getValor());
        }
        if (mercadoria.getDescricao() != null) {
            mercadoriaAtual.setDescricao(mercadoria.getDescricao());
        }

        repositorioMercadoria.save(mercadoriaAtual);
    }

    public List<Mercadoria> listarMercadorias() {
        List<Mercadoria> mercadorias = repositorioMercadoria.findAll();
        adicionadorLinkMercadoria.adicionarLink(mercadorias);
        return mercadorias;
    }

    public Mercadoria visualizarMercadoria(Long id) {
        Mercadoria mercadoria = repositorioMercadoria.findById(id).orElse(null);
        if (mercadoria != null) {
            adicionadorLinkMercadoria.adicionarLink(mercadoria);
        }
        return mercadoria;
    }

    public List<Mercadoria> visualizarMercadoriaEmpresa(Long idEmpresa) {
        Empresa empresa = repositorioEmpresa.findById(idEmpresa).orElse(null);
        if (empresa == null) {
            throw new IllegalArgumentException("Empresa não encontrada");
        }
        Set<Mercadoria> mercadorias = empresa.getMercadorias();
        List<Mercadoria> mercadoriasLista = new ArrayList<>(mercadorias);
        adicionadorLinkMercadoria.adicionarLink(mercadoriasLista);
        return mercadoriasLista;
    }

    public List<Mercadoria> visualizarMercadoriaUsuario(Long idUsuario) {
        Usuario usuario = repositorioUsuario.findById(idUsuario).orElse(null);
        if (usuario == null) {
            throw new IllegalArgumentException("Usuario não encontrado");
        }
        Set<Mercadoria> mercadorias = usuario.getMercadorias();
        List<Mercadoria> mercadoriasLista = new ArrayList<>(mercadorias);
        adicionadorLinkMercadoria.adicionarLink(mercadoriasLista);
        return mercadoriasLista;
    }

    public void vincularMercadoriaEmpresa(Long idMercadoria, Long idEmpresa) {
        Mercadoria mercadoria = repositorioMercadoria.findById(idMercadoria).orElse(null);
        Empresa empresa = repositorioEmpresa.findById(idEmpresa).orElse(null);
        if (mercadoria == null) {
            throw new IllegalArgumentException("Mercadoria não encontrada");
        }
        if (empresa == null) {
            throw new IllegalArgumentException("Empresa não encontrada");
        }
        empresa.getMercadorias().add(mercadoria);
        repositorioEmpresa.save(empresa);
    }

    public void vincularMercadoriaUsuario(Long idMercadoria, Long idUsuario) {
        Mercadoria mercadoria = repositorioMercadoria.findById(idMercadoria).orElse(null);
        Usuario usuario = repositorioUsuario.findById(idUsuario).orElse(null);
        if (mercadoria == null) {
            throw new IllegalArgumentException("Mercadoria não encontrada");
        }
        if (usuario == null) {
            throw new IllegalArgumentException("Usuario não encontrado");
        }
        usuario.getMercadorias().add(mercadoria);
        repositorioUsuario.save(usuario);
    }

    public void desvincularMercadoriaEmpresa(Long idMercadoria, Long idEmpresa) {
        Mercadoria mercadoria = repositorioMercadoria.findById(idMercadoria).orElse(null);
        Empresa empresa = repositorioEmpresa.findById(idEmpresa).orElse(null);
        if (mercadoria == null) {
            throw new IllegalArgumentException("Mercadoria não encontrada");
        }
        if (empresa == null) {
            throw new IllegalArgumentException("Empresa não encontrada");
        }
        empresa.getMercadorias().removeIf(m -> m.getId().equals(idMercadoria));
        repositorioEmpresa.save(empresa);
    }

    public void desvincularMercadoriaUsuario(Long idMercadoria, Long idUsuario) {
        Mercadoria mercadoria = repositorioMercadoria.findById(idMercadoria).orElse(null);
        Usuario usuario = repositorioUsuario.findById(idUsuario).orElse(null);
        if (mercadoria == null) {
            throw new IllegalArgumentException("Mercadoria não encontrada");
        }
        if (usuario == null) {
            throw new IllegalArgumentException("Usuario não encontrado");
        }
        usuario.getMercadorias().removeIf(m -> m.getId().equals(idMercadoria));
        repositorioUsuario.save(usuario);
    }
}
