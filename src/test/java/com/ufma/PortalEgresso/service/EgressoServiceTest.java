package com.ufma.PortalEgresso.service;

import com.ufma.PortalEgresso.exception.RegraNegocioRunTime;
import com.ufma.PortalEgresso.model.entity.Egresso;
import com.ufma.PortalEgresso.model.repo.EgressoRepo;
import jakarta.validation.Valid;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
public class EgressoServiceTest {

    @Autowired
    EgressoService service;

    @Autowired
    EgressoRepo repo;

    @Test
    public void deveGerarErroAoTentarSalvarSemNome(){
        Egresso egresso = new Egresso();
        egresso.setNome("");
        egresso.setDescricao("Descricao teste");
        egresso.setEmail("teste@teste.com");

        Assertions.assertThrows(RegraNegocioRunTime.class, () -> service.salvar(egresso), "O nome do egresso deve estar preenchido");
    }

    @Test
    public void deveGerarErroAoTentarSalvarSemEmail(){
        Egresso egresso = new Egresso();
        egresso.setNome("teste nome");
        egresso.setDescricao("Descricao teste");
        egresso.setEmail("");

        Assertions.assertThrows(RegraNegocioRunTime.class, () -> service.salvar(egresso), "O e-mail do egresso deve estar preenchido");
    }

    @Test
    public void deveGerarErroAoTentarSalvarEmailJaCadastrado(){
        Egresso egresso1 = new Egresso();
        egresso1.setNome("teste nome1");
        egresso1.setDescricao("Descricao teste1");
        egresso1.setEmail("teste@teste.com");

        Egresso egresso2 = new Egresso();
        egresso2.setNome("teste nome2");
        egresso2.setDescricao("Descricao teste2");
        egresso2.setEmail("teste@teste.com");

        service.salvar(egresso1);
        Assertions.assertThrows(RegraNegocioRunTime.class, () -> service.salvar(egresso2), "O e-mail já está cadastrado. Por favor, utilize um e-mail diferente");
    }

//    @Test
//    public void deveGerarErroAoTentarSalvarEmailInvalido(){
//        Egresso egresso = new Egresso();
//        egresso.setNome("teste nome");
//        egresso.setDescricao("Descricao teste");
//        egresso.setEmail("teste");
//
//        Assertions.assertThrows(RegraNegocioRunTime.class, () -> service.salvar(egresso), "E-mail inválido. Verifique o formato");
//    }
}
