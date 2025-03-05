package com.ufma.PortalEgresso.model.entity.DTOs;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class CursoDTO {
    private String nome;
    private String nivel;
}
