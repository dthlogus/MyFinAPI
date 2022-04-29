package com.darthlougs.MyFin.service.impl;

import com.darthlougs.MyFin.entity.Usuario;
import com.darthlougs.MyFin.exception.AutenticacaoException;
import com.darthlougs.MyFin.exception.RegraNegocioException;
import com.darthlougs.MyFin.repository.UsuarioRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

@ExtendWith({SpringExtension.class})
@ActiveProfiles("test")
public class UsuarioServiceImplTest {

    @MockBean
    UsuarioRepository repository;
    @SpyBean
    UsuarioServiceImpl service;


    @Test
    public void deveAutenticarUmUsuarioComSucesso() {
        Assertions.assertDoesNotThrow(() -> {
            String email = "email@email.com";
            String senha = "123";

            Usuario usuario = Usuario.builder().email(email).senha(senha).id(1L).build();
            Mockito.when(repository.findByEmail(email)).thenReturn(Optional.of(usuario));

            Usuario usuarioRetorno = service.autenticar(email, senha);
            Assertions.assertNotNull(usuarioRetorno.getId());
        });
    }

    @Test
    public void deveRetonarAutenticacaoExceptionQuandoNaoAcharUsuarioComOEmailIgual() {
        Exception e = Assertions.assertThrows(AutenticacaoException.class, () -> {
            String email = "email@email.com";
            String senha = "123";

            Usuario usuario = Usuario.builder().email(email).senha(senha).id(1L).build();
            Mockito.when(repository.findByEmail(email)).thenReturn(Optional.of(usuario));

            service.autenticar("jose@email.com", senha);
        });
        Assertions.assertEquals(e.getMessage(), "Usuário não encontrado para o e-mail informado");
    }

    @Test
    public void deveRetonarAutenticacaoExceptionQuandoValidarQueASenhaSaoDiferentes() {
        Exception e = Assertions.assertThrows(AutenticacaoException.class, () -> {
            String email = "email@email.com";
            String senha = "123";

            Usuario usuario = Usuario.builder().email(email).senha(senha).id(1L).build();
            Mockito.when(repository.findByEmail(email)).thenReturn(Optional.of(usuario));

            service.autenticar(email, "456");
        });

        Assertions.assertEquals(e.getMessage(), "Senha inválida");
    }

    @Test
    public void seNaoExisteEmailEleTerminaSemLancarUmaThrowRegraDeNegocio() {
        Assertions.assertDoesNotThrow(() -> {
            Mockito.when(repository.existsByEmail(Mockito.anyString())).thenReturn(false);
            service.validarEmail("usuario@email.com");
        });
    }

    @Test
    public void deveLancarUmaRegraDeNegocioExcepitonQuandoExistirOEmail() {
        Assertions.assertThrows(RegraNegocioException.class, () -> {
            Mockito.when(repository.existsByEmail(Mockito.anyString())).thenReturn(true);
            service.validarEmail("usuario@email.com");
        });
    }

    @Test
    public void salvarUmUsuarioNaBase() {
        Assertions.assertDoesNotThrow(() -> {
            Mockito.doNothing().when(service).validarEmail(Mockito.anyString());
            Usuario usuario = Usuario.builder().id(1L).nome("usuario").email("email@email.com").senha("123").build();

            Mockito.when(repository.save(Mockito.any(Usuario.class))).thenReturn(usuario);

            Usuario usuarioSalvo = service.salvarUsuario(new Usuario());
            Assertions.assertNotNull(usuarioSalvo);
            Assertions.assertEquals(usuarioSalvo.getId(), 1L);
            Assertions.assertEquals(usuarioSalvo.getNome(), "usuario");
            Assertions.assertEquals(usuarioSalvo.getEmail(), "email@email.com");
            Assertions.assertEquals(usuarioSalvo.getSenha(), "123");
        });
    }

    @Test
    public void NaoDeveSalvarUsuarioComEmailJaCadastradoLancandoRegraDeNegocioException() {
        Assertions.assertThrows(RegraNegocioException.class, () -> {
            String email = "email@email.com";
            Usuario usuario = Usuario.builder().email(email).build();
            Mockito.doThrow(RegraNegocioException.class).when(service).validarEmail(email);
            service.salvarUsuario(usuario);
            Mockito.verify(repository, Mockito.never()).save(usuario);
        });
    }

}
