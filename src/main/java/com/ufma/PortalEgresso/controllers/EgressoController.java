package com.ufma.PortalEgresso.controllers;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ufma.PortalEgresso.exception.RegraNegocioRunTime;
import com.ufma.PortalEgresso.model.entity.Egresso;
import com.ufma.PortalEgresso.model.entity.DTOs.EgressoDTO;
import com.ufma.PortalEgresso.service.EgressoService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/api/egressos")
public class EgressoController {
    @Autowired
    EgressoService service;
    // -------------------- AUTENTICAÇÃO ---------------------
    @PostMapping("/login")
    public ResponseEntity login(@RequestBody EgressoDTO request) {
        try {
            service.efetuarLogin(request.getEmail(), request.getSenha());
            return ResponseEntity.ok().body(true);
        } catch (RegraNegocioRunTime e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // -------------------- endpoints CRUD ---------------------
    // TODO validação email e senha

    @PostMapping("/salvar")
    public ResponseEntity salvar(@RequestBody EgressoDTO request) {
        Egresso egresso = Egresso.builder().
                                nome(request.getNome())
                                .email(request.getEmail())
                                .senha(request.getSenha())
                                .descricao(request.getDescricao())
                                .foto(request.getFoto())
                                .linkedin(request.getLinkedin())
                                .instagram(request.getInstagram())
                                .curriculo(request.getCurriculo())
                                .build();
        try {
            Egresso salvo = service.salvar(egresso);
            return new ResponseEntity<>(salvo, HttpStatus.CREATED);
        } catch (RegraNegocioRunTime e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }    
    }
    
    @PutMapping("{id}")
    public ResponseEntity atualizar(@PathVariable UUID id, @RequestBody EgressoDTO request) {
        try {
            Egresso egresso = Egresso.builder().
                                id_egresso(id)
                                .nome(request.getNome())
                                .email(request.getEmail())
                                .senha(request.getSenha())
                                .descricao(request.getDescricao())
                                .foto(request.getFoto())
                                .linkedin(request.getLinkedin())
                                .instagram(request.getInstagram())
                                .curriculo(request.getCurriculo())
                                .build();
            Egresso atualizado = service.atualizar(egresso);
            return ResponseEntity.ok(atualizado);
        } catch (RegraNegocioRunTime e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("{id}")
    public ResponseEntity buscar(@PathVariable UUID id) {
        try {
            Egresso egresso = service.buscarPorId(id);
            return ResponseEntity.ok(egresso);
        
        } catch (RegraNegocioRunTime e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
    

    @DeleteMapping("{id}")
    public ResponseEntity deletar(@PathVariable UUID id){
        try {
            service.deletar(id);
            return ResponseEntity.ok(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioRunTime e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    //-------------------- endpoints de consulta ---------------------

}
