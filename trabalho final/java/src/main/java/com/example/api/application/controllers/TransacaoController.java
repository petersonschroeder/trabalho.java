package com.example.api.application.controllers;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.api.application.dto.TransacaoDTO;
import com.example.api.application.models.Transacao;
import com.example.api.application.services.implementations.TransacaoServiceFactory;

@RestController
@RequestMapping("/transacao")
public class TransacaoController {

    private final TransacaoServiceFactory transacaoServiceFactory;

    public TransacaoController(TransacaoServiceFactory transacaoServiceFactory) {
        this.transacaoServiceFactory = transacaoServiceFactory;
    }

    @GetMapping
    public ResponseEntity<List<Transacao>> listarTransacoes(@RequestParam(required = false) String banco) {
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
    }

    @PostMapping
    public ResponseEntity<Transacao> criarTransacao(@RequestBody TransacaoDTO dto) {
        try {
            Transacao transacao = transacaoServiceFactory.salvarTransacao(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(transacao);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping
    public ResponseEntity<Void> deletarTodasTransacoes(@RequestParam(required = false) String banco) {
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).build();
    }

    @DeleteMapping("/periodo")
    public ResponseEntity<?> deletarPorPeriodo(
        @RequestParam String banco,
        @RequestParam String senha,
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataInicial,
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataFinal) {

    if (!"BD1@123".equals(senha)) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Senha inválida");
    }

    try {
        transacaoServiceFactory.deletarPorPeriodoBanco(banco, dataInicial, dataFinal, senha);
        return ResponseEntity.noContent().build();
    } catch (RuntimeException e) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro interno");
    }
}

}
