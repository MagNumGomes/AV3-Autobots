package com.autobots.automanager.modelos.cadastrador;

import com.autobots.automanager.entitades.*;
import com.autobots.automanager.repositorios.RepositorioMercadoria;
import com.autobots.automanager.repositorios.RepositorioServico;
import com.autobots.automanager.repositorios.RepositorioUsuario;
import com.autobots.automanager.repositorios.RepositorioVenda;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class CadastradorEmpresa {

    @Autowired
    private CadastradorMercadoria cadastradorMercadoria;

    @Autowired
    private CadastradorUsuario cadastradorUsuario;

    @Autowired
    private CadastradorVenda cadastradorVenda;

    @Autowired
    private RepositorioUsuario repositorioUsuario;

    @Autowired
    private RepositorioMercadoria repositorioMercadoria;

    @Autowired
    private RepositorioServico repositorioServico;

    @Autowired
    private RepositorioVenda repositorioVenda;

    public Empresa cadastrarEmpresa(Empresa empresa){
        Empresa setEmpresa = new Empresa();
        setEmpresa.setNomeFantasia(empresa.getNomeFantasia());
        setEmpresa.setRazaoSocial(empresa.getRazaoSocial());
        if (empresa.getTelefones() != null){
            for (Telefone telefone : empresa.getTelefones()){
                Telefone setTelefone = new Telefone();
                setTelefone.setDdd(telefone.getDdd());
                setTelefone.setNumero(telefone.getNumero());
                setEmpresa.getTelefones().add(setTelefone);
            }
        }

        if (empresa.getEndereco() != null) {
            Endereco setEndereco = new Endereco();
            setEndereco.setEstado(empresa.getEndereco().getEstado());
            setEndereco.setCidade(empresa.getEndereco().getCidade());
            setEndereco.setBairro(empresa.getEndereco().getBairro());
            setEndereco.setRua(empresa.getEndereco().getRua());
            setEndereco.setNumero(empresa.getEndereco().getNumero());
            setEndereco.setCodigoPostal(empresa.getEndereco().getCodigoPostal());
            if (empresa.getEndereco().getInformacoesAdicionais() != null) {
                setEndereco.setInformacoesAdicionais(empresa.getEndereco().getInformacoesAdicionais());
            }
            setEmpresa.setEndereco(setEndereco);
        }

        setEmpresa.setCadastro(empresa.getCadastro() != null ? empresa.getCadastro() : new Date());

        if (empresa.getServicos() != null){
            for (Servico servico : empresa.getServicos()){
                Servico setServico = (servico.getId() != null)
                        ? repositorioServico.findById(servico.getId()).orElseThrow(() -> new IllegalArgumentException("Serviço não encontrado"))
                        : servico;
                setEmpresa.getServicos().add(setServico);
            }
        }

        if (empresa.getUsuarios() != null){
            for (Usuario usuario : empresa.getUsuarios()){
                Usuario setUsuario = (usuario.getId() != null)
                        ? repositorioUsuario.findById(usuario.getId()).orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"))
                        : cadastradorUsuario.cadastrarUsuario(usuario);
                setEmpresa.getUsuarios().add(setUsuario);
            }
        }

        if (empresa.getVendas() != null){
            for (Venda venda : empresa.getVendas()){
                Venda setVenda = (venda.getId() != null)
                        ? repositorioVenda.findById(venda.getId()).orElseThrow(() -> new IllegalArgumentException("Venda não encontrada"))
                        : cadastradorVenda.cadastrarVenda(venda);
                setEmpresa.getVendas().add(setVenda);
            }
        }

        if (empresa.getMercadorias() != null){
            for (Mercadoria mercadoria : empresa.getMercadorias()){
                Mercadoria setMercadoria = (mercadoria.getId() != null)
                        ? repositorioMercadoria.findById(mercadoria.getId()).orElseThrow(() -> new IllegalArgumentException("Mercadoria não encontrada"))
                        : cadastradorMercadoria.cadastrarMercadoria(mercadoria);
                setEmpresa.getMercadorias().add(setMercadoria);
            }
        }

        return setEmpresa;
    }
}
