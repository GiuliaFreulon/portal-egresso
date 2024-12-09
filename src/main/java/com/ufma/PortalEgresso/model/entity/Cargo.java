package com.ufma.PortalEgresso.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name = "CARGO")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Cargo implements Serializable{
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id_cargo;

    @ManyToOne
    @JoinColumn(name = "id_egresso")
    private Egresso egresso;

    @Column(nullable = false)
    private String descricao;
    @Column(nullable = false)
    private String local;
    @Column(nullable = false)
    private Integer anoInicio;
    @Column
    private Integer anoFim;


}
