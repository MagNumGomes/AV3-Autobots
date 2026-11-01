package com.autobots.automanager.modelos.cadastrador;

import com.autobots.automanager.DTO.UsuarioDto;
import com.autobots.automanager.entitades.*;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class CadastradorUsuario {
    public static Usuario cadastrarUsuario(UsuarioDto usuario){
        Usuario setUsario = new Usuario();
        setUsario.setNome(usuario.nome());
        setUsario.setNomeSocial(usuario.nomeSocial());
        if (usuario.perfis() != null) {
            setUsario.setPerfis(usuario.perfis());
        }

        if (usuario.telefones() != null) {
            for (Telefone telefone : usuario.telefones()){
                Telefone setTelefone = new Telefone();
                setTelefone.setDdd(telefone.getDdd());
                setTelefone.setNumero(telefone.getNumero());
                setUsario.getTelefones().add(setTelefone);
            }
        }

        if (usuario.endereco() != null) {
            Endereco setEndereco = new Endereco();
            setEndereco.setEstado(usuario.endereco().getEstado());
            setEndereco.setCidade(usuario.endereco().getCidade());
            setEndereco.setBairro(usuario.endereco().getBairro());
            setEndereco.setRua(usuario.endereco().getRua());
            setEndereco.setNumero(usuario.endereco().getNumero());
            setEndereco.setCodigoPostal(usuario.endereco().getCodigoPostal());
            if (usuario.endereco().getInformacoesAdicionais() != null){
                setEndereco.setInformacoesAdicionais(usuario.endereco().getInformacoesAdicionais());
            }
            setUsario.setEndereco(setEndereco);
        }

        if (usuario.documentos() != null) {
            for (Documento documento : usuario.documentos()){
                Documento setDocumento = new Documento();
                setDocumento.setTipo(documento.getTipo());
                setDocumento.setNumero(documento.getNumero());
                setDocumento.setDataEmissao(documento.getDataEmissao() != null ? documento.getDataEmissao() : new Date());
                setUsario.getDocumentos().add(setDocumento);
            }
        }

        if (usuario.emails() != null) {
            for (Email email : usuario.emails()){
                Email setEmail = new Email();
                setEmail.setEndereco(email.getEndereco());
                setUsario.getEmails().add(setEmail);
            }
        }

        if (usuario.credenciais() != null) {
            for (Credencial credencial : usuario.credenciais()){
                if (credencial instanceof CredencialUsuarioSenha) {
                    CredencialUsuarioSenha original = (CredencialUsuarioSenha) credencial;
                    CredencialUsuarioSenha nova = new CredencialUsuarioSenha();
                    nova.setNomeUsuario(original.getNomeUsuario());
                    nova.setSenha(original.getSenha());
                    nova.setInativo(false);
                    nova.setUltimoAcesso(new Date());
                    nova.setCriacao(new Date());
                    setUsario.getCredenciais().add(nova);
                } else if (credencial instanceof CredencialCodigoBarra) {
                    CredencialCodigoBarra original = (CredencialCodigoBarra) credencial;
                    CredencialCodigoBarra nova = new CredencialCodigoBarra();
                    nova.setCodigo(original.getCodigo());
                    nova.setInativo(false);
                    nova.setUltimoAcesso(new Date());
                    nova.setCriacao(new Date());
                    setUsario.getCredenciais().add(nova);
                }
            }
        }

        if (usuario.mercadorias() != null) {
            for (Mercadoria mercadoria : usuario.mercadorias()){
                Mercadoria setMercadoria = new Mercadoria();
                setMercadoria.setNome(mercadoria.getNome());
                setMercadoria.setValor(mercadoria.getValor());
                setMercadoria.setQuantidade(mercadoria.getQuantidade());
                setMercadoria.setCadastro(mercadoria.getCadastro() != null ? mercadoria.getCadastro() : new Date());
                setMercadoria.setFabricao(mercadoria.getFabricao() != null ? mercadoria.getFabricao() : new Date());
                setMercadoria.setValidade(mercadoria.getValidade() != null ? mercadoria.getValidade() : new Date());
                setMercadoria.setDescricao(mercadoria.getDescricao());
                setUsario.getMercadorias().add(setMercadoria);
            }
        }

        if (usuario.vendas() != null){
            setUsario.setVendas(usuario.vendas());
        }

        if (usuario.veiculos() != null){
            for (Veiculo veiculo : usuario.veiculos()) {
                veiculo.setProprietario(setUsario);
                setUsario.getVeiculos().add(veiculo);
            }
        }
        return setUsario;
    }

    public Usuario cadastrarUsuario(Usuario usuario){
        Usuario setUsario = new Usuario();
        setUsario.setNome(usuario.getNome());
        setUsario.setNomeSocial(usuario.getNomeSocial());
        if (usuario.getPerfis() != null) {
            setUsario.setPerfis(usuario.getPerfis());
        }

        if (usuario.getTelefones() != null) {
            for (Telefone telefone : usuario.getTelefones()){
                Telefone setTelefone = new Telefone();
                setTelefone.setDdd(telefone.getDdd());
                setTelefone.setNumero(telefone.getNumero());
                setUsario.getTelefones().add(setTelefone);
            }
        }

        if (usuario.getEndereco() != null) {
            Endereco setEndereco = new Endereco();
            setEndereco.setEstado(usuario.getEndereco().getEstado());
            setEndereco.setCidade(usuario.getEndereco().getCidade());
            setEndereco.setBairro(usuario.getEndereco().getBairro());
            setEndereco.setRua(usuario.getEndereco().getRua());
            setEndereco.setNumero(usuario.getEndereco().getNumero());
            setEndereco.setCodigoPostal(usuario.getEndereco().getCodigoPostal());
            if (usuario.getEndereco().getInformacoesAdicionais() != null){
                setEndereco.setInformacoesAdicionais(usuario.getEndereco().getInformacoesAdicionais());
            }
            setUsario.setEndereco(setEndereco);
        }

        if (usuario.getDocumentos() != null) {
            for (Documento documento : usuario.getDocumentos()){
                Documento setDocumento = new Documento();
                setDocumento.setTipo(documento.getTipo());
                setDocumento.setNumero(documento.getNumero());
                setDocumento.setDataEmissao(documento.getDataEmissao() != null ? documento.getDataEmissao() : new Date());
                setUsario.getDocumentos().add(setDocumento);
            }
        }

        if (usuario.getEmails() != null) {
            for (Email email : usuario.getEmails()){
                Email setEmail = new Email();
                setEmail.setEndereco(email.getEndereco());
                setUsario.getEmails().add(setEmail);
            }
        }

        if (usuario.getCredenciais() != null) {
            for (Credencial credencial : usuario.getCredenciais()) {
                if (credencial instanceof CredencialUsuarioSenha) {
                    CredencialUsuarioSenha credencialCadastrada = new CredencialUsuarioSenha();
                    CredencialUsuarioSenha original = (CredencialUsuarioSenha) credencial;
                    credencialCadastrada.setNomeUsuario(original.getNomeUsuario());
                    credencialCadastrada.setSenha(original.getSenha());
                    credencialCadastrada.setInativo(false);
                    credencialCadastrada.setCriacao(new Date());
                    credencialCadastrada.setUltimoAcesso(new Date());
                    setUsario.getCredenciais().add(credencialCadastrada);
                } else if (credencial instanceof CredencialCodigoBarra) {
                    CredencialCodigoBarra credencialCadastrada = new CredencialCodigoBarra();
                    CredencialCodigoBarra original = (CredencialCodigoBarra) credencial;
                    credencialCadastrada.setCodigo(original.getCodigo());
                    credencialCadastrada.setInativo(false);
                    credencialCadastrada.setCriacao(new Date());
                    credencialCadastrada.setUltimoAcesso(new Date());
                    setUsario.getCredenciais().add(credencialCadastrada);
                }
            }
        }

        if (usuario.getMercadorias() != null){
            for (Mercadoria mercadoria : usuario.getMercadorias()){
                Mercadoria setMercadoria = new Mercadoria();
                setMercadoria.setNome(mercadoria.getNome());
                setMercadoria.setValor(mercadoria.getValor());
                setMercadoria.setQuantidade(mercadoria.getQuantidade());
                setMercadoria.setCadastro(mercadoria.getCadastro() != null ? mercadoria.getCadastro() : new Date());
                setMercadoria.setFabricao(mercadoria.getFabricao() != null ? mercadoria.getFabricao() : new Date());
                setMercadoria.setValidade(mercadoria.getValidade() != null ? mercadoria.getValidade() : new Date());
                setMercadoria.setDescricao(mercadoria.getDescricao());
                setUsario.getMercadorias().add(setMercadoria);
            }
        }

        if (usuario.getVendas() != null){
            setUsario.setVendas(usuario.getVendas());
        }

        if (usuario.getVeiculos() != null){
            for (Veiculo veiculo : usuario.getVeiculos()) {
                veiculo.setProprietario(setUsario);
                setUsario.getVeiculos().add(veiculo);
            }
        }

        return setUsario;
    }
}
