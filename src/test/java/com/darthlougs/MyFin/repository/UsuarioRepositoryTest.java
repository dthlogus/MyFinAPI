package com.darthlougs.MyFin.repository;

import com.darthlougs.MyFin.entity.Usuario;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class UsuarioRepositoryTest {

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    TestEntityManager entityManager;

    @Test
    public void deveRetornarTrueQuandoOEmailExistir() {
        Usuario usuario = criarUsuario();
        entityManager.persist(usuario);
        Boolean existe = usuarioRepository.existsByEmail("email@email.com");
        Assertions.assertTrue(existe);
    }

    @Test
    public void deveRetornarFalseQuandoOEmailNaoExistir() {
        Boolean existe = usuarioRepository.existsByEmail("email@email.com");
        Assertions.assertFalse(existe);
    }

    @Test
    public void devePersistirUmUsuarioNaBaseDeDados(){
        Usuario usuario = criarUsuario();

        Usuario usuarioSalvo = usuarioRepository.save(usuario);

        Assertions.assertNotNull(usuarioSalvo.getId());
    }

    @Test
    public void deveBuscarOUsuarioComBaseNoEmail(){
        Usuario usuario = criarUsuario();

        usuarioRepository.save(usuario);

        Optional<Usuario> usuarioBuscado  = usuarioRepository.findByEmail("email@email.com");

        Assertions.assertTrue(usuarioBuscado.isPresent());
    }

    @Test
    public void deveRetornarVazioQuandoNaoExistirUsuarioCadastradoComEsseEmail(){
        Optional<Usuario> usuarioBuscado  = usuarioRepository.findByEmail("email@email.com");

        Assertions.assertFalse(usuarioBuscado.isPresent());
    }

    private Usuario criarUsuario(){
        return Usuario.builder()
                .nome("usuario")
                .email("email@email.com")
                .senha("123")
                .build();
    }
}
