package com.autobots.automanager.modelos.cadastrador;

import com.autobots.automanager.DTO.MercadoriaDto;
import com.autobots.automanager.entitades.Mercadoria;
import com.autobots.automanager.entitades.Servico;
import com.autobots.automanager.entitades.Usuario;
import com.autobots.automanager.entitades.Veiculo;
import com.autobots.automanager.entitades.Venda;
import com.autobots.automanager.repositorios.RepositorioMercadoria;
import com.autobots.automanager.repositorios.RepositorioServico;
import com.autobots.automanager.repositorios.RepositorioUsuario;
import com.autobots.automanager.repositorios.RepositorioVeiculo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Optional;

@Component
public class CadastradorVenda {

    @Autowired
    private CadastradorMercadoria cadastradorMercadoria;

    @Autowired
    private RepositorioMercadoria repositorioMercadoria;

    @Autowired
    private RepositorioServico repositorioServico;

    @Autowired
    private RepositorioUsuario repositorioUsuario;

    @Autowired
    private RepositorioVeiculo repositorioVeiculo;

    @Autowired
    private CadastradorUsuario cadastradorUsuario;

    public Venda cadastrarVenda(Venda venda){
        Venda setVenda = new Venda();
        setVenda.setCadastro(venda.getCadastro() != null ? venda.getCadastro() : new Date());
        setVenda.setIdentificacao(venda.getIdentificacao());
        if (venda.getCliente() != null){
            Usuario usuario = (venda.getCliente().getId() != null)
                    ? repositorioUsuario.findById(venda.getCliente().getId()).orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado"))
                    : cadastradorUsuario.cadastrarUsuario(venda.getCliente());
            setVenda.setCliente(usuario);
        }
        if (venda.getMercadorias() != null){
            for (Mercadoria mercadoria : venda.getMercadorias()){
                if (mercadoria.getId() != null) {
                    Mercadoria existente = repositorioMercadoria.findById(mercadoria.getId())
                            .orElseThrow(() -> new IllegalArgumentException("Mercadoria não encontrada"));
                    setVenda.getMercadorias().add(existente);
                } else {
                    MercadoriaDto mercadoriaDto = new MercadoriaDto(
                            mercadoria.getNome(),
                            mercadoria.getQuantidade(),
                            mercadoria.getValor(),
                            Optional.ofNullable(mercadoria.getDescricao())
                    );
                    Mercadoria novaMercadoria = cadastradorMercadoria.cadastrarMercadoria(mercadoriaDto);
                    repositorioMercadoria.save(novaMercadoria);
                    setVenda.getMercadorias().add(novaMercadoria);
                }
            }
        }

        if (venda.getFuncionario() != null){
            Usuario usuarioFuncionario = (venda.getFuncionario().getId() != null)
                    ? repositorioUsuario.findById(venda.getFuncionario().getId()).orElseThrow(() -> new IllegalArgumentException("Funcionário não encontrado"))
                    : cadastradorUsuario.cadastrarUsuario(venda.getFuncionario());
            setVenda.setFuncionario(usuarioFuncionario);
        }

        if (venda.getServicos() != null){
            for (Servico servico : venda.getServicos()) {
                if (servico.getId() != null) {
                    Servico existente = repositorioServico.findById(servico.getId())
                            .orElseThrow(() -> new IllegalArgumentException("Serviço não encontrado"));
                    setVenda.getServicos().add(existente);
                } else {
                    setVenda.getServicos().add(servico);
                }
            }
        }

        if (venda.getVeiculo() != null){
            Veiculo veiculo = (venda.getVeiculo().getId() != null)
                    ? repositorioVeiculo.findById(venda.getVeiculo().getId()).orElseThrow(() -> new IllegalArgumentException("Veículo não encontrado"))
                    : venda.getVeiculo();
            setVenda.setVeiculo(veiculo);
        }

        return setVenda;
    }
}
