package com.ufma.PortalEgresso.model.repo;

import com.ufma.PortalEgresso.model.entity.Discussao;
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
public class DiscussaoTest {
    @Autowired
    DiscussaoRepo repo;

    @Test
    @Transactional
    public void deveVerificarSalvarDiscussao() {
        //cenário
        Discussao discussao = new Discussao();
        discussao.setTitulo("titulo teste");

        //ação
        Discussao salvo = repo.save(discussao); //salva

        //Verificação
        Assertions.assertNotNull(salvo);
        Assertions.assertEquals(discussao.getTitulo(), salvo.getTitulo());
    }

    @Test
    @Transactional
    public void deveVerificarRemoverDiscussao() {
        //cenário
        Discussao discussao = new Discussao();
        discussao.setTitulo("titulo teste");

        //ação
        Discussao salvo = repo.save(discussao); //salva
        UUID id = salvo.getId_discussao();
        repo.deleteById(salvo.getId_discussao());

        //verificação
        Optional<Discussao> temp = repo.findById(id);
        Assertions.assertFalse(temp.isPresent());
    }
}
