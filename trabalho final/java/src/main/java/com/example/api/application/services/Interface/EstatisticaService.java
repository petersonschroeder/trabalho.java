package com.example.api.application.services.Interface;

import java.time.LocalDateTime;
import java.util.Optional;

import com.example.api.application.dto.EstatisticaDTO;
import com.example.api.application.dto.PeriodoDTO;
import com.example.api.application.models.Transacao;

public interface EstatisticaService {
    EstatisticaDTO calcularEstatisticas();
    EstatisticaDTO calcularEstatisticasPorPeriodo(PeriodoDTO periodo);
    EstatisticaDTO removerPorPeriodo(LocalDateTime dataInicial, LocalDateTime dataFinal);
    Optional<Transacao> findLastTransacao();

    
}
