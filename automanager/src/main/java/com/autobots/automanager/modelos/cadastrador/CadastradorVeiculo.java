package com.autobots.automanager.modelos.cadastrador;

import com.autobots.automanager.entitades.Usuario;
import com.autobots.automanager.entitades.Veiculo;
import com.autobots.automanager.entitades.Venda;
import com.autobots.automanager.repositorios.RepositorioUsuario;
import com.autobots.automanager.repositorios.RepositorioVeiculo;
import com.autobots.automanager.repositorios.RepositorioVenda;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CadastradorVeiculo {

    @Autowired
    private RepositorioVeiculo repositorioVeiculo;

    @Autowired
    private RepositorioUsuario repositorioUsuario;

    @Autowired
    private RepositorioVenda repositorioVenda;

    @Autowired
    private CadastradorVenda cadastradorVenda;

    @Autowired
    private CadastradorUsuario cadastradorUsuario;

    public Veiculo cadastrarVeiculo(Veiculo veiculo){
        Veiculo setVeiculo = new Veiculo();
        setVeiculo.setModelo(veiculo.getModelo());
        setVeiculo.setPlaca(veiculo.getPlaca());
        setVeiculo.setTipo(veiculo.getTipo());

        repositorioVeiculo.save(setVeiculo);

        if (veiculo.getProprietario() != null) {
            Usuario proprietario;
            if (veiculo.getProprietario().getId() != null) {
                proprietario = repositorioUsuario.findById(veiculo.getProprietario().getId())
                        .orElseThrow(() -> new IllegalArgumentException("Proprietário não encontrado"));
            } else {
                proprietario = cadastradorUsuario.cadastrarUsuario(veiculo.getProprietario());
            }
            proprietario.getVeiculos().add(setVeiculo);
            setVeiculo.setProprietario(proprietario);
        }
        if (veiculo.getVendas() != null && !veiculo.getVendas().isEmpty()) {
            for (Venda venda : veiculo.getVendas()) {
                Venda vendaAtual = (venda.getId() != null)
                        ? repositorioVenda.findById(venda.getId()).orElseThrow(() -> new IllegalArgumentException("Venda não encontrada"))
                        : cadastradorVenda.cadastrarVenda(venda);
                vendaAtual.setVeiculo(setVeiculo);
                setVeiculo.getVendas().add(vendaAtual);
            }
        }

        return repositorioVeiculo.save(setVeiculo);
    }
}
