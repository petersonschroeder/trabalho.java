package com.example.api.application.dto;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PeriodoDTO {

    private LocalDateTime dataInicial;
    private LocalDateTime dataFinal;
    
}
