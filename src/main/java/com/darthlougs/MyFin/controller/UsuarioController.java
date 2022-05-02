package com.darthlougs.MyFin.controller;

import com.darthlougs.MyFin.dto.UsuarioAuthDTO;
import com.darthlougs.MyFin.dto.UsuarioDTO;
import com.darthlougs.MyFin.entity.Usuario;
import com.darthlougs.MyFin.exception.NaoEncontradoException;
import com.darthlougs.MyFin.service.LancamentoService;
import com.darthlougs.MyFin.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Optional;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService service;
    private final LancamentoService lancamentoService;

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
    public Usuario autenticar(@RequestBody UsuarioAuthDTO dto){
        return service.autenticar(dto.getEmail(), dto.getSenha());
    }

    @GetMapping("{id}/saldo")
    public BigDecimal obterSaldo(@PathVariable("id") Long id){
        service.buscarPorId(id);
        return lancamentoService.obterSaldoPorUsuario(id);
    }

}
