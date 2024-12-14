package com.ufma.PortalEgresso.model.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
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
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Egresso implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @EqualsAndHashCode.Include
    private UUID id_egresso;

    @Column(nullable = false)
    @EqualsAndHashCode.Include
    private String nome;

    @Column(nullable = false, unique = true)
    @EqualsAndHashCode.Include
    @Email
    private String email;

    @Column(nullable = false)
    @EqualsAndHashCode.Include
    private String senha;

    @Column
    @EqualsAndHashCode.Include
    private String descricao;

    @Column
    @EqualsAndHashCode.Include
    private String foto;

    @Column
    @EqualsAndHashCode.Include
    private String linkedin;

    @Column
    @EqualsAndHashCode.Include
    private String instagram;

    @Column
    @EqualsAndHashCode.Include
    private String curriculo;


    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @OneToMany(mappedBy = "egresso", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @EqualsAndHashCode.Exclude
    private Set<Cargo> cargos = new HashSet<>();

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @OneToMany(mappedBy = "egresso", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @EqualsAndHashCode.Exclude
    private Set<Depoimento> depoimentos = new HashSet<>();

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @OneToMany(mappedBy = "egresso", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @EqualsAndHashCode.Exclude
    private Set<CursoEgresso> cursos = new HashSet<>();

}
