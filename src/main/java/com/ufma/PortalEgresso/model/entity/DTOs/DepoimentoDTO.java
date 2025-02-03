package com.ufma.PortalEgresso.model.entitys.DTOs;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
public class DepoimentoDTO {
    private String texto;
    private LocalDate data;
}
