package com.example.api.application.repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.example.api.application.models.Transacao;

@Repository
public class TransacaoRepository {

    private final List<Transacao> lista = new ArrayList<>();
    private static long contadorId = 1;

    public void adicionarTransacao(Transacao transacao) {
        transacao.setId(contadorId++);
        lista.add(transacao);
    }

    public List<Transacao> listarTransacoes() {
        return new ArrayList<>(lista);
    }

    public void deletarTodasTransacoes() {
        lista.clear();
    }

    public List<Transacao> findByDataHoraAfter(LocalDateTime limite) {
        List<Transacao> recentes = new ArrayList<>();
        for (Transacao transacao : lista) {
            if (transacao.getDataHora() != null && (transacao.getDataHora().isEqual(limite) || transacao.getDataHora().isAfter(limite))) {
                recentes.add(transacao);
            }
        }
        return recentes;
    }

    public List<Transacao> findByDataHoraBetween(LocalDateTime dataInicial, LocalDateTime dataFinal) {
        return lista.stream()
            .filter(t -> {
                if (t.getDataHora() == null) return false;
                return (t.getDataHora().isEqual(dataInicial) || t.getDataHora().isAfter(dataInicial))
                    && (t.getDataHora().isEqual(dataFinal) || t.getDataHora().isBefore(dataFinal));
            })
            .collect(Collectors.toList());
    }

    public Optional<Transacao> findLastTransacao() {
        return lista.stream()
            .filter(t -> t.getDataHora() != null)
            .max((t1, t2) -> t1.getDataHora().compareTo(t2.getDataHora()));
    }

    public void clearDataBetween(LocalDateTime dataInicial, LocalDateTime dataFinal) {
        lista.removeIf(t -> {
            if (t.getDataHora() == null) return false;
            return (t.getDataHora().isEqual(dataInicial) || t.getDataHora().isAfter(dataInicial))
                && (t.getDataHora().isEqual(dataFinal) || t.getDataHora().isBefore(dataFinal));
        });
    }
}
