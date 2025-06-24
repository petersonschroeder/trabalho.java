
package com.seuprojeto.controller;

import com.seuprojeto.dto.*;
import com.seuprojeto.service.*;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transacao")
public class TransacaoController {

    @PostMapping
    public void registrar(@RequestBody RequisicaoComBancoDTO dto) {
        getService(dto.getBanco()).registrarTransacao(dto);
    }

    @DeleteMapping
    public void limpar(@RequestBody RequisicaoComBancoDTO dto) {
        getService(dto.getBanco()).limparTransacoes(dto);
    }

    @DeleteMapping("/periodo")
    public void excluirPorPeriodo(@RequestBody PeriodoRequestDTO dto) {
        getService(dto.getBanco()).excluirPorPeriodo(dto);
    }

    @GetMapping("/estatistica")
    public EstatisticaResponseDTO estatisticasRecentes(@RequestBody RequisicaoComBancoDTO dto) {
        return getService(dto.getBanco()).estatisticasRecentes(dto);
    }

    @PostMapping("/periodo")
    public EstatisticaResponseDTO estatisticasPorPeriodo(@RequestBody PeriodoRequestDTO dto) {
        return getService(dto.getBanco()).estatisticasPorPeriodo(dto);
    }

    @GetMapping("/ultima")
    public TransacaoResponseDTO consultarUltima(@RequestBody RequisicaoComBancoDTO dto) {
        return getService(dto.getBanco()).consultarUltima(dto);
    }

    private TransacaoService getService(String banco) {
        switch (banco.toLowerCase()) {
            case "banco1":
                return new Banco1ServiceImpl();
            case "banco2":
                return new Banco2ServiceImpl();
            case "banco3":
                return new Banco3ServiceImpl();
            default:
                throw new RuntimeException("Banco inválido: " + banco);
        }
    }
}
