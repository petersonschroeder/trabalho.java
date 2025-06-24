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
public class Banco1ServiceImpl implements TransacaoService {

    private final TransacaoRepository repository;

    public Banco1ServiceImpl(TransacaoRepository repository) {
        this.repository = repository;
    }

    @Override
    public Transacao salvarTransacao(TransacaoDTO dto) {
        if (dto.getValor().compareTo(new java.math.BigDecimal("5.00")) < 0) {
            return null; // Ignora valores abaixo de R$5,00
        }

        Transacao transacao = new Transacao(dto);
        repository.adicionarTransacao(transacao);
        return transacao;
    }

    @Override
    public List<Transacao> listarTransacoes() {
        return repository.listarTransacoes();
    }

    @Override
    public void deletarTodasTransacoes() {
        LocalDateTime limite = LocalDateTime.now().minusYears(3);
        List<Transacao> antigas = repository.listarTransacoes().stream()
                .filter(t -> t.getDataHora().isBefore(limite))
                .collect(Collectors.toList());
        antigas.forEach(t -> repository.listarTransacoes().remove(t));
    }

    @Override
    public void deletarPorPeriodo(LocalDateTime inicio, LocalDateTime fim, String senha) {
        // Verifique se a senha está correta
        if (!"BD1@123".equals(senha)) {
            throw new RuntimeException("Senha incorreta");
        }

        // Filtra as transações dentro do período informado
        List<Transacao> transacoesParaDeletar = repository.listarTransacoes().stream()
                .filter(t -> t.getDataHora().isAfter(inicio) && t.getDataHora().isBefore(fim))
                .collect(Collectors.toList());

        // Remove as transações encontradas
        transacoesParaDeletar.forEach(t -> repository.listarTransacoes().remove(t));
    }
}
