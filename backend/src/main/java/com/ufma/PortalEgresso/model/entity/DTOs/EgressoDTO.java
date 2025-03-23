package com.ufma.PortalEgresso.model.entity.DTOs;

import com.ufma.PortalEgresso.model.entity.Cargo;
import com.ufma.PortalEgresso.model.entity.CursoEgresso;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EgressoDTO {
    private String nome;
    @Email(message = "E-mail inválido. Verifique o formato")
    private String email;
    private String senha;
    private String descricao;
    private String foto;
    private String linkedin;
    private String github;
    private String curriculo;
    private Set<CursoEgresso> cursos;
    private Set<Cargo> cargos;
}
