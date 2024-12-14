package com.ufma.PortalEgresso.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name = "CARGO")
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Cargo implements Serializable{
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @EqualsAndHashCode.Include
    private UUID id_cargo;

    @ManyToOne
    @JoinColumn(name = "id_egresso")
    @EqualsAndHashCode.Exclude
    private Egresso egresso;

    @Column(nullable = false)
    @EqualsAndHashCode.Include
    private String descricao;

    @Column(nullable = false)
    @EqualsAndHashCode.Include
    private String local;

    @Column(nullable = false)
    @EqualsAndHashCode.Include
    private Integer anoInicio;

    @Column
    @EqualsAndHashCode.Include
    private Integer anoFim;

}
