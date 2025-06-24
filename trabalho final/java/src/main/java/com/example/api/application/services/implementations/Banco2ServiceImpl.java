package com.example.api.application.services.implementations;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.api.application.dto.TransacaoDTO;
import com.example.api.application.models.Transacao;
import com.example.api.application.repository.TransacaoRepository;
import com.example.api.application.services.Interface.TransacaoService;

@Service
public class Banco2ServiceImpl implements TransacaoService {

    private final TransacaoRepository repository;

    public Banco2ServiceImpl(TransacaoRepository repository) {
        this.repository = repository;
    }

    @Override
    public Transacao salvarTransacao(TransacaoDTO dto) {
        // Aceita apenas transações com data no ano de 2025
        if (dto.getDataHora() == null || dto.getDataHora().getYear() != 2025) {
            return null;
        }

        Transacao transacao = new Transacao(dto);
        repository.adicionarTransacao(transacao);
        return transacao;
    }

    @Override
    public List<Transacao> listarTransacoes() {
        // Ignora transações acima de R$1.000,00
        return repository.listarTransacoes().stream()
                .filter(t -> t.getValor().compareTo(new java.math.BigDecimal("1000.00")) <= 0)
                .collect(Collectors.toList());
    }

    @Override
    public void deletarTodasTransacoes() {
        throw new UnsupportedOperationException("Use deletarPorPeriodo com senha 'BD2@456'");
    }

    // Método adicional para deleção com senha (seria usado por DELETE /transacao/periodo)
    public void deletarPorPeriodo(LocalDateTime inicio, LocalDateTime fim, String senha) {
        if (!"BD2@456".equals(senha)) {
            throw new RuntimeException("Senha inválida para Banco 2");
        }
        repository.clearDataBetween(inicio, fim);
    }
}
