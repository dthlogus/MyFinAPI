package com.darthlougs.MyFin.entity;

import com.darthlougs.MyFin.entity.enums.TipoLancamento;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.darthlougs.MyFin.entity.enums.StatusLancamento;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(schema = "financas")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Lancamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String descricao;
    private Integer mes;
    private Integer ano;
    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;
    private BigDecimal valor;
    @Column(name = "data_cadastro")
    private LocalDate dataCadastro;
    @Enumerated(value = EnumType.STRING)
    private TipoLancamento tipo;
    @Enumerated(value = EnumType.STRING)
    private StatusLancamento status;
}
