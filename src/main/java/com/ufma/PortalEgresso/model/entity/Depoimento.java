package com.ufma.PortalEgresso.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "DEPOIMENTO")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Depoimento implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id_depoimento;

    @ManyToOne
    @JoinColumn(name = "id_egresso")
    private Egresso egresso;

    @Column
    private String texto;
    @Column
    private Date data;


}
