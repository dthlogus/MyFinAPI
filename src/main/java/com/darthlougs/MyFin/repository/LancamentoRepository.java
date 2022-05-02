package com.darthlougs.MyFin.repository;

import com.darthlougs.MyFin.entity.Lancamento;
import com.darthlougs.MyFin.entity.enums.TipoLancamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;

public interface LancamentoRepository extends JpaRepository<Lancamento, Long> {

    @Query(value = "SELECT sum(l.valor) " +
            "FROM Lancamento l " +
            "join l.usuario u " +
            "where u.id = :idUsuario and l.tipo = :tipo and l.status <> 'CANCELADO' " +
            "GROUP BY u")
    BigDecimal obterSaldoPorTipoLancamentoEUsuario(@Param("idUsuario") Long idUsuario,@Param("tipo") TipoLancamento tipo);
}
