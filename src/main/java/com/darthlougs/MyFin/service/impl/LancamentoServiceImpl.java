package com.darthlougs.MyFin.service.impl;

import com.darthlougs.MyFin.entity.Lancamento;
import com.darthlougs.MyFin.entity.enums.StatusLancamento;
import com.darthlougs.MyFin.repository.LancamentoRepository;
import com.darthlougs.MyFin.service.LancamentoService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LancamentoServiceImpl implements LancamentoService {

    private LancamentoRepository repository;

    public LancamentoServiceImpl(LancamentoRepository repository) {
        this.repository = repository;
    }

    @Override
    public Lancamento salvar(Lancamento lancamento) {
        return null;
    }

    @Override
    public Lancamento atualizar(Lancamento lancamento) {
        return null;
    }

    @Override
    public void deletar(Lancamento lancamento) {

    }

    @Override
    public List<Lancamento> buscar(Lancamento lancamentoFiltro) {
        return null;
    }

    @Override
    public void atualizarStatus(Lancamento lancamento, StatusLancamento status) {

    }
}
