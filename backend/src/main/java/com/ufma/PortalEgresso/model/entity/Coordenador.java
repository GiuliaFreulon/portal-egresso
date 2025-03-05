package com.ufma.PortalEgresso.model.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "COORDENADOR")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Coordenador implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @EqualsAndHashCode.Include
    private UUID id_coordenador;

    @Column(nullable = false, unique = true)
    @EqualsAndHashCode.Include
    private String login;

    @Column(nullable = false)
    @EqualsAndHashCode.Include
    private String senha;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @OneToMany(mappedBy = "coordenador", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<Curso> cursos = new HashSet<>();

}
