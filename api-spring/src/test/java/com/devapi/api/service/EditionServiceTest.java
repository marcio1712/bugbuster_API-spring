package com.devapi.api.service;

import com.devapi.api.domain.dtos.EditionDTO;
import com.devapi.api.service.classes.EditionService;
import com.devapi.api.domain.model.*;
import com.devapi.api.utils.EditionStatus;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class EditionServiceTest {

    private EditionService editionService = new EditionService();
    private EditionDTO editionDTO;
    private User user;
    private Event event;
    private UserKey userKey;

    @BeforeEach
    void setUp(){
        user = new User(1L, "thaleco1", "Thales1", "thaleco1@email.com", "admin");
        userKey = new UserKey(1L, "100000", user);
        event = new Event(1L, "FestaThaleco1", "FT1", "Primeira festa", user);

        editionDTO = new EditionDTO();
        editionDTO.setId(1L);
        editionDTO.setNumber(1);
        editionDTO.setYear(2025);
        editionDTO.setInitialDate(Date.valueOf("2025-02-01"));
        editionDTO.setFinalDate(Date.valueOf("2025-02-07"));
        editionDTO.setCity("Niteroi");
        editionDTO.setStatus(EditionStatus.CONFIRMED);
        editionDTO.setEvent(event);
    }

    @Test
    @DisplayName("Simulando edição que já aconteceu.")
    void testeRetornaCONCLUDEDseDataMaiorQueDataFinal() {
        Edition edition = new Edition();
        edition.setFinalDate(Date.valueOf("2025-01-25"));
        System.out.println("[Retorna Concluido] - Atual status da edição: " + edition.getStatus());
        edition.setStatus(EditionStatus.CONFIRMED);

        assertEquals(EditionStatus.CONCLUDED, editionService.verifyEditionStatus(edition, Date.valueOf("2025-01-26"))); //Análise de Valor Limite ( Dia compara Dia + 1 )
    }

    @Test
    @DisplayName("Simulando edição que ainda vai acontecer.")
    void testeRetornaCONDFIRMEDseDataMenorQueDataFinal() {
        Edition edition = new Edition();
        edition.setFinalDate(Date.valueOf("2025-01-27"));
        System.out.println("[Retorna Confirmado] - Atual status da edição: " + edition.getStatus());
        edition.setStatus(EditionStatus.CONCLUDED);

        assertEquals(EditionStatus.CONFIRMED, editionService.verifyEditionStatus(edition, Date.valueOf("2025-1-26"))); //Análise de Valor Limite ( Dia compara Dia - 1 )
    }

    @Test
    @DisplayName("Simulando edição acontecendo na data atual.")
    void testeRetornaCONFIRMEDseDataIgualaDataFinal() {
        Edition edition = new Edition();
        edition.setFinalDate(Date.valueOf("2025-01-26"));
        System.out.println("[Retorna Confirmado] - Atual status da edição: " + edition.getStatus());
        edition.setStatus(EditionStatus.CONCLUDED);

        assertEquals(EditionStatus.CONFIRMED, editionService.verifyEditionStatus(edition, Date.valueOf("2025-01-26"))); //Análise de Valor Limite ( Dia compara Dia )
    }

    @Test
    @DisplayName("Simulando edição cancelada com data que já passou.")
    void testeRetornaCANCELEDseStatusSetadoCancelado_1() {
        Edition edition = new Edition();
        edition.setFinalDate(Date.valueOf("2025-01-25"));
        System.out.println("[Retorna Cancelado] - Atual status da edição: " + edition.getStatus());
        edition.setStatus(EditionStatus.CANCELED);

        assertEquals(EditionStatus.CANCELED, editionService.verifyEditionStatus(edition, Date.valueOf("2025-01-26"))); //Classe de Equivalência (CANCELED)
    }

    @Test
    @DisplayName("Simulando edição cancelada com data futura.")
    void testeRetornaCANCELEDseStatusSetadoCancelado_2() {
        Edition edition = new Edition();
        edition.setFinalDate(Date.valueOf("2025-01-27"));
        System.out.println("[Retorna Cancelado] - Atual status da edição: " + edition.getStatus());
        edition.setStatus(EditionStatus.CANCELED);

        assertEquals(EditionStatus.CANCELED, editionService.verifyEditionStatus(edition, Date.valueOf("2025-01-26"))); //Classe de Equivalência (CANCELED)
    }

    @Test
    @DisplayName("Simulando edição sem status setado.")
    void testeRetornaCANCELEDseStatusIgualNull() {
        Edition edition = new Edition();
        edition.setFinalDate(Date.valueOf("2025-01-27"));
        System.out.println("[Retorna Cancelado] - Atual status da edição: " + edition.getStatus());

        //A requisição post não deixa criar uma edição com status null (status é setado como CONFIRMED)
        //Teste apenas para validar logica

        assertEquals(EditionStatus.CANCELED, editionService.verifyEditionStatus(edition, Date.valueOf("2025-01-26")));
    }

    @Test
    @DisplayName("Simulando post de edicao")
    void testeConverteDTOparaEntidade(){

        Edition edition = EditionService.convertToEntity(editionDTO);

        assertEquals(editionDTO.getNumber(), edition.getNumber());
        assertEquals(editionDTO.getYear(), edition.getYear());
        assertEquals(editionDTO.getInitialDate(), edition.getInitialDate());
        assertEquals(editionDTO.getFinalDate(), edition.getFinalDate());
        assertEquals(editionDTO.getCity(), edition.getCity());
        assertEquals(editionDTO.getStatus(), edition.getStatus());
    }

}
