package com.darthlougs.MyFin.service.impl;

import com.darthlougs.MyFin.entity.Usuario;
import com.darthlougs.MyFin.exception.RegraNegocioException;
import com.darthlougs.MyFin.repository.UsuarioRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest
@ExtendWith({SpringExtension.class})
@ActiveProfiles("test")
public class UsuarioServiceImplTest {

    @Autowired
    UsuarioServiceImpl service;

    @Autowired
    UsuarioRepository repository;

    @Test
    public void seNaoExisteEmailEleTerminaSemLancarUmaThrowRegraDeNegocio(){
        Assertions.assertDoesNotThrow(() -> {
            repository.deleteAll();
            service.validarEmail("usuario@email.com");
        });
    }

    @Test
    public void deveLancarUmaRegraDeNegocioExcepitonQuandoExistirOEmail(){
        Assertions.assertThrows(RegraNegocioException.class, () -> {
           repository.save(Usuario.builder().nome("usuario").email("usuario@email.com").build());
           service.validarEmail("usuario@email.com");
        });
    }

}
