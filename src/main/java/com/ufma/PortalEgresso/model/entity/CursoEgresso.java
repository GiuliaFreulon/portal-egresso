package com.ufma.PortalEgresso.model.entity;

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
    private UUID idCursoEgresso;

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
