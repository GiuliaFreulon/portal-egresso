package com.ufma.PortalEgresso.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "DEPOIMENTO")
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Depoimento implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @EqualsAndHashCode.Include
    private UUID id_depoimento;

    @ManyToOne
    @JoinColumn(name = "id_egresso")
    @EqualsAndHashCode.Exclude
    private Egresso egresso;

    @Column
    @EqualsAndHashCode.Include
    private String texto;

    @Column
    @EqualsAndHashCode.Include
    private Date data;

}
