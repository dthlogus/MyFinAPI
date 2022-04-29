package com.darthlougs.MyFin.repository;

import com.darthlougs.MyFin.entity.Usuario;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class UsuarioRepositoryTest {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Test
    public void deveRetornarUmaThrowQuandoExistirEmail() {
        Usuario usuario = Usuario.builder().nome("teste").email("usuario@email.com").build();
        usuarioRepository.save(usuario);
        Boolean existe = usuarioRepository.existsByEmail("usuario@email.com");
        Assertions.assertTrue(existe);
    }

    @Test
    public void naoDeveRetornarUmaThrowSeNaoExistirOEmail() {
        usuarioRepository.deleteAll();
        Boolean existe = usuarioRepository.existsByEmail("usuario@email.com");
        Assertions.assertFalse(existe);
    }

}
