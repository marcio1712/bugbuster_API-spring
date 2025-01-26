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
    void testeSimples(){
        int resultado = 5 + 5;
        assertEquals(10, resultado);
    }

    @Test
    @DisplayName("Retorna Concluido")
    void testeStatusConcluido(){
        Edition edition = new Edition();
        edition.setFinalDate(Date.valueOf("2024-01-25"));
        edition.setStatus(EditionStatus.CONFIRMED);

        assertEquals(EditionStatus.CONCLUDED, editionService.verifyEditionStatus(edition, Date.valueOf("2024-01-26")));
    }


}
