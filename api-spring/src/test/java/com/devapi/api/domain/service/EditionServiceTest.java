package com.devapi.api.domain.service;

import com.devapi.api.domain.model.Edition;
import com.devapi.api.service.classes.EditionService;
import com.devapi.api.utils.EditionStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.Date;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class EditionServiceTest {

    private final Edition mockEdition = mock(Edition.class);
    EditionService editionService = new EditionService();

    /***
     * Teste unitário que verifica se o status está como cancelado, dado uma data.
     */
    @Test
    void TestCanceledStatus(){
        when(mockEdition.getStatus()).thenReturn(EditionStatus.CANCELED);
        EditionStatus status;
        status = editionService.verifyEditionStatus(mockEdition, Date.valueOf("2015-03-30"));

        Assertions.assertEquals(EditionStatus.CANCELED, status);
    }

    /***
     * Teste unitário que verifica se o status está como concluído, dado uma data.
     */
    @Test
    void TestConfirmedStatus(){
        when(mockEdition.getStatus()).thenReturn(EditionStatus.CONCLUDED);
        when(mockEdition.getFinalDate()).thenReturn(Date.valueOf("2015-04-01"));

        EditionStatus status;
        status = editionService.verifyEditionStatus(mockEdition, Date.valueOf("2015-03-30"));

        Assertions.assertEquals(EditionStatus.CONFIRMED, status);
    }

    /***
     * Teste unitário que verifica se o status está como confirmado, dado uma data.
     */
    @Test
    void TestConcludedStatus(){
        when(mockEdition.getStatus()).thenReturn(EditionStatus.CONFIRMED);
        when(mockEdition.getFinalDate()).thenReturn(Date.valueOf("2015-03-30"));

        EditionStatus status;
        status = editionService.verifyEditionStatus(mockEdition, Date.valueOf("2015-04-01"));

        Assertions.assertEquals(EditionStatus.CONCLUDED, status);
    }
}
