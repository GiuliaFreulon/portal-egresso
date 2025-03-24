package com.ufma.PortalEgresso.model.entity;

import com.ufma.PortalEgresso.model.entity.ENUMs.Status;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name = "OPORTUNIDADE")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Oportunidade implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @EqualsAndHashCode.Include
    private UUID id_oportunidade;

    @Column(nullable = false)
    @EqualsAndHashCode.Include
    private String titulo;

    @ManyToOne
    @JoinColumn(name = "id_egresso")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Egresso egresso;

    @Column(nullable = false, length = 1000)
    @EqualsAndHashCode.Include
    private String descricao;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    @EqualsAndHashCode.Include
    private Status status = Status.AGUARDANDO;

}
