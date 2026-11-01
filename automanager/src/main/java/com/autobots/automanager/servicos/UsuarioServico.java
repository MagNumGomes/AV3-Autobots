package com.autobots.automanager.servicos;

import com.autobots.automanager.DTO.AtualizarUsuarioDto;
import com.autobots.automanager.DTO.UsuarioDto;
import com.autobots.automanager.entitades.*;
import com.autobots.automanager.modelos.adicionadores.AdicionadorLinkUsuario;
import com.autobots.automanager.modelos.atualizador.AtualizadorDocumento;
import com.autobots.automanager.modelos.atualizador.AtualizadorEmail;
import com.autobots.automanager.modelos.atualizador.AtualizadorTelefone;
import com.autobots.automanager.modelos.cadastrador.CadastradorUsuario;
import com.autobots.automanager.repositorios.RepositorioEmpresa;
import com.autobots.automanager.repositorios.RepositorioUsuario;
import com.autobots.automanager.repositorios.RepositorioVeiculo;
import com.autobots.automanager.repositorios.RepositorioVenda;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioServico {

    @Autowired
    private RepositorioUsuario repositorioUsuario;

    @Autowired
    private AdicionadorLinkUsuario adicionadorLinkUsuario;

    @Autowired
    private RepositorioVenda repositorioVenda;

    @Autowired
    private RepositorioEmpresa repositorioEmpresa;

    @Autowired
    private RepositorioVeiculo repositorioVeiculo;

    @Autowired
    private CadastradorUsuario cadastradorUsuario;

    @Autowired
    private AtualizadorEmail atualizadorEmail;

    @Autowired
    private AtualizadorDocumento atualizadorDocumento;

    @Autowired
    private AtualizadorTelefone atualizadorTelefone;

    public void cadastrarUsuario(UsuarioDto usuario) {
        Usuario usuarioCadastrado = CadastradorUsuario.cadastrarUsuario(usuario);
        repositorioUsuario.save(usuarioCadastrado);
    }

    public void cadastrarUsuarioEmpresa(UsuarioDto usuario, Long idEmpresa) {
        Empresa empresa = repositorioEmpresa.findById(idEmpresa).orElse(null);
        if (empresa == null) {
            throw new IllegalArgumentException("Empresa não encontrada");
        }
        Usuario usuarioCadastrado = CadastradorUsuario.cadastrarUsuario(usuario);
        Usuario usuarioSalvo = repositorioUsuario.save(usuarioCadastrado);
        empresa.getUsuarios().add(usuarioSalvo);
        repositorioEmpresa.save(empresa);
    }

    public void deletarUsuario(Long id) {
        Usuario usuario = repositorioUsuario.findById(id).orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));
        List<Venda> vendas = repositorioVenda.findAll();
        for (Venda venda : vendas) {
            boolean alterada = false;
            if (venda.getCliente() != null && venda.getCliente().getId().equals(id)) {
                venda.setCliente(null);
                alterada = true;
            }
            if (venda.getFuncionario() != null && venda.getFuncionario().getId().equals(id)) {
                venda.setFuncionario(null);
                alterada = true;
            }
            if (alterada) {
                repositorioVenda.save(venda);
            }
        }

        List<Veiculo> veiculos = repositorioVeiculo.findAll();
        for (Veiculo veiculo : veiculos) {
            if (veiculo.getProprietario() != null && veiculo.getProprietario().getId().equals(id)) {
                veiculo.setProprietario(null);
                repositorioVeiculo.save(veiculo);
            }
        }

        List<Empresa> empresas = repositorioEmpresa.findAll();
        for (Empresa empresa : empresas) {
            if (empresa.getUsuarios().removeIf(u -> u.getId().equals(id))) {
                repositorioEmpresa.save(empresa);
            }
        }

        usuario.getVeiculos().clear();
        usuario.getVendas().clear();
        usuario.getDocumentos().clear();
        usuario.setEndereco(null);
        usuario.getTelefones().clear();
        usuario.getEmails().clear();
        usuario.getCredenciais().clear();
        usuario.getMercadorias().clear();
        repositorioUsuario.save(usuario);

        repositorioUsuario.deleteById(id);
    }

    public ResponseEntity<?> atualizarUsuario(Long id, AtualizarUsuarioDto usuario) {
        Usuario usuarioAtual = repositorioUsuario.findById(id).orElse(null);
        if (usuarioAtual != null) {
            if (usuario.nome().isPresent()) {
                usuarioAtual.setNome(usuario.nome().get());
            }
            if (usuario.nomeSocial().isPresent()) {
                usuarioAtual.setNomeSocial(usuario.nomeSocial().get());
            }
            if (usuario.perfis().isPresent()) {
                usuarioAtual.setPerfis(usuario.perfis().get());
            }
            if (usuario.telefones().isPresent()) {
                for (Telefone telefone : usuario.telefones().get()) {
                    if (telefone.getId() != null) {
                        for (Telefone telefoneAtual : usuarioAtual.getTelefones()) {
                            if (telefone.getId().equals(telefoneAtual.getId())) {
                                atualizadorTelefone.atualizarTelefone(telefoneAtual, telefone);
                            }
                        }
                    } else {
                        usuarioAtual.getTelefones().add(telefone);
                    }
                }
            }
            if (usuario.endereco().isPresent()) {
                usuarioAtual.setEndereco(usuario.endereco().get());
            }
            if (usuario.documentos().isPresent()) {
                for (Documento documento : usuario.documentos().get()) {
                    if (documento.getId() != null) {
                        for (Documento documentoAtual : usuarioAtual.getDocumentos()) {
                            if (documento.getId().equals(documentoAtual.getId())) {
                                atualizadorDocumento.atualizarDocumento(documentoAtual, documento);
                            }
                        }
                    } else {
                        if (documento.getDataEmissao() == null) {
                            documento.setDataEmissao(new java.util.Date());
                        }
                        usuarioAtual.getDocumentos().add(documento);
                    }
                }
            }
            if (usuario.emails().isPresent()) {
                for (Email email : usuario.emails().get()) {
                    if (email.getId() != null) {
                        for (Email emailAtual : usuarioAtual.getEmails()) {
                            if (email.getId().equals(emailAtual.getId())) {
                                atualizadorEmail.atualizarEmail(emailAtual, email);
                            }
                        }
                    } else {
                        usuarioAtual.getEmails().add(email);
                    }
                }
            }
            if (usuario.credenciais().isPresent()) {
                for (Credencial credencial : usuario.credenciais().get()) {
                    if (credencial.getCriacao() == null) {
                        credencial.setCriacao(new java.util.Date());
                    }
                    if (credencial.getUltimoAcesso() == null) {
                        credencial.setUltimoAcesso(new java.util.Date());
                    }
                    usuarioAtual.getCredenciais().add(credencial);
                }
            }
            if (usuario.mercadorias().isPresent()) {
                usuarioAtual.getMercadorias().addAll(usuario.mercadorias().get());
            }
            if (usuario.vendas().isPresent()) {
                usuarioAtual.getVendas().addAll(usuario.vendas().get());
            }
            if (usuario.veiculos().isPresent()) {
                usuarioAtual.getVeiculos().addAll(usuario.veiculos().get());
            }
            repositorioUsuario.save(usuarioAtual);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public List<Usuario> listarUsuarios() {
        List<Usuario> usuarios = repositorioUsuario.findAll();
        adicionadorLinkUsuario.adicionarLink(usuarios);
        return usuarios;
    }

    public Usuario visualizarUsuario(Long id) {
        Usuario usuario = repositorioUsuario.findById(id).orElse(null);
        if (usuario != null) {
            adicionadorLinkUsuario.adicionarLink(usuario);
        }
        return usuario;
    }

    public void vincularUsuarioEmpresa(Long idUsuario, Long idEmpresa) {
        Usuario usuario = repositorioUsuario.findById(idUsuario).orElse(null);
        if (usuario == null) {
            throw new IllegalArgumentException("Usuário não encontrado");
        }
        Empresa empresa = repositorioEmpresa.findById(idEmpresa).orElse(null);
        if (empresa == null) {
            throw new IllegalArgumentException("Empresa não encontrada");
        }
        empresa.getUsuarios().add(usuario);
        repositorioEmpresa.save(empresa);
    }

    public void desvincularUsuarioEmpresa(Long idUsuario, Long idEmpresa) {
        Usuario usuario = repositorioUsuario.findById(idUsuario).orElse(null);
        if (usuario == null) {
            throw new IllegalArgumentException("Usuário não encontrado");
        }
        Empresa empresa = repositorioEmpresa.findById(idEmpresa).orElse(null);
        if (empresa == null) {
            throw new IllegalArgumentException("Empresa não encontrada");
        }
        empresa.getUsuarios().removeIf(u -> u.getId().equals(idUsuario));
        repositorioEmpresa.save(empresa);
    }
}
