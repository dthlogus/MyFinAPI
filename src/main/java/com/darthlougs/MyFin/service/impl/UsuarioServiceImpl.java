package com.darthlougs.MyFin.service.impl;

import com.darthlougs.MyFin.entity.Usuario;
import com.darthlougs.MyFin.exception.AutenticacaoException;
import com.darthlougs.MyFin.exception.RegraNegocioException;
import com.darthlougs.MyFin.repository.UsuarioRepository;
import com.darthlougs.MyFin.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    private UsuarioRepository repository;

    @Autowired
    public UsuarioServiceImpl(UsuarioRepository repository) {
        this.repository = repository;
    }

    @Override
    public Usuario autenticar(String email, String senha) {
        Optional<Usuario> usuario = repository.findByEmail(email);

        if (!usuario.isPresent()){
            throw new AutenticacaoException("Usuário não encontrado para o e-mail informado");
        }

        if (!usuario.get().getSenha().equals(senha)){
            throw new AutenticacaoException("Senha inválida");
        }

        return usuario.get();
    }

    @Override
    @Transactional
    public Usuario salvarUsuario(Usuario usuario) {
        validarEmail(usuario.getEmail());
        return repository.save(usuario);
    }

    @Override
    public void validarEmail(String email) {
        if(repository.existsByEmail(email)){
            throw new RegraNegocioException("Já existe um usuário com esse email");
        }
    }

}
