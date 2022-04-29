package com.darthlougs.MyFin.controller;

import com.darthlougs.MyFin.dto.UsuarioDTO;
import com.darthlougs.MyFin.entity.Usuario;
import com.darthlougs.MyFin.exception.RegraNegocioException;
import com.darthlougs.MyFin.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private UsuarioService service;

    @Autowired
    public UsuarioController(UsuarioService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Usuario salvar(@RequestBody UsuarioDTO dto) {
        Usuario usuario = Usuario.builder()
                .nome(dto.getNome())
                .email(dto.getEmail())
                .senha(dto.getSenha())
                .build();
        return service.salvarUsuario(usuario);
    }

    @PostMapping("/autenticar")
    public Usuario autenticar(@RequestBody UsuarioDTO dto){
        return service.autenticar(dto.getEmail(), dto.getSenha());
    }

}
