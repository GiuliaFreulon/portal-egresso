package com.ufma.PortalEgresso.model.repo;

import com.ufma.PortalEgresso.model.entity.Oportunidade;
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
public class OportunidadeTest {
    @Autowired
    OportunidadeRepo repo;

    @Test
    @Transactional
    public void deveVerificarSalvarOportunidade() {
        //cenário
        Oportunidade oportunidade = new Oportunidade();
        oportunidade.setTitulo("titulo teste");
        oportunidade.setDescricao("descricao teste");

        //ação
        Oportunidade salvo = repo.save(oportunidade); //salva

        //Verificação
        Assertions.assertNotNull(salvo);
        Assertions.assertEquals(oportunidade.getTitulo(), salvo.getTitulo());
        Assertions.assertEquals(oportunidade.getDescricao(), salvo.getDescricao());
    }

    @Test
    @Transactional
    public void deveVerificarRemoverOportunidade() {
        //cenário
        Oportunidade oportunidade = new Oportunidade();
        oportunidade.setTitulo("titulo teste");
        oportunidade.setDescricao("descricao teste");

        //ação
        Oportunidade salvo = repo.save(oportunidade); //salva
        UUID id = salvo.getId_oportunidade();
        repo.deleteById(salvo.getId_oportunidade());

        //verificação
        Optional<Oportunidade> temp = repo.findById(id);
        Assertions.assertFalse(temp.isPresent());
    }
}
