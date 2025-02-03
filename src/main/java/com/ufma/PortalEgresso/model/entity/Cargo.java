package com.ufma.PortalEgresso.model.entitys;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name = "CARGO")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Cargo implements Serializable{
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @EqualsAndHashCode.Include
    private UUID id_cargo;

    @ManyToOne
    @JoinColumn(name = "id_egresso")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
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
