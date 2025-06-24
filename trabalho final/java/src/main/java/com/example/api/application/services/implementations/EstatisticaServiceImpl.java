package com.example.api.application.services.implementations;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.example.api.application.dto.EstatisticaDTO;
import com.example.api.application.dto.PeriodoDTO;
import com.example.api.application.models.Transacao;
import com.example.api.application.repository.TransacaoRepository;
import com.example.api.application.services.Interface.EstatisticaService;

@Service
public class EstatisticaServiceImpl implements EstatisticaService {

    private final TransacaoRepository repository;

    @Autowired
    public EstatisticaServiceImpl(TransacaoRepository repository) {
        this.repository = repository;
    }

    @Override
    public EstatisticaDTO calcularEstatisticas() {
        LocalDateTime  limite = LocalDateTime.now();
        List<Transacao> ultimasTransacoes = repository.findByDataHoraAfter(limite.minusSeconds(60));


        BigDecimal sum = BigDecimal.ZERO;
        BigDecimal min = null;
        BigDecimal max = null;

        for (Transacao transacao : ultimasTransacoes) {
            BigDecimal valor = transacao.getValor();
            sum = sum.add(valor);

            if (min == null || valor.compareTo(min) < 0) {
                min = valor;
            }

            if (max == null || valor.compareTo(max) > 0) {
                max = valor;
            }
        }

        int count = ultimasTransacoes.size();
        BigDecimal avg = count > 0 ? sum.divide(BigDecimal.valueOf(count), 3, RoundingMode.HALF_UP) : BigDecimal.ZERO;

        EstatisticaDTO estatistica = new EstatisticaDTO();
        estatistica.setCount(count);
        estatistica.setSum(sum);
        estatistica.setAvg(avg);
        estatistica.setMin(min != null ? min : BigDecimal.ZERO);
        estatistica.setMax(max != null ? max : BigDecimal.ZERO);


        return estatistica;
    }


    @Override
    public EstatisticaDTO calcularEstatisticasPorPeriodo(PeriodoDTO periodo){
        List<Transacao> transacoes = repository.findByDataHoraBetween(periodo.getDataInicial(), periodo.getDataFinal());

        BigDecimal sum = BigDecimal.ZERO;
        BigDecimal min = null;
        BigDecimal max = null;

        for(Transacao transacao : transacoes){
            BigDecimal valor = transacao.getValor();
            sum = sum.add(valor);

            if(min == null || min.compareTo(min) < 0 ){
                min = valor;
            }

            if(max == null || max.compareTo(max) > 0) {
                max = valor;
            }
        }

            int count = transacoes.size();

            BigDecimal avg = count > 0 ? sum.divide(BigDecimal.valueOf(count), 3, RoundingMode.HALF_UP) : BigDecimal.ZERO;

            EstatisticaDTO estatistica = new EstatisticaDTO();
            estatistica.setCount(count);
            estatistica.setSum(sum);
            estatistica.setAvg(avg);
            estatistica.setMin(min != null ? min : BigDecimal.ZERO);
            estatistica.setMax(max != null ? max : BigDecimal.ZERO);

            return estatistica;
        
    }

    @Override
    public Optional<Transacao> findLastTransacao() {
        Optional<Transacao> ultimaTransacao = repository.findLastTransacao();

        ultimaTransacao.ifPresentOrElse(
            transacao -> {
                System.out.println("Última transação: " + transacao.getValor() + " em " + transacao.getDataHora());
                }, 
                () -> System.out.println("Nenhuma transação encontrada")
            );
        
            return ultimaTransacao;
    }

    @Override
    public EstatisticaDTO removerPorPeriodo(LocalDateTime dataInicial, LocalDateTime dataFinal) {
        List<Transacao> transacoes = repository.findByDataHoraBetween(dataInicial, dataFinal);
    
        if (transacoes.isEmpty()) {
            return new EstatisticaDTO();
        }
    
        EstatisticaDTO estatistica = new EstatisticaDTO();
        estatistica.setCount(transacoes.size());
        estatistica.setSum(transacoes.stream().map(Transacao::getValor).reduce(BigDecimal.ZERO, BigDecimal::add));
        estatistica.setAvg(estatistica.getSum().divide(BigDecimal.valueOf(estatistica.getCount()), BigDecimal.ROUND_HALF_UP));
        estatistica.setMin(transacoes.stream().map(Transacao::getValor).min(BigDecimal::compareTo).orElse(BigDecimal.ZERO));
        estatistica.setMax(transacoes.stream().map(Transacao::getValor).max(BigDecimal::compareTo).orElse(BigDecimal.ZERO));
    
    
        repository.clearDataBetween(dataInicial, dataFinal);
    
        return estatistica;
    }
    
    
    
    

}