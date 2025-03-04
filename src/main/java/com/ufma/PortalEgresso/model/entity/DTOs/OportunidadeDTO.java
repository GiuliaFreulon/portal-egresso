package com.ufma.PortalEgresso.model.entity.DTOs;

import com.ufma.PortalEgresso.model.entity.ENUMs.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class OportunidadeDTO {
    private String titulo;
    private String descricao;
    private Status status;
}
