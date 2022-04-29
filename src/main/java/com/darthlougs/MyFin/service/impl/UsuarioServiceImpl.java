package com.darthlougs.MyFin.service.impl;

import com.darthlougs.MyFin.entity.Usuario;
import com.darthlougs.MyFin.exception.RegraNegocioException;
import com.darthlougs.MyFin.repository.UsuarioRepository;
import com.darthlougs.MyFin.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioRepository repository;

    @Override
    public Usuario autenticar(String email, String senha) {
        return null;
    }

    @Override
    public Usuario salvarUsuario(Usuario usuario) {
        return null;
    }

    @Override
    public void validarEmail(String email) {
        if(repository.existsByEmail(email)){
            throw new RegraNegocioException("Já existe um usuário com esse email");
        }
    }

}
