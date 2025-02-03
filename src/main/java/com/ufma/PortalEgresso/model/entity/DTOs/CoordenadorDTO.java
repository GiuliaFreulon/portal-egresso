package com.ufma.PortalEgresso.model.entitys.DTOs;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class CoordenadorDTO {
    private String login;
    private String senha;
    private String tipo;
}
