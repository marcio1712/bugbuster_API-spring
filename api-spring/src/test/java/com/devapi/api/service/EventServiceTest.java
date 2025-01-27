package com.devapi.api.service;

import com.devapi.api.domain.dtos.EventDTO;
import com.devapi.api.domain.model.*;
import com.devapi.api.exception.RatingNotFoundException;
import com.devapi.api.service.classes.EventService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EventServiceTest {

    private EventService eventService;
    private Event mockEvent;
    private List<Rating> ratings;
    private List<Ticket> tickets;

    @BeforeEach
    void setUp() {
        eventService = new EventService();
        mockEvent = mock(Event.class);
        ratings = new ArrayList<>();
        tickets = new ArrayList<>();
    }

    @Test
    @DisplayName("Deve retornar uma lista de avaliações de um evento válido - Teste de Estrutura")
    void testFindRatingsByEventId_ValidEvent() throws RatingNotFoundException {
        // Configuração do evento
        when(mockEvent.getId()).thenReturn(1L);

        Rating rating1 = new Rating();
        rating1.setEvent(mockEvent);
        rating1.setValue(5);

        Rating rating2 = new Rating();
        rating2.setEvent(mockEvent);
        rating2.setValue(3);

        ratings.add(rating1);
        ratings.add(rating2);

        // Chamada do método
        List<Rating> result = eventService.findRatingsByEventId(mockEvent, ratings);

        // Verificações
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(5, result.get(0).getValue());
        assertEquals(3, result.get(1).getValue());
    }

    @Test
    @DisplayName("Deve lançar exceção quando não houver avaliações para o evento - Teste Negativo")
    void testFindRatingsByEventId_EmptyRatingsList() {
        when(mockEvent.getId()).thenReturn(1L);

        assertThrows(RatingNotFoundException.class, () -> {
            eventService.findRatingsByEventId(mockEvent, ratings);
        });
    }

    @Test
    @DisplayName("Deve retornar eventos comprados por um usuário válido - Teste Funcional")
    void testReturnPurchasedEvents_ValidUser() {
        // Configuração de eventos e ingressos
        Event event1 = new Event();
        event1.setId(1L);
        Event event2 = new Event();
        event2.setId(2L);

        User user = new User();
        user.setId(1L);

        Edition edition1 = new Edition();
        edition1.setEvent(event1);
        Edition edition2 = new Edition();
        edition2.setEvent(event2);

        Ticket ticket1 = new Ticket();
        ticket1.setUser(user);
        ticket1.setEdition(edition1);

        Ticket ticket2 = new Ticket();
        ticket2.setUser(user);
        ticket2.setEdition(edition2);

        tickets.add(ticket1);
        tickets.add(ticket2);

        // Chamada do método
        List<Event> result = EventService.returnPurchasedEvents(1L, tickets);

        // Verificações
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(event1, result.get(0));
        assertEquals(event2, result.get(1));
    }

    @Test
    @DisplayName("Deve retornar uma lista vazia quando o usuário não possui ingressos - Teste Limite Inferior")
    void testReturnPurchasedEvents_NoTickets() {
        // Configuração de uma lista vazia
        tickets.clear();

        // Chamada do método
        List<Event> result = EventService.returnPurchasedEvents(1L, tickets);

        // Verificações
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    @DisplayName("Deve retornar uma lista vazia quando o usuário não corresponde aos ingressos - Teste de Estrutura")
    void testReturnPurchasedEvents_UserNotFound() {
        // Configuração de eventos e ingressos para outro usuário
        Event event1 = new Event();
        event1.setId(1L);

        User user = new User();
        user.setId(2L);

        Edition edition1 = new Edition();
        edition1.setEvent(event1);

        Ticket ticket1 = new Ticket();
        ticket1.setUser(user);
        ticket1.setEdition(edition1);

        tickets.add(ticket1);

        // Chamada do método
        List<Event> result = EventService.returnPurchasedEvents(1L, tickets);

        // Verificações
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }


    @Test
    @DisplayName("Deve converter um Event para EventDTO - Teste de Conversão")
    void testConvertToDTO() {
        // Criação do objeto Event
        Event event = new Event();
        event.setId(1L);
        event.setName("Evento Teste");
        event.setAcronym("ET");

        // Executa a conversão
        EventDTO eventDTO = eventService.convertToDTO(event);

        // Verifica o resultado
        assertNotNull(eventDTO);
        assertEquals(1L, eventDTO.getId());
        assertEquals("Evento Teste", eventDTO.getName());
        assertEquals("ET", eventDTO.getAcronym());
    }

    @Test
    @DisplayName("Deve converter um EventDTO para Event - Teste de Conversão")
    void testConvertToEntity() {
        // Criação do objeto EventDTO
        EventDTO eventDTO = new EventDTO();
        eventDTO.setId(1L);
        eventDTO.setName("Evento Teste");
        eventDTO.setAcronym("ET");

        // Executa a conversão
        Event event = EventService.convertToEntity(eventDTO);

        // Verifica o resultado
        assertNotNull(event);
        assertEquals(1L, event.getId());
        assertEquals("Evento Teste", event.getName());
        assertEquals("ET", event.getAcronym());
    }
    @Test
    @DisplayName("Deve lançar exceção quando a lista de avaliações for nula - Teste de Exceção")
    void testFindRatingsByEventId_NullRatingsList() {
        // Configuração
        when(mockEvent.getId()).thenReturn(1L);

        // Verificação
        assertThrows(NullPointerException.class, () -> {
            eventService.findRatingsByEventId(mockEvent, null);
        });
    }
    @Test
    @DisplayName("Deve lançar exceção para evento inválido (sem ID) - Teste de Validação")
    void testFindRatingsByEventId_InvalidEvent() {
        // Configuração
        Event invalidEvent = new Event();

        // Verificação
        assertThrows(RatingNotFoundException.class, () -> {
            eventService.findRatingsByEventId(invalidEvent, ratings);
        });
    }
    @Test
    @DisplayName("Deve retornar uma lista vazia quando nenhuma avaliação corresponder ao evento - Teste de Valor Limite")
    void testFindRatingsByEventId_NoMatchingRatings() throws RatingNotFoundException {
        // Configuração
        when(mockEvent.getId()).thenReturn(1L);

        Rating rating1 = new Rating();
        rating1.setEvent(new Event());
        rating1.getEvent().setId(2L); // ID diferente

        Rating rating2 = new Rating();
        rating2.setEvent(new Event());
        rating2.getEvent().setId(3L); // ID diferente

        ratings.add(rating1);
        ratings.add(rating2);

        // Chamada do método
        List<Rating> result = eventService.findRatingsByEventId(mockEvent, ratings);

        // Verificações
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

}
