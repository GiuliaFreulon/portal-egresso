package com.ufma.PortalEgresso.controller;

import com.ufma.PortalEgresso.exception.BuscaVaziaRunTime;
import com.ufma.PortalEgresso.exception.RegraNegocioRunTime;
import com.ufma.PortalEgresso.model.entity.Cargo;
import com.ufma.PortalEgresso.model.entity.DTOs.CargoDTO;
import com.ufma.PortalEgresso.service.CargoService;
import com.ufma.PortalEgresso.service.EgressoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/cargo")
public class CargoController {
    @Autowired
    CargoService cargoService;
    @Autowired
    private EgressoService egressoService;

    // -------------------- endpoints CRUD ---------------------

    @PostMapping("{id}")
    public ResponseEntity salvar(@PathVariable UUID id, @RequestBody CargoDTO request) {
        Cargo cargo = Cargo.builder()
                .egresso(egressoService.buscarPorId(id).get())
                .descricao(request.getDescricao())
                .local(request.getLocal())
                .anoInicio(request.getAnoInicio())
                .anoFim(request.getAnoFim())
                .build();
        try {
            Cargo salvo = cargoService.salvar(cargo);
            return new ResponseEntity<>(salvo, HttpStatus.CREATED);
        } catch (RegraNegocioRunTime e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    public ResponseEntity atualizar(@PathVariable UUID id, @RequestBody CargoDTO request) {
        try {
            // Recupera o cargo existente do banco de dados
            Cargo cargoExistente = cargoService.buscarPorId(id).get();

            if (cargoExistente == null) {
                return ResponseEntity.notFound().build(); // Retorna 404 se o cargo não for encontrado
            }

            // Atualiza os campos passados no DTO
            if (request.getDescricao() != null && !request.getDescricao().trim().isEmpty()) {
                cargoExistente.setDescricao(request.getDescricao());
            }
            if (request.getLocal() != null && !request.getLocal().trim().isEmpty()) {
                cargoExistente.setLocal(request.getLocal());
            }
            if (request.getAnoInicio() != null) {
                cargoExistente.setAnoInicio(request.getAnoInicio());
            }
            if (request.getAnoFim() != null) {
                cargoExistente.setAnoFim(request.getAnoFim());
            }

            // Atualiza o cargo no banco de dados
            Cargo atualizado = cargoService.atualizar(cargoExistente);

            return ResponseEntity.ok(atualizado);
        } catch (RegraNegocioRunTime e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/buscarPorId/{id}")
    public ResponseEntity buscarPorId(@PathVariable UUID id) {
        try {
            Cargo cargo = cargoService.buscarPorId(id).get();
            return ResponseEntity.ok(cargo);

        } catch (RegraNegocioRunTime e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/listarTodos")
    public ResponseEntity listarTodos() {
        try {
            List<Cargo> lista = cargoService.listarTodos();
            return ResponseEntity.ok(lista);

        } catch (RegraNegocioRunTime | BuscaVaziaRunTime e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity deletar(@PathVariable UUID id){
        try {
            cargoService.deletar(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (RegraNegocioRunTime e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
