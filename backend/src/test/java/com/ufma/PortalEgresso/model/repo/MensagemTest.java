package com.ufma.PortalEgresso.model.repo;

import com.ufma.PortalEgresso.model.entity.Mensagem;
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
public class MensagemTest {
    @Autowired
    MensagemRepo repo;

    @Test
    @Transactional
    public void deveVerificarSalvarMensagem() {
        //cenário
        Mensagem mensagem = new Mensagem();
        mensagem.setTexto("texto teste");

        //ação
        Mensagem salvo = repo.save(mensagem); //salva

        //Verificação
        Assertions.assertNotNull(salvo);
        Assertions.assertEquals(mensagem.getTexto(), salvo.getTexto());
    }

    @Test
    @Transactional
    public void deveVerificarRemoverMensagem() {
        //cenário
        Mensagem mensagem = new Mensagem();
        mensagem.setTexto("texto teste");

        //ação
        Mensagem salvo = repo.save(mensagem); //salva
        UUID id = salvo.getId_mensagem();
        repo.deleteById(salvo.getId_mensagem());

        //verificação
        Optional<Mensagem> temp = repo.findById(id);
        Assertions.assertFalse(temp.isPresent());
    }
}
