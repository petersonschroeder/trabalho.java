package com.example.api.application.services.implementations;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.api.application.dto.TransacaoDTO;
import com.example.api.application.models.Transacao;
import com.example.api.application.repository.TransacaoRepository;
import com.example.api.application.services.Interface.TransacaoService;

@Service
public class TransacaoServiceImpl implements TransacaoService {

    private final TransacaoRepository repository;

    @Autowired
    public TransacaoServiceImpl(TransacaoRepository repository) {
        this.repository = repository;
    }

    @Override
    public Transacao salvarTransacao(TransacaoDTO dto) {

        if (dto == null || dto.getValor() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Campos obrigatórios ausentes");
        }
    
        if (dto.getValor().compareTo(BigDecimal.ZERO) < 0) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Valor não pode ser negativo.");
        }
        Transacao transacao = new Transacao();
        
        transacao.setDataHora(LocalDateTime.now());
        
        transacao.setValor(dto.getValor());
        
        repository.adicionarTransacao(transacao);
    
        return transacao;
    }
    

    @Override
    public List<Transacao> listarTransacoes() {
        return repository.listarTransacoes();
    }

    public void deletarTodasTransacoes() {
    repository.deletarTodasTransacoes();
}

    @Override
    public void deletarPorPeriodo(LocalDateTime inicio, LocalDateTime fim, String senha) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deletarPorPeriodo'");
    }


}
