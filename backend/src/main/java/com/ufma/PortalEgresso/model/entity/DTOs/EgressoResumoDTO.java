package com.ufma.PortalEgresso.model.entity.DTOs;

import java.util.UUID;

public class EgressoResumoDTO {
    private UUID id;
    private String nome;
    private String foto;

    // Construtor
    public EgressoResumoDTO(UUID id, String nome, String foto) {
        this.id = id;
        this.nome = nome;
        this.foto = foto;
    }

    // Getters (obrigatório para serialização JSON)
    public UUID getId() { return id; }
    public String getNome() { return nome; }
    public String getFoto() { return foto; }
}