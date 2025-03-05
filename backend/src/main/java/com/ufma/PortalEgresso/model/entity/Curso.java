package com.ufma.PortalEgresso.model.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "CURSO")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Curso implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @EqualsAndHashCode.Include
    private UUID id_curso;

    @ManyToOne
    @JoinColumn(name = "id_coordenador")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Coordenador coordenador;

    @Column(nullable = false)
    @EqualsAndHashCode.Include
    private String nome;

    @Column(nullable = false)
    @EqualsAndHashCode.Include
    private String nivel;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @OneToMany(mappedBy = "curso", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<CursoEgresso> egressos = new HashSet<>();

}
