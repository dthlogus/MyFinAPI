package com.darthlougs.MyFin.controller;

import com.darthlougs.MyFin.dto.AtualizaStatusDTO;
import com.darthlougs.MyFin.dto.LancamentoDTO;
import com.darthlougs.MyFin.entity.Lancamento;
import com.darthlougs.MyFin.entity.enums.StatusLancamento;
import com.darthlougs.MyFin.entity.enums.TipoLancamento;
import com.darthlougs.MyFin.exception.RegraNegocioException;
import com.darthlougs.MyFin.service.LancamentoService;
import com.darthlougs.MyFin.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/lancamentos")
@RequiredArgsConstructor
public class LancamentoController {

    private final LancamentoService service;
    private final UsuarioService usuarioService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Lancamento salvar(@RequestBody LancamentoDTO dto) {
        return service.salvar(converter(dto));
    }

    @PutMapping("{id}")
    public Lancamento atualizar(@PathVariable("id") Long id, @RequestBody LancamentoDTO dto) {
        Lancamento lancamento = service.buscarPorId(id);
        Lancamento lancamentoConvertido = converter(dto);
        lancamentoConvertido.setId(lancamento.getId());
        return service.atualizar(lancamentoConvertido);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar(@PathVariable("id") Long id) {
        service.deletar(service.buscarPorId(id));
    }

    @GetMapping
    public List<Lancamento> buscar(
            @RequestParam(value = "descricao", required = false) String descricao,
            @RequestParam(value = "mes", required = false) Integer mes,
            @RequestParam(value = "ano", required = false) Integer ano,
            @RequestParam(value = "tipo", required = false) String tipo,
            @RequestParam("usuario") Long idUsuario
    ) {
        Lancamento lancamentoFiltro = Lancamento
                .builder()
                .descricao(descricao)
                .mes(mes)
                .ano(ano)
                .tipo(TipoLancamento.valueOf(tipo))
                .usuario(usuarioService.buscarPorId(idUsuario))
                .build();
        return service.buscar(lancamentoFiltro);
    }

    @PutMapping("{id}/atualizaStatus")
    public Lancamento atualizarStatus(@RequestBody AtualizaStatusDTO dto,@PathVariable("id") Long id){
        Lancamento lancamento = service.buscarPorId(id);
        lancamento.setStatus(StatusLancamento.valueOf(dto.getStatus()));
        return service.atualizar(lancamento);
    }



    private Lancamento converter(LancamentoDTO dto) {
        if (dto.getTipo() == null){
            throw new RegraNegocioException("O lançamento deve ter um tipo");
        }
        if (dto.getStatus() == null){
            dto.setStatus(StatusLancamento.PENDENTE.toString());
        }
        return Lancamento.builder()
                .id(dto.getId())
                .descricao(dto.getDescricao())
                .mes(dto.getMes())
                .ano(dto.getAno())
                .valor(dto.getValor())
                .usuario(usuarioService.buscarPorId(dto.getUsuario()))
                .tipo(TipoLancamento.valueOf(dto.getTipo()))
                .status(StatusLancamento.valueOf(dto.getStatus()))
                .build();
    }
}
