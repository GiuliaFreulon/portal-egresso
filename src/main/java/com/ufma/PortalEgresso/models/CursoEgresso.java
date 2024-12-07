package com.ufma.PortalEgresso.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name = "CURSO_EGRESSO")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CursoEgresso implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id_curso_egresso;

    @ManyToOne
    @JoinColumn(name = "id_egresso")
    private Egresso egresso;

    @ManyToOne
    @JoinColumn(name = "id_curso")
    private Curso curso;

    @Column(nullable = false)
    private Integer anoInicio;

    @Column
    private Integer anoFim;
}
