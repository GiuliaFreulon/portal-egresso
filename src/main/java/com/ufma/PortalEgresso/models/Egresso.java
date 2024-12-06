package com.ufma.PortalEgresso.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "EGRESSO")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Egresso implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id_egresso;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false, unique = true)
    @Email
    private String email;

    @Column
    private String descricao;
    @Column
    private String foto;
    @Column
    private String linkedin;
    @Column
    private String instagram;
    @Column
    private String curriculo;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @OneToMany(mappedBy = "egresso", fetch = FetchType.LAZY)
    private Set<Cargo> cargos = new HashSet<>();

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @OneToMany(mappedBy = "egresso", fetch = FetchType.LAZY)
    private Set<Depoimento> depoimentos = new HashSet<>();

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @OneToMany(mappedBy = "egresso", fetch = FetchType.LAZY)
    private Set<CursoEgresso> cursos = new HashSet<>();

}
