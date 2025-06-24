package com.example.api.application.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransacaoDTO {
    private BigDecimal valor;
    private LocalDateTime dataHora;
    private String nome;
    private String cpf;
    private String banco;
    private String senha;
}
