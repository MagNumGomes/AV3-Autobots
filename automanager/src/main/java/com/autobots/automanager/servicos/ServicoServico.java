package com.autobots.automanager.servicos;

import com.autobots.automanager.entitades.Empresa;
import com.autobots.automanager.entitades.Servico;
import com.autobots.automanager.entitades.Venda;
import com.autobots.automanager.modelos.adicionadores.AdicionadorLinkServico;
import com.autobots.automanager.modelos.cadastrador.CadastradorVenda;
import com.autobots.automanager.repositorios.RepositorioEmpresa;
import com.autobots.automanager.repositorios.RepositorioServico;
import com.autobots.automanager.repositorios.RepositorioVenda;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class ServicoServico {

    @Autowired
    private RepositorioServico repositorioServico;

    @Autowired
    private AdicionadorLinkServico adicionadorLinkServico;

    @Autowired
    private RepositorioVenda repositorioVenda;

    @Autowired
    private RepositorioEmpresa repositorioEmpresa;

    @Autowired
    private CadastradorVenda cadastradorVenda;

    public void cadastrarServico(Servico servico) {
        repositorioServico.save(servico);
    }

    public void cadastrarServicoEmpresa(Servico servico, Long idEmpresa) {
        Empresa empresa = repositorioEmpresa.findById(idEmpresa).orElse(null);
        if (empresa != null) {
            repositorioServico.save(servico);
            empresa.getServicos().add(servico);
            repositorioEmpresa.save(empresa);
        }
        else {
            throw new IllegalArgumentException("Empresa não encontrada");
        }
    }

    public void cadastrarServicoVenda(Servico servico, Long idVenda) {
        Venda venda = repositorioVenda.findById(idVenda).orElse(null);
        if (venda != null) {
            repositorioServico.save(servico);
            venda.getServicos().add(servico);
            repositorioVenda.save(venda);
        }
        else {
            throw new IllegalArgumentException("Venda não encontrada");
        }
    }

    public void deletarServico(Long id) {
        Servico servico = repositorioServico.findById(id).orElse(null);
        if (servico != null) {

            List<Empresa> empresas = repositorioEmpresa.findAll();
            for (Empresa empresa : empresas) {
                if (empresa.getServicos().removeIf(s -> s.getId().equals(id))) {
                    repositorioEmpresa.save(empresa);
                }
            }

            List<Venda> vendas = repositorioVenda.findAll();
            for (Venda venda : vendas) {
                if (venda.getServicos().removeIf(s -> s.getId().equals(id))) {
                    repositorioVenda.save(venda);
                }
            }

            repositorioServico.deleteById(id);
        }
        else {
            throw new IllegalArgumentException("Serviço não encontrado");
        }
    }

    public void atualizarServico(Long servicoId, Servico servico) {
        Servico servicoAtualizado = repositorioServico.findById(servicoId).orElse(null);
        if (servicoAtualizado != null) {
            if (servico.getNome() != null) {
                servicoAtualizado.setNome(servico.getNome());
            }
            if (servico.getDescricao() != null) {
                servicoAtualizado.setDescricao(servico.getDescricao());
            }
            if (servico.getValor() != 0) {
                servicoAtualizado.setValor(servico.getValor());
            }
            repositorioServico.save(servicoAtualizado);
        }
        else {
            throw new IllegalArgumentException("Serviço não encontrado");
        }
    }

    public List<Servico> listarServicos() {
        List<Servico> servicos = repositorioServico.findAll();
        adicionadorLinkServico.adicionarLink(servicos);
        return servicos;
    }

    public Servico visualizarServico(Long id) {
        Servico servico = repositorioServico.findById(id).orElse(null);
        if (servico != null) {
            adicionadorLinkServico.adicionarLink(servico);
        }
        return servico;
    }

    public List<Servico> visualizarServicosVenda(Long idVenda) {
        Venda venda = repositorioVenda.findById(idVenda).orElse(null);
        if (venda == null) {
            throw new IllegalArgumentException("Venda não encontrada");
        }
        Set<Servico> servicos = venda.getServicos();
        List<Servico> servicosLista = new ArrayList<>(servicos);
        adicionadorLinkServico.adicionarLink(servicosLista);
        return servicosLista;
    }

    public List<Servico> visualizarServicosEmpresa(Long idEmpresa) {
        Empresa empresa = repositorioEmpresa.findById(idEmpresa).orElse(null);
        if (empresa == null) {
            throw new IllegalArgumentException("Empresa não encontrada");
        }
        Set<Servico> servicos = empresa.getServicos();
        List<Servico> servicosLista = new ArrayList<>(servicos);
        adicionadorLinkServico.adicionarLink(servicosLista);
        return servicosLista;
    }

    public void vincularServicoVenda(Long idServico, Long idVenda) {
        Servico servico = repositorioServico.findById(idServico).orElse(null);
        Venda venda = repositorioVenda.findById(idVenda).orElse(null);
        if (servico != null && venda != null) {
            venda.getServicos().add(servico);
            repositorioVenda.save(venda);
        }
        else {
            throw new IllegalArgumentException("Serviço ou venda não encontrados");
        }
    }

    public void desvincularServicoVenda(Long idServico, Long idVenda) {
        Servico servico = repositorioServico.findById(idServico).orElse(null);
        Venda venda = repositorioVenda.findById(idVenda).orElse(null);
        if (servico != null && venda != null) {
            venda.getServicos().removeIf(s -> s.getId().equals(idServico));
            repositorioVenda.save(venda);
        }
        else {
            throw new IllegalArgumentException("Serviço ou venda não encontrados");
        }
    }

    public void vincularServicoEmpresa(Long idServico, Long idEmpresa) {
        Servico servico = repositorioServico.findById(idServico).orElse(null);
        Empresa empresa = repositorioEmpresa.findById(idEmpresa).orElse(null);
        if (servico != null && empresa != null) {
            empresa.getServicos().add(servico);
            repositorioEmpresa.save(empresa);
        } else {
            throw new IllegalArgumentException("Serviço ou empresa não encontrados");
        }
    }

    public void desvincularServicoEmpresa(Long idServico, Long idEmpresa) {
        Servico servico = repositorioServico.findById(idServico).orElse(null);
        Empresa empresa = repositorioEmpresa.findById(idEmpresa).orElse(null);
        if (servico != null && empresa != null) {
            empresa.getServicos().removeIf(s -> s.getId().equals(idServico));
            repositorioEmpresa.save(empresa);
        } else {
            throw new IllegalArgumentException("Serviço ou empresa não encontrados");
        }
    }
}
