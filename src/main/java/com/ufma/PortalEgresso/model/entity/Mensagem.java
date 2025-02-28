package com.ufma.PortalEgresso.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name = "MENSAGEM")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Mensagem implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @EqualsAndHashCode.Include
    private UUID id_mensagem;

    @ManyToOne
    @JoinColumn(name = "id_egresso")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Egresso egresso;

    @Column
    @EqualsAndHashCode.Include
    private String texto;

    @ManyToOne
    @JoinColumn(name = "id_discussao")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Discussao discussao;
}
