package com.ufma.PortalEgresso.models.repo;

import com.ufma.PortalEgresso.models.Depoimento;
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

    @Test
    public void deveVerificarSalvarDepoimento() throws ParseException {
        //cenário
        Depoimento depoimento = new Depoimento();
        depoimento.setTexto("teste texto depoimento");
        SimpleDateFormat formatter = new SimpleDateFormat("dd-mm-yyyy", Locale.ENGLISH);
        String dateInString = "06-12-2024";
        Date data = formatter.parse(dateInString);
        depoimento.setData(data);

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
    public void deveVerificarRemoverDepoimento() throws ParseException {
        //cenário
        Depoimento depoimento = new Depoimento();
        depoimento.setTexto("teste texto depoimento");
        SimpleDateFormat formatter = new SimpleDateFormat("dd-mm-yyyy", Locale.ENGLISH);
        String dateInString = "06-12-2024";
        Date data = formatter.parse(dateInString);
        depoimento.setData(data);

        //ação
        Depoimento salvo = repo.save(depoimento);
        UUID id = salvo.getId_depoimento();
        repo.deleteById(id);

        //verificação
        Optional<Depoimento> temp = repo.findById(id);
        Assertions.assertFalse(temp.isPresent());
    }
}
