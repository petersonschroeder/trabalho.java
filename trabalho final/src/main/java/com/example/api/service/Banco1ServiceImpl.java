
package com.seuprojeto.service;

import com.seuprojeto.dto.*;
import com.seuprojeto.model.Transacao;
import com.seuprojeto.repository.TransacaoRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class Banco1ServiceImpl implements TransacaoService {

    private TransacaoRepository repository = new TransacaoRepository();

    @Override
    public void registrarTransacao(RequisicaoComBancoDTO dto) {
        validarCampos(dto);
        repository.salvar(dto.toTransacao());
    }

    @Override
    public void limparTransacoes(RequisicaoComBancoDTO dto) {
        repository.limparTransacoesMaisAntigasQue(LocalDate.now().minusYears(3));
    }

    @Override
    public void excluirPorPeriodo(PeriodoRequestDTO dto) {
        if (!"BD1-123".equals(dto.getSenha())) {
            throw new RuntimeException("Senha inválida para Banco1.");
        }
        repository.excluirPorPeriodo(dto.getInicio(), dto.getFim());
    }

    @Override
    public EstatisticaResponseDTO estatisticasRecentes(RequisicaoComBancoDTO dto) {
        List<Transacao> transacoes = repository.buscarUltimos30Dias();
        List<Transacao> filtradas = transacoes.stream()
            .filter(t -> t.getValor() >= 5.0)
            .collect(Collectors.toList());
        return EstatisticaResponseDTO.of(filtradas);
    }

    @Override
    public EstatisticaResponseDTO estatisticasPorPeriodo(PeriodoRequestDTO dto) {
        List<Transacao> transacoes = repository.buscarPorPeriodo(dto.getInicio(), dto.getFim());
        List<Transacao> filtradas = transacoes.stream()
            .filter(t -> t.getValor() >= 5.0)
            .collect(Collectors.toList());
        return EstatisticaResponseDTO.of(filtradas);
    }

    @Override
    public TransacaoResponseDTO consultarUltima(RequisicaoComBancoDTO dto) {
        return TransacaoResponseDTO.of(repository.buscarUltima());
    }

    private void validarCampos(RequisicaoComBancoDTO dto) {
        if (dto.getNome() == null || dto.getNome().trim().isEmpty()) {
            throw new RuntimeException("Nome não pode ser vazio.");
        }
        if (!dto.getCpf().matches("\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}")) {
            throw new RuntimeException("CPF inválido. Deve ter o formato XXX.XXX.XXX-XX");
        }
    }
}
