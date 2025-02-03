package com.ufma.PortalEgresso.model.entitys.DTOs;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
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
    @Size(min = 8, message = "Senha deve ter ao menos 8 caracteres")
    private String senha;
    private String descricao;
    private String foto;
    private String linkedin;
    private String instagram;
    private String curriculo;
}
