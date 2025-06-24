package com.example.api.application.services.implementations;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.api.application.dto.TransacaoDTO;
import com.example.api.application.models.Transacao;
import com.example.api.application.services.Interface.TransacaoService;

@Service
public class TransacaoServiceFactory implements TransacaoService {

    private final Banco1ServiceImpl banco1Service;
    private final Banco2ServiceImpl banco2Service;
    private final Banco3ServiceImpl banco3Service;

    public TransacaoServiceFactory(Banco1ServiceImpl banco1Service,
                                   Banco2ServiceImpl banco2Service,
                                   Banco3ServiceImpl banco3Service) {
        this.banco1Service = banco1Service;
        this.banco2Service = banco2Service;
        this.banco3Service = banco3Service;
    }

    private TransacaoService getServiceByBanco(String banco) {
        if (banco == null) {
            throw new IllegalArgumentException("Banco não informado");
        }
        switch (banco.toLowerCase()) {
            case "banco1":
                return banco1Service;
            case "banco2":
                return banco2Service;
            case "banco3":
                return banco3Service;
            default:
                throw new IllegalArgumentException("Banco inválido: " + banco);
        }
    }

    @Override
    public Transacao salvarTransacao(TransacaoDTO dto) {
        return getServiceByBanco(dto.getBanco()).salvarTransacao(dto);
    }

    @Override
    public List<Transacao> listarTransacoes() {
        throw new UnsupportedOperationException("Listar transações deve ser feita por banco");
    }

    @Override
    public void deletarTodasTransacoes() {
        throw new UnsupportedOperationException("Deletar todas as transações não suportado na fábrica");
    }

    @Override
    public void deletarPorPeriodo(LocalDateTime inicio, LocalDateTime fim, String senha) {
        throw new UnsupportedOperationException("Use o método específico por banco");
    }

    public void deletarPorPeriodoBanco(String banco, LocalDateTime inicio, LocalDateTime fim, String senha) {
        if (!"BD1@123".equals(senha)) {
            throw new RuntimeException("Senha inválida");
        }
        getServiceByBanco(banco).deletarPorPeriodo(inicio, fim, senha);
    }
}
