package com.ufma.PortalEgresso.model.repo;

import com.ufma.PortalEgresso.model.entity.Cargo;
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
public class CargoTest {
    @Autowired
    CargoRepo repo;

    @Test
    @Transactional
    public void deveVerificarSalvarCargo() {
        //cenário
        Cargo cargo = new Cargo();
        cargo.setDescricao("teste descrição cargo");
        cargo.setLocal("teste local");
        cargo.setAnoInicio(2000);

        //ação
        Cargo salvo = repo.save(cargo);

        //Verificação
        Assertions.assertNotNull(salvo);
        Assertions.assertEquals(cargo.getId_cargo(), salvo.getId_cargo());
        Assertions.assertEquals(cargo.getEgresso(), salvo.getEgresso());
        Assertions.assertEquals(cargo.getDescricao(), salvo.getDescricao());
        Assertions.assertEquals(cargo.getLocal(), salvo.getLocal());
        Assertions.assertEquals(cargo.getAnoInicio(), salvo.getAnoInicio());
        Assertions.assertEquals(cargo.getAnoFim(), salvo.getAnoFim());
    }

    @Test
    @Transactional
    public void deveVerificarRemoverCargo() {
        //cenário
        Cargo cargo = new Cargo();
        cargo.setDescricao("teste descrição cargo");
        cargo.setLocal("teste local");
        cargo.setAnoInicio(2000);

        //ação
        Cargo salvo = repo.save(cargo);
        UUID id = salvo.getId_cargo();
        repo.deleteById(id);

        //verificação
        Optional<Cargo> temp = repo.findById(id);
        Assertions.assertFalse(temp.isPresent());
    }
}
