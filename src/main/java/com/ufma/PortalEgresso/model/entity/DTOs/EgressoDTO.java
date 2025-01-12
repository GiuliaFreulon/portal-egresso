package com.ufma.PortalEgresso.model.entity.DTOs;

import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class EgressoDTO {
    private String nome;
    @Email(message = "E-mail inválido. Verifique o formato")
    private String email;
    private String senha;
    private String descricao;
    private String foto;
    private String linkedin;
    private String instagram;
    private String curriculo;
}
