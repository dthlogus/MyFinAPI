package com.darthlougs.MyFin.repository;

import com.darthlougs.MyFin.entity.Lancamento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LancamentoRepository extends JpaRepository<Lancamento, Long> {
}
