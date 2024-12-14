package com.ufma.PortalEgresso.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name = "CURSO_EGRESSO")
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class CursoEgresso implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @EqualsAndHashCode.Include
    private UUID idCursoEgresso;

    @ManyToOne
    @JoinColumn(name = "id_egresso")
    @EqualsAndHashCode.Exclude
    private Egresso egresso;

    @ManyToOne
    @JoinColumn(name = "id_curso")
    @EqualsAndHashCode.Exclude
    private Curso curso;

    @Column(nullable = false)
    @EqualsAndHashCode.Include
    private Integer anoInicio;

    @Column
    @EqualsAndHashCode.Include
    private Integer anoFim;
}
