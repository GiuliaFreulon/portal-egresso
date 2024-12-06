package com.ufma.PortalEgresso.models.repo;

import com.ufma.PortalEgresso.models.Cargo;
import com.ufma.PortalEgresso.models.Egresso;
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
public class CargoTest {
    @Autowired
    CargoRepo repo;
    @Autowired
    EgressoRepo egressoRepo;

    @Test
    public void deveVerificarSalvarCargo() {
        //cenário
        Egresso egresso = new Egresso();
        egresso.setNome("teste nome");
        egresso.setEmail("teste@teste.com");
        egresso.setDescricao("teste descrição egresso");
        egresso.setFoto("teste foto");
        egresso.setLinkedin("teste Linkedin");
        egresso.setInstagram("teste Instagram");
        egresso.setCurriculo("teste Curriculo");


        Egresso egressoSalvo = egressoRepo.save(egresso); //salva egresso
        UUID egressoID = egressoSalvo.getId_egresso();
        Optional<Egresso> EgressoTemp = egressoRepo.findById(egressoID);

        Cargo cargo = new Cargo();
        cargo.setEgresso(EgressoTemp.get());
        cargo.setDescricao("teste descrição cargo");
        cargo.setLocal("teste local");
        cargo.setAnoInicio(2000);

        //ação
        Cargo cargoSalvo = repo.save(cargo);


        //Verificação
        Assertions.assertNotNull(cargoSalvo);
        Assertions.assertEquals(cargo.getId_cargo(), cargoSalvo.getId_cargo());
        Assertions.assertEquals(cargo.getEgresso(), cargoSalvo.getEgresso());
        Assertions.assertEquals(cargo.getDescricao(), cargoSalvo.getDescricao());
        Assertions.assertEquals(cargo.getLocal(), cargoSalvo.getLocal());
        Assertions.assertEquals(cargo.getAnoInicio(), cargoSalvo.getAnoInicio());
        Assertions.assertEquals(cargo.getAnoFim(), cargoSalvo.getAnoFim());

    }

    @Test
    public void deveVerificarRemoverCargo() {
        //cenário
        Egresso egresso = new Egresso();
        egresso.setNome("teste nome");
        egresso.setEmail("teste@teste.com");
        egresso.setDescricao("teste descrição egresso");
        egresso.setFoto("teste foto");
        egresso.setLinkedin("teste Linkedin");
        egresso.setInstagram("teste Instagram");
        egresso.setCurriculo("teste Curriculo");


        Egresso egressoSalvo = egressoRepo.save(egresso); //salva egresso
        UUID egressoID = egressoSalvo.getId_egresso();
        Optional<Egresso> EgressoTemp = egressoRepo.findById(egressoID);

        Cargo cargo = new Cargo();
        cargo.setEgresso(EgressoTemp.get());
        cargo.setDescricao("teste descrição cargo");
        cargo.setLocal("teste local");
        cargo.setAnoInicio(2000);

        //ação
        Cargo cargoSalvo = repo.save(cargo);
        UUID cargoId = cargoSalvo.getId_cargo();
        repo.deleteById(cargoId);

        //verificação
        Optional<Cargo> cargoTemp = repo.findById(cargoId);
        Assertions.assertFalse(cargoTemp.isPresent());
    }
}
