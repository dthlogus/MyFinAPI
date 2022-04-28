package com.darthlougs.MyFin.repository;

import com.darthlougs.MyFin.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
}
