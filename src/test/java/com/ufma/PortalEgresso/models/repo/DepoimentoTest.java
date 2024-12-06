package com.ufma.PortalEgresso.models.repo;

import com.ufma.PortalEgresso.models.Depoimento;
import com.ufma.PortalEgresso.models.Egresso;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Optional;
import java.util.UUID;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
public class DepoimentoTest {
    @Autowired
    DepoimentoRepo repo;
    @Autowired
    EgressoRepo egressoRepo;

    @Test
    public void deveVerificarSalvarDepoimento() throws ParseException {
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

        Depoimento depoimento = new Depoimento();
        depoimento.setEgresso(EgressoTemp.get());
        depoimento.setTexto("teste texto depoimento");

        SimpleDateFormat formatter = new SimpleDateFormat("dd-mm-yyyy", Locale.ENGLISH);
        String dateInString = "06-12-2024";
        Date data = formatter.parse(dateInString);
        depoimento.setData(data);

        //ação
        Depoimento depoimentoSalvo = repo.save(depoimento);


        //Verificação
        Assertions.assertNotNull(depoimentoSalvo);
        Assertions.assertEquals(depoimento.getId_depoimento(), depoimentoSalvo.getId_depoimento());
        Assertions.assertEquals(depoimento.getEgresso(), depoimentoSalvo.getEgresso());
        Assertions.assertEquals(depoimento.getTexto(), depoimentoSalvo.getTexto());
        Assertions.assertEquals(depoimento.getData(), depoimentoSalvo.getData());
    }

    @Test
    public void deveVerificarRemoverDepoimento() throws ParseException {
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

        Depoimento depoimento = new Depoimento();
        depoimento.setEgresso(EgressoTemp.get());
        depoimento.setTexto("teste texto depoimento");

        SimpleDateFormat formatter = new SimpleDateFormat("dd-mm-yyyy", Locale.ENGLISH);
        String dateInString = "06-12-2024";
        Date data = formatter.parse(dateInString);
        depoimento.setData(data);

        //ação
        Depoimento depoimentoSalvo = repo.save(depoimento);
        UUID depoimentoId = depoimentoSalvo.getId_depoimento();
        repo.deleteById(depoimentoSalvo.getId_depoimento());

        //verificação
        Optional<Depoimento> cargoTemp = repo.findById(depoimentoId);
        Assertions.assertFalse(cargoTemp.isPresent());
    }
}
