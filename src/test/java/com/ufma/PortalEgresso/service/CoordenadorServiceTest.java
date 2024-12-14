package com.ufma.PortalEgresso.service;

import com.ufma.PortalEgresso.model.entity.Coordenador;
import com.ufma.PortalEgresso.model.entity.Egresso;
import com.ufma.PortalEgresso.model.repo.CoordenadorRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
public class CoordenadorServiceTest {

    @Autowired
    CoordenadorService service;

    @Autowired
    CoordenadorRepo repo;

    @Test
    @Transactional
    public void deveVerificarSalvarOCoordenador() {
        Coordenador coordenador = new Coordenador();
        coordenador.setLogin("login teste");
        coordenador.setSenha("senha teste");
        coordenador.setTipo("tipo teste");

        Coordenador salvo = service.salvar(coordenador);

        //Verificação
        Assertions.assertNotNull(salvo);
        Assertions.assertEquals(coordenador.getLogin(), salvo.getLogin());
        Assertions.assertEquals(coordenador.getSenha(), salvo.getSenha());
        Assertions.assertEquals(coordenador.getTipo(), salvo.getTipo());

    }

}
