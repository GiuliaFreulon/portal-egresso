package com.ufma.PortalEgresso.model.repo;

import com.ufma.PortalEgresso.model.entity.Egresso;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
public class EgressoTest {
    @Autowired
    EgressoRepo repo;

    @Test
    @Transactional
    public void deveVerificarSalvarEgresso() {
        //cenário
        Egresso egresso = new Egresso();
        egresso.setNome("teste nome");
        egresso.setEmail("testesave@teste.com");
        egresso.setSenha("senha teste");
        egresso.setDescricao("teste descrição");
        egresso.setFoto("teste foto");
        egresso.setLinkedin("teste Linkedin");
        egresso.setGithub("teste Instagram");
        egresso.setCurriculo("teste Curriculo");

        //ação
        Egresso salvo = repo.save(egresso); //salva?

        //Verificação
        Assertions.assertNotNull(salvo);
        Assertions.assertEquals(egresso.getNome(), salvo.getNome());
        Assertions.assertEquals(egresso.getEmail(), salvo.getEmail());
        Assertions.assertEquals(egresso.getSenha(), salvo.getSenha());
        Assertions.assertEquals(egresso.getDescricao(), salvo.getDescricao());
        Assertions.assertEquals(egresso.getFoto(), salvo.getFoto());
        Assertions.assertEquals(egresso.getLinkedin(), salvo.getLinkedin());
        Assertions.assertEquals(egresso.getGithub(), salvo.getGithub());
        Assertions.assertEquals(egresso.getCurriculo(), salvo.getCurriculo());
    }

    @Test
    @Transactional
    public void deveVerificarRemoverEgresso() {
        //cenário
        Egresso egresso = new Egresso();
        egresso.setNome("teste nome");
        egresso.setEmail("testesave@teste.com");
        egresso.setSenha("senha teste");
        egresso.setDescricao("teste descrição");
        egresso.setFoto("teste foto");
        egresso.setLinkedin("teste Linkedin");
        egresso.setGithub("teste Instagram");
        egresso.setCurriculo("teste Curriculo");

        //ação
        Egresso salvo = repo.save(egresso); //salva
        UUID id = salvo.getId_egresso();
        repo.deleteById(salvo.getId_egresso());

        //verificação
        Optional<Egresso> temp = repo.findById(id);
        Assertions.assertFalse(temp.isPresent());
    }
}
