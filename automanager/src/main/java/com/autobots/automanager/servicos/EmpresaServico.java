package com.autobots.automanager.servicos;

import com.autobots.automanager.entitades.Empresa;
import com.autobots.automanager.modelos.adicionadores.AdicionadorLinkEmpresa;
import com.autobots.automanager.modelos.cadastrador.CadastradorEmpresa;
import com.autobots.automanager.repositorios.RepositorioEmpresa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class EmpresaServico {

    @Autowired
    private CadastradorEmpresa cadastradorEmpresa;

    @Autowired
    private RepositorioEmpresa repositorioEmpresa;

    @Autowired
    private AdicionadorLinkEmpresa adicionadorLinkEmpresa;

    public void cadastrarEmpresa(Empresa empresa) {
        Empresa empresaCadastrada = cadastradorEmpresa.cadastrarEmpresa(empresa);
        repositorioEmpresa.save(empresaCadastrada);
    }

    public void deletarEmpresa(Long id) {
        Empresa empresa = repositorioEmpresa.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Empresa não encontrada"));
        empresa.getUsuarios().clear();
        empresa.getMercadorias().clear();
        empresa.getServicos().clear();
        empresa.getVendas().clear();
        repositorioEmpresa.save(empresa);
        repositorioEmpresa.deleteById(id);
    }

    public ResponseEntity<?> atualizarEmpresa(Long id, Empresa empresa) {
        Empresa empresaAtual = repositorioEmpresa.findById(id).orElse(null);
        if (empresaAtual != null) {
            if (empresa.getRazaoSocial() != null) {
                empresaAtual.setRazaoSocial(empresa.getRazaoSocial());
            }
            if (empresa.getNomeFantasia() != null) {
                empresaAtual.setNomeFantasia(empresa.getNomeFantasia());
            }
            if (empresa.getTelefones() != null && !empresa.getTelefones().isEmpty()) {
                empresaAtual.getTelefones().clear();
                empresaAtual.getTelefones().addAll(empresa.getTelefones());
            }
            if (empresa.getEndereco() != null) {
                empresaAtual.setEndereco(empresa.getEndereco());
            }
            if (empresa.getCadastro() != null) {
                empresaAtual.setCadastro(empresa.getCadastro());
            }
            if (empresa.getUsuarios() != null && !empresa.getUsuarios().isEmpty()) {
                empresaAtual.getUsuarios().addAll(empresa.getUsuarios());
            }
            if (empresa.getMercadorias() != null && !empresa.getMercadorias().isEmpty()) {
                empresaAtual.getMercadorias().addAll(empresa.getMercadorias());
            }
            if (empresa.getServicos() != null && !empresa.getServicos().isEmpty()) {
                empresaAtual.getServicos().addAll(empresa.getServicos());
            }
            if (empresa.getVendas() != null && !empresa.getVendas().isEmpty()) {
                empresaAtual.getVendas().addAll(empresa.getVendas());
            }
            repositorioEmpresa.save(empresaAtual);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public List<Empresa> listarEmpresas() {
        List<Empresa> empresas = repositorioEmpresa.findAll();
        adicionadorLinkEmpresa.adicionarLink(empresas);
        return empresas;
    }

    public Empresa visualizarEmpresa(Long id) {
        Empresa empresa = repositorioEmpresa.findById(id).orElse(null);
        if (empresa != null) {
            adicionadorLinkEmpresa.adicionarLink(empresa);
        }
        return empresa;
    }
}
