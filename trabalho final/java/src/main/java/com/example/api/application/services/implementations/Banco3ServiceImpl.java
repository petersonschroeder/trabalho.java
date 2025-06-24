package com.example.api.application.services.implementations;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.api.application.dto.TransacaoDTO;
import com.example.api.application.models.Transacao;
import com.example.api.application.repository.TransacaoRepository;
import com.example.api.application.services.Interface.TransacaoService;

@Service
public class Banco3ServiceImpl implements TransacaoService {

    private final TransacaoRepository repository;

    public Banco3ServiceImpl(TransacaoRepository repository) {
        this.repository = repository;
    }

    @Override
    public Transacao salvarTransacao(TransacaoDTO dto) {
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
        repository.deletarTodasTransacoes(); // comportamento padrão
    }

    // Método adicional específico para Banco3: exige senha
    public void deletarPorPeriodo(LocalDateTime inicio, LocalDateTime fim, String senha) {
        if (!"BD3@789".equals(senha)) {
            throw new RuntimeException("Senha inválida para Banco 3");
        }
        repository.clearDataBetween(inicio, fim);
    }
}
