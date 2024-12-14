package com.ufma.PortalEgresso.model.repo;

import com.ufma.PortalEgresso.model.entity.CursoEgresso;
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
public class CursoEgressoTest {
    @Autowired
    CursoEgressoRepo repo;

    @Test
    @Transactional
    public void deveVerificarSalvarCursoEgresso() {
        //cenário
        CursoEgresso cursoEgresso = new CursoEgresso();
        cursoEgresso.setAnoInicio(2000);

        //ação
        CursoEgresso salvo = repo.save(cursoEgresso); //salva?

        //Verificação
        Assertions.assertNotNull(salvo);
        Assertions.assertEquals(cursoEgresso.getIdCursoEgresso(), salvo.getIdCursoEgresso());
        Assertions.assertEquals(cursoEgresso.getEgresso(), salvo.getEgresso());
        Assertions.assertEquals(cursoEgresso.getCurso(), salvo.getCurso());
        Assertions.assertEquals(cursoEgresso.getAnoInicio(), salvo.getAnoInicio());
        Assertions.assertEquals(cursoEgresso.getAnoFim(), salvo.getAnoFim());
    }

    @Test
    @Transactional
    public void deveVerificarRemoverCursoEgresso() {
        //cenário
        CursoEgresso cursoEgresso = new CursoEgresso();
        cursoEgresso.setAnoInicio(2000);

        //ação
        CursoEgresso salvo = repo.save(cursoEgresso); //salva
        UUID id = salvo.getIdCursoEgresso();
        repo.deleteById(id);

        //verificação
        Optional<CursoEgresso> temp = repo.findById(id);
        Assertions.assertFalse(temp.isPresent());
    }
}
