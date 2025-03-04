package com.ufma.PortalEgresso.service;

import com.ufma.PortalEgresso.exception.RegraNegocioRunTime;
import com.ufma.PortalEgresso.model.entity.Discussao;
import com.ufma.PortalEgresso.model.entity.Egresso;
import com.ufma.PortalEgresso.model.entity.Mensagem;
import com.ufma.PortalEgresso.model.repo.DiscussaoRepo;
import com.ufma.PortalEgresso.model.repo.EgressoRepo;
import com.ufma.PortalEgresso.model.repo.MensagemRepo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
public class MensagemServiceTest {
    @Autowired
    private MensagemService service;

    @Autowired
    private MensagemRepo repo;

    @Autowired
    private EgressoRepo egressoRepo;

    @Autowired
    private DiscussaoRepo discussaoRepo;

    @PersistenceContext
    private EntityManager entityManager;

    @Test
    @Transactional
    public void deveGerarErroAoTentarSalvarSemEgresso() {
        Mensagem mensagem = new Mensagem();
        mensagem.setTexto("texto teste");

        Exception exception = Assertions.assertThrows(RegraNegocioRunTime.class, () -> service.salvar(mensagem), "A mensagem deve estar associado a um egresso");
        Assertions.assertEquals("A mensagem deve estar associado a um egresso", exception.getMessage());
    }

    @Test
    @Transactional
    public void deveGerarErroAoTentarSalvarMensagemVazia() {
        Mensagem mensagem = new Mensagem();
        mensagem.setTexto("");

        Exception exception = Assertions.assertThrows(RegraNegocioRunTime.class, () -> service.salvar(mensagem), "A mensagem deve estar preenchida");
        Assertions.assertEquals("A mensagem deve estar preenchida", exception.getMessage());
    }

    @Test
    @Transactional
    public void deveVerificarSalvarMensagem() {
        Egresso egresso = egressoRepo.findById(UUID.fromString("e2ff521f-168e-4337-a9e8-2109ccee0531")).orElse(null);
        assert egresso != null;

        Discussao discussao = discussaoRepo.findById(UUID.fromString("550e8400-e29b-41d4-a716-446655440000")).orElse(null);
        assert discussao != null;

        Mensagem mensagem = new Mensagem();
        mensagem.setEgresso(egresso);
        mensagem.setTexto("texto teste");
        mensagem.setDiscussao(discussao);
        Mensagem salvo = service.salvar(mensagem);

        //Verificação
        Assertions.assertNotNull(salvo);
        Assertions.assertEquals(mensagem.getTexto(), salvo.getTexto());
        Assertions.assertEquals(mensagem.getDiscussao(), salvo.getDiscussao());
        Assertions.assertEquals(mensagem.getEgresso(), salvo.getEgresso());
    }

    @Test
    @Transactional
    public void deveListarMensagensOrdenadas() {
        UUID idDiscussao = UUID.fromString("550e8400-e29b-41d4-a716-446655440000");

        List<Mensagem> mensagens = service.listarMensagensOrdenadas(idDiscussao);
        List<Mensagem> mensagensEsperadas = entityManager
                .createQuery("SELECT m FROM Mensagem m WHERE m.discussao.id_discussao = :idDiscussao ORDER BY m.dataEnvio ASC", Mensagem.class)
                .getResultList();

        Assertions.assertEquals(mensagens, mensagensEsperadas);
    }

    @Test
    @Transactional
    public void deveDeletarMensagem() {
        Mensagem mensagem = repo.findById(UUID.fromString("07e83479-07bd-4ac4-aa7b-68dd888228ca")).orElse(null);
        assert mensagem != null;

        service.deletar(mensagem.getId_mensagem());
        Assertions.assertFalse(repo.existsById(mensagem.getId_mensagem()));
    }
}
