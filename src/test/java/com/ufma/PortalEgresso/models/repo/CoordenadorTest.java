package com.ufma.PortalEgresso.models.repo;

import com.ufma.PortalEgresso.models.Coordenador;
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
public class CoordenadorTest {
    @Autowired
    CoordenadorRepo repo;

    @Test
    public void deveVerificarSalvarCoordenador() {
        //cenário
        Coordenador coordenador = new Coordenador();
        coordenador.setLogin("teste login");
        coordenador.setSenha("teste senha");
        coordenador.setTipo("teste tipo");

        //ação
        Coordenador salvo = repo.save(coordenador); //salva?

        //Verificação
        Assertions.assertNotNull(salvo);
        Assertions.assertEquals(coordenador.getId_coordenador(), salvo.getId_coordenador());
        Assertions.assertEquals(coordenador.getCursos(), salvo.getCursos());
        Assertions.assertEquals(coordenador.getLogin(), salvo.getLogin());
        Assertions.assertEquals(coordenador.getSenha(), salvo.getSenha());
        Assertions.assertEquals(coordenador.getTipo(), salvo.getTipo());
    }

    @Test
    public void deveVerificarRemoverCoordenador() {
        //cenário
        Coordenador coordenador = new Coordenador();
        coordenador.setLogin("teste login");
        coordenador.setSenha("teste senha");
        coordenador.setTipo("teste tipo");

        //ação
        Coordenador salvo = repo.save(coordenador); //salva
        UUID id = salvo.getId_coordenador();
        repo.deleteById(id);

        //verificação
        Optional<Coordenador> temp = repo.findById(id);
        Assertions.assertFalse(temp.isPresent());
    }
}
