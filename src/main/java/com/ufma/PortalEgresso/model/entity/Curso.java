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
@Table(name = "CURSO")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Curso implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id_curso;

    @ManyToOne
    @JoinColumn(name = "id_coordenador")
    private Coordenador coordenador;

    @Column(nullable = false)
    private String nome;
    @Column(nullable = false)
    private String nivel;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @OneToMany(mappedBy = "curso", fetch = FetchType.LAZY)
    private Set<CursoEgresso> egressos = new HashSet<>();

}
