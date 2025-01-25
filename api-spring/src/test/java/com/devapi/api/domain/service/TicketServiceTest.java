package com.devapi.api.domain.service;

import com.devapi.api.domain.model.Edition;
import com.devapi.api.domain.model.Ticket;
import com.devapi.api.domain.model.User;
import com.devapi.api.exception.InvalidDateException;
import com.devapi.api.service.classes.TicketService;
import com.devapi.api.utils.TicketStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.Date;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class TicketServiceTest {

    private static Ticket ticket;
    private static Edition mockEdition = mock(Edition.class);

    private static TicketService ticketService;

    @BeforeAll
    static void SetUp(){
        ticket = new Ticket(1L,
                new User(1L, "BrunoCotelo","Bruno Cotelo", "cotelo@gmail.com", "ADMIN"),
                TicketStatus.CONFIRMED,
                mockEdition);

        ticketService = new TicketService();
    }


    /***
     * Teste unitário que verifica se o cancelamento teve êxito com uma data válida.
     */
    @Test
    void TestValidDateTicketCanceling() throws InvalidDateException {
        when(mockEdition.getFinalDate()).thenReturn(Date.valueOf("2023-06-18"));

        Ticket ticket2 = ticketService.cancelTicket(ticket, Date.valueOf("2023-06-14"));

        Assertions.assertEquals(TicketStatus.CANCELED, ticket2.getStatus());
    }

    /***
     * Teste unitário que verifica se o cancelamento não teve êxito, com uma data inválida.
     */
    @Test
    void TestInvalidDateTicketCanceling(){
        when(mockEdition.getFinalDate()).thenReturn(Date.valueOf("2023-06-18"));

        Assertions.assertThrows(InvalidDateException.class, () -> {
            ticketService.cancelTicket(ticket, Date.valueOf("2023-06-16"));
        });
    }
}
