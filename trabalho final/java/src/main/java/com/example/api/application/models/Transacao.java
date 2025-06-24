package com.example.api.application.models;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Transacao {

    private Long id;
    private BigDecimal valor;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime dataHora;

    private String nome;
    private String cpf;

    public Transacao(com.example.api.application.dto.TransacaoDTO dto) {
        this.valor = dto.getValor();
        this.dataHora = dto.getDataHora();
        this.nome = dto.getNome();
        this.cpf = dto.getCpf();
    }
}
