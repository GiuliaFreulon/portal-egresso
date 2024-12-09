package com.ufma.PortalEgresso.model.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "COORDENADOR")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Coordenador implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id_coordenador;

    @Column(nullable = false)
    private String login;

    @Column(nullable = false)
    private String senha;

    @Column(nullable = false)
    private String tipo;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @OneToMany(mappedBy = "coordenador", fetch = FetchType.LAZY)
    private Set<Curso> cursos = new HashSet<>();

}
