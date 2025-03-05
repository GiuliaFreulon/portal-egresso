package com.ufma.PortalEgresso.controller;

import com.ufma.PortalEgresso.exception.BuscaVaziaRunTime;
import com.ufma.PortalEgresso.exception.RegraNegocioRunTime;
import com.ufma.PortalEgresso.model.entity.DTOs.MensagemDTO;
import com.ufma.PortalEgresso.model.entity.Depoimento;
import com.ufma.PortalEgresso.model.entity.Mensagem;
import com.ufma.PortalEgresso.service.DiscussaoService;
import com.ufma.PortalEgresso.service.EgressoService;
import com.ufma.PortalEgresso.service.MensagemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/mensagem")
public class MensagemController {
    @Autowired
    MensagemService mensagemService;
    @Autowired
    private EgressoService egressoService;
    @Autowired
    private DiscussaoService discussaoService;

    // -------------------- endpoints CRUD ---------------------

    @PostMapping("{idDiscussao}/{idEgresso}")
    public ResponseEntity salvar(@PathVariable UUID idDiscussao, @PathVariable UUID idEgresso, @RequestBody MensagemDTO request) {
        Mensagem mensagem = Mensagem.builder()
                .egresso(egressoService.buscarPorId(idEgresso).get())
                .discussao(discussaoService.buscarPorId(idDiscussao).get())
                .texto(request.getTexto())
                .build();
        try {
            Mensagem salvo = mensagemService.salvar(mensagem);
            return new ResponseEntity<>(salvo, HttpStatus.CREATED);
        } catch (RegraNegocioRunTime e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity deletar(@PathVariable UUID id){
        try {
            mensagemService.deletar(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (RegraNegocioRunTime e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{idDiscussao}")
    public ResponseEntity listarMensagensOrdenadas(@PathVariable UUID idDiscussao) {
        try {
            List<Mensagem> lista = mensagemService.listarMensagensOrdenadas(idDiscussao);
            return ResponseEntity.ok(lista);

        } catch (RegraNegocioRunTime | BuscaVaziaRunTime e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
