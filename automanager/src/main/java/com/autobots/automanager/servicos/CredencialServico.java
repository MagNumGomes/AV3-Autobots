package com.autobots.automanager.servicos;

import com.autobots.automanager.DTO.CredencialDto;
import com.autobots.automanager.entitades.Credencial;
import com.autobots.automanager.entitades.CredencialCodigoBarra;
import com.autobots.automanager.entitades.CredencialUsuarioSenha;
import com.autobots.automanager.entitades.Usuario;
import com.autobots.automanager.modelos.adicionadores.AdicionadorLinkCredencial;
import com.autobots.automanager.repositorios.RepositorioCredencial;
import com.autobots.automanager.repositorios.RepositorioUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Service
public class CredencialServico {

    @Autowired
    private RepositorioCredencial repositorioCredencial;

    @Autowired
    private RepositorioUsuario repositorioUsuario;

    @Autowired
    private AdicionadorLinkCredencial adicionadorLinkCredencial;

    public List<Credencial> listarCredenciais() {
        List<Credencial> credencials = repositorioCredencial.findActiveCredentials();
        adicionadorLinkCredencial.adicionarLink(credencials);
        return credencials;
    }

    public Credencial visualizarCredencial(Long id) {
        Credencial credencial = repositorioCredencial.findActiveCredentialById(id).orElse(null);
        if (credencial != null) {
            adicionadorLinkCredencial.adicionarLink(credencial);
        }
        return credencial;
    }

    public List<Credencial> listarCredenciaisUsuario(Long idUsuario) {
        Usuario usuario = repositorioUsuario.findById(idUsuario).orElse(null);
        if (usuario == null) {
            throw new IllegalArgumentException("Usuário não encontrado.");
        }
        Set<Credencial> credenciais = usuario.getCredenciais();
        List<Credencial> credenciaisLista = new ArrayList<>();
        for (Credencial credencial : credenciais) {
            if (!credencial.isInativo()) {
                credenciaisLista.add(credencial);
            }
        }
        adicionadorLinkCredencial.adicionarLink(credenciaisLista);
        return credenciaisLista;
    }

    public void cadastrarCredencialUsuarioSenha(Long idUsuario, CredencialDto credencial) {

        Usuario usuario = repositorioUsuario.findById(idUsuario).orElse(null);
        if (usuario == null) {
            throw new IllegalArgumentException("Usuário não encontrado.");
        }
        if (credencial.nomeUsuario().isEmpty() || credencial.senha().isEmpty()) {
            throw new IllegalArgumentException("Nome de usuário e senha são obrigatórios.");
        }

        CredencialUsuarioSenha credencialUsuarioSenha = new CredencialUsuarioSenha();
        credencialUsuarioSenha.setNomeUsuario(credencial.nomeUsuario().get());
        credencialUsuarioSenha.setSenha(credencial.senha().get());
        credencialUsuarioSenha.setInativo(false);
        credencialUsuarioSenha.setCriacao(new Date());
        credencialUsuarioSenha.setUltimoAcesso(new Date());
        repositorioCredencial.save(credencialUsuarioSenha);

        usuario.getCredenciais().add(credencialUsuarioSenha);
        repositorioUsuario.save(usuario);
    }

    public void cadastrarCredencialCodigoBarra(Long idUsuario, CredencialDto credencial) {

        Usuario usuario = repositorioUsuario.findById(idUsuario).orElse(null);
        if (usuario == null) {
            throw new IllegalArgumentException("Usuário não encontrado.");
        }
        if (credencial.codigo().isEmpty()) {
            throw new IllegalArgumentException("Código de barras é obrigatório.");
        }

        CredencialCodigoBarra credencialCodigoBarra = new CredencialCodigoBarra();
        credencialCodigoBarra.setCodigo(credencial.codigo().get());
        credencialCodigoBarra.setInativo(false);
        credencialCodigoBarra.setCriacao(new Date());
        credencialCodigoBarra.setUltimoAcesso(new Date());
        repositorioCredencial.save(credencialCodigoBarra);

        usuario.getCredenciais().add(credencialCodigoBarra);
        repositorioUsuario.save(usuario);
    }

    public void atualizarCredencial(Long id, CredencialDto credencial) {
        Credencial credencialAtualizado = repositorioCredencial.findActiveCredentialById(id).orElse(null);

        if (credencialAtualizado != null) {
            if (credencialAtualizado instanceof CredencialUsuarioSenha) {
                if (credencial.nomeUsuario().isPresent()) {
                    ((CredencialUsuarioSenha) credencialAtualizado).setNomeUsuario(credencial.nomeUsuario().get());
                }
                if (credencial.senha().isPresent()) {
                    ((CredencialUsuarioSenha) credencialAtualizado).setSenha(credencial.senha().get());
                }
            }
            else if (credencialAtualizado instanceof CredencialCodigoBarra) {
                if (credencial.codigo().isPresent()) {
                    ((CredencialCodigoBarra) credencialAtualizado).setCodigo(credencial.codigo().get());
                }
            }
            credencialAtualizado.setUltimoAcesso(new Date());

            repositorioCredencial.save(credencialAtualizado);
        }

        else {
            throw new IllegalArgumentException("Credencial não encontrada.");
        }
    }

    public void deletarCredencial(Long id) {
        Credencial credencial = repositorioCredencial.findById(id).orElse(null);
        if (credencial != null) {
            credencial.setInativo(true);
            repositorioCredencial.save(credencial);
        }
        else {
            throw new IllegalArgumentException("Credencial não encontrada.");
        }
    }
}