package com.ufma.PortalEgresso.models.repo;

import com.ufma.PortalEgresso.models.Curso;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;
import java.util.UUID;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
public class CursoTest {
    @Autowired
    CursoRepo repo;

    @Test
    public void deveVerificarSalvarCurso() {
        //cenário
        Curso curso = new Curso();
        curso.setNome("teste nome");
        curso.setNivel("teste nivel");

        //ação
        Curso salvo = repo.save(curso); //salva?

        //Verificação
        Assertions.assertNotNull(salvo);
        Assertions.assertEquals(curso.getId_curso(), salvo.getId_curso());
        Assertions.assertEquals(curso.getCoordenador(), salvo.getCoordenador());
        Assertions.assertEquals(curso.getNome(), salvo.getNome());
        Assertions.assertEquals(curso.getEgressos(), salvo.getEgressos());
        Assertions.assertEquals(curso.getNivel(), salvo.getNivel());
    }

    @Test
    public void deveVerificarRemoverCurso() {
        //cenário
        Curso curso = new Curso();
        curso.setNome("teste nome");
        curso.setNivel("teste nivel");

        //ação
        Curso salvo = repo.save(curso); //salva
        UUID id = salvo.getId_curso();
        repo.deleteById(id);

        //verificação
        Optional<Curso> temp = repo.findById(id);
        Assertions.assertFalse(temp.isPresent());
    }
}
