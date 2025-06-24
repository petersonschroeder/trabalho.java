
package com.seuprojeto.service;

import com.seuprojeto.dto.*;
import com.seuprojeto.model.Transacao;
import com.seuprojeto.repository.TransacaoRepository;

import java.util.List;

public class Banco3ServiceImpl implements TransacaoService {

    private TransacaoRepository repository = new TransacaoRepository();

    @Override
    public void registrarTransacao(RequisicaoComBancoDTO dto) {
        validarCampos(dto);
        repository.salvar(dto.toTransacao());
    }

    @Override
    public void limparTransacoes(RequisicaoComBancoDTO dto) {
        repository.limparTudo();
    }

    @Override
    public void excluirPorPeriodo(PeriodoRequestDTO dto) {
        if (!"BD3-789".equals(dto.getSenha())) {
            throw new RuntimeException("Senha inválida para Banco3.");
        }
        repository.excluirPorPeriodo(dto.getInicio(), dto.getFim());
    }

    @Override
    public EstatisticaResponseDTO estatisticasRecentes(RequisicaoComBancoDTO dto) {
        List<Transacao> transacoes = repository.buscarUltimos30Dias();
        return EstatisticaResponseDTO.of(transacoes);
    }

    @Override
    public EstatisticaResponseDTO estatisticasPorPeriodo(PeriodoRequestDTO dto) {
        List<Transacao> transacoes = repository.buscarPorPeriodo(dto.getInicio(), dto.getFim());
        return EstatisticaResponseDTO.of(transacoes);
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
