package com.ufma.PortalEgresso.model.entitys.DTOs;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class CursoEgressoDTO {
    private Integer anoInicio;
    private Integer anoFim;
}
