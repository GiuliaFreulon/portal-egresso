package com.ufma.PortalEgresso.model.entity.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EgressoDTO {
    private String nome;
    private String email;
    private String senha;
    private String descricao;
    private String foto;
    private String linkedin;
    private String instagram;
    private String curriculo;
}
