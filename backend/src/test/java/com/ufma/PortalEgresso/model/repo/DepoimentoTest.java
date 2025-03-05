package com.ufma.PortalEgresso.model.repo;

import com.ufma.PortalEgresso.model.entity.Depoimento;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
public class DepoimentoTest {
    @Autowired
    DepoimentoRepo repo;

    @Test
    @Transactional
    public void deveVerificarSalvarDepoimento(){
        //cenário
        Depoimento depoimento = new Depoimento();
        depoimento.setTexto("teste texto depoimento");
        depoimento.setData(LocalDate.of(2024, 12, 6));

        //ação
        Depoimento salvo = repo.save(depoimento);

        //Verificação
        Assertions.assertNotNull(salvo);
        Assertions.assertEquals(depoimento.getId_depoimento(), salvo.getId_depoimento());
        Assertions.assertEquals(depoimento.getEgresso(), salvo.getEgresso());
        Assertions.assertEquals(depoimento.getTexto(), salvo.getTexto());
        Assertions.assertEquals(depoimento.getData(), salvo.getData());
    }

    @Test
    @Transactional
    public void deveVerificarRemoverDepoimento(){
        //cenário
        Depoimento depoimento = new Depoimento();
        depoimento.setTexto("teste texto depoimento");
        depoimento.setData(LocalDate.of(2024, 12, 6));

        //ação
        Depoimento salvo = repo.save(depoimento);
        UUID id = salvo.getId_depoimento();
        repo.deleteById(id);

        //verificação
        Optional<Depoimento> temp = repo.findById(id);
        Assertions.assertFalse(temp.isPresent());
    }
}
