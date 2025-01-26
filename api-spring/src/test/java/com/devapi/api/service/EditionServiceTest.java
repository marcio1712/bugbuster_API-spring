package com.devapi.api.service;

import com.devapi.api.service.classes.EditionService;
import com.devapi.api.domain.model.*;
import com.devapi.api.utils.EditionStatus;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EditionServiceTest {

    private EditionService editionService = new EditionService();

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

}
