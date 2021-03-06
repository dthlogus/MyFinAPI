package com.darthlougs.MyFin.service.impl;

import com.darthlougs.MyFin.entity.Lancamento;
import com.darthlougs.MyFin.entity.enums.StatusLancamento;
import com.darthlougs.MyFin.entity.enums.TipoLancamento;
import com.darthlougs.MyFin.exception.NaoEncontradoException;
import com.darthlougs.MyFin.exception.RegraNegocioException;
import com.darthlougs.MyFin.repository.LancamentoRepository;
import com.darthlougs.MyFin.service.LancamentoService;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@Service
public class LancamentoServiceImpl implements LancamentoService {

    private LancamentoRepository repository;

    public LancamentoServiceImpl(LancamentoRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional
    public Lancamento salvar(Lancamento lancamento) {
        validar(lancamento);
        lancamento.setStatus(StatusLancamento.PENDENTE);
        return repository.save(lancamento);
    }

    @Override
    @Transactional
    public Lancamento atualizar(Lancamento lancamento) {
        Objects.requireNonNull(lancamento.getId());
        validar(lancamento);
        return repository.save(lancamento);
    }

    @Override
    @Transactional
    public void deletar(Lancamento lancamento) {
        Objects.requireNonNull(lancamento.getId());
        repository.delete(lancamento);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Lancamento> buscar(Lancamento lancamentoFiltro) {
        Example<Lancamento> example = Example.of(lancamentoFiltro, ExampleMatcher.matching().withIgnoreCase().withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING));
        return repository.findAll(example);
    }

    @Override
    public void atualizarStatus(Lancamento lancamento, StatusLancamento status) {
        lancamento.setStatus(status);
        atualizar(lancamento);
    }

    @Override
    public Lancamento buscarPorId(Long id) {
        return repository.findById(id).orElseThrow(() -> new NaoEncontradoException("Lancamento n??o encontrado na base de dados"));
    }

    @Override
    @Transactional(readOnly = true)
    public BigDecimal obterSaldoPorUsuario(Long id) {
        BigDecimal receita = repository.obterSaldoPorTipoLancamentoEUsuario(id, TipoLancamento.RECEITA);
        BigDecimal despesa = repository.obterSaldoPorTipoLancamentoEUsuario(id, TipoLancamento.DESPESA);
        if (receita == null){
            receita = BigDecimal.ZERO;
        }
        if (despesa == null){
            despesa = BigDecimal.ZERO;
        }
        return receita.subtract(despesa);
    }

    @Override
    public void validar(Lancamento lancamento) {
        if (lancamento.getDescricao() == null || lancamento.getDescricao().trim().equals("")){
            throw new RegraNegocioException("Informe uma descri????o v??lida");
        }

        if (lancamento.getMes() == null || lancamento.getMes() < 1 || lancamento.getMes() > 12){
            throw new RegraNegocioException("Informe um m??s v??lido");
        }

        if (lancamento.getAno() == null || lancamento.getAno().toString().length() != 4){
            throw new RegraNegocioException("Informe um ano v??lido");
        }

        if (lancamento.getUsuario() == null || lancamento.getUsuario().getId() == null){
            throw new RegraNegocioException("Informe um usu??rio");
        }

        if (lancamento.getValor() == null || lancamento.getValor().compareTo(BigDecimal.ZERO) < 1){
            throw new RegraNegocioException("Informe um valor v??lido");
        }

        if (lancamento.getTipo() == null){
            throw new RegraNegocioException("Informe um tipo de lancamento");
        }
    }
}
