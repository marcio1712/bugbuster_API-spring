package com.devapi.api.domain.service;

import com.devapi.api.domain.model.*;
import com.devapi.api.exception.RatingNotFoundException;
import com.devapi.api.service.classes.EventService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class EventServiceTest {

    private Event mockEvent = mock(Event.class);
    private static Rating rating1 = new Rating();
    private static Rating rating2 = new Rating();
    private static Rating rating3 = new Rating();
    private static List<Rating> ratings = new ArrayList<>();

    private static List<Ticket> tickets = new ArrayList<>();
    private static Ticket ticket1 = new Ticket();
    private static Ticket ticket2 = new Ticket();
    private static EventService eventService;

    private static List<Event> events = new ArrayList<>();
    private static Event event1 = new Event();
    private static Event event2 = new Event();

    @BeforeAll
    public static void SetUp(){
        eventService = new EventService();

        // Evento 1
        event1.setId(1);
        event1.setAcronym("ET");
        event1.setName("Evento teste");
        event1.setUser(new User(1L, "LuanaVidal", "Luana Vidal", "luana@gmail.com", "ADMIN"));

        // Evento 2
        event2.setId(2);
        event2.setAcronym("OT");
        event2.setName("Outro evento teste");
        event2.setUser(new User(1L, "LuanaVidal", "Luana Vidal", "luana@gmail.com", "ADMIN"));

        rating1.setEvent(event1);
        rating1.setValue(5);

        rating2.setEvent(event1);
        rating2.setValue(1);

        rating3.setEvent(event1);
        rating3.setValue(5);

        ticket1.setEdition(new Edition(1L,1,2023, new Date(05-06-2023), new Date(05-10-2023), "Maricá", null,
                event1));
        ticket1.setUser(new User(2L, "maironAzevedo", "Mairon Azevedo", "mairon@id.uff.br", "USER"));

        ticket2.setEdition(new Edition(1L,2,2023, new Date(05-06-2023), new Date(05-10-2023), "São Gonçalo", null,
                event2));
        ticket2.setUser(new User(2L, "maironAzevedo", "Mairon Azevedo", "mairon@id.uff.br", "USER"));
    }


    /***
     * Teste unitário que verifica se a lista de avaliações de um certo evento chega corretamente.
     */
    @Test
    void TestRatingByEventRegularList() throws RatingNotFoundException {
        when(mockEvent.getId()).thenReturn(1L);

        ratings.add(rating1);
        ratings.add(rating2);
        ratings.add(rating3);

        List<Rating> ratingsList = eventService.findRatingsByEventId(mockEvent, ratings);

        Assertions.assertEquals(3, ratingsList.size());
        Assertions.assertEquals("Evento teste", ratingsList.get(0).getEvent().getName());
    }

    /***
     * Teste unitário que verifica se o método de procurar avaliações de um evento tem retorno null.
     */
    @Test
    void TestRatingByEventEmptyList() {
        ratings.clear();
        when(mockEvent.getId()).thenReturn(1L);

        Assertions.assertThrows(RatingNotFoundException.class, () -> {
            eventService.findRatingsByEventId(mockEvent, ratings);
        });
    }

    @Test
    void TestEventsUserNotFound() {
        tickets.add(ticket1);
        tickets.add(ticket2);

        List expectedList = new ArrayList<>();

        List<Event> eventsList = eventService.returnPurchasedEvents(3, tickets);

        Assertions.assertEquals(expectedList, eventsList);
    }

    @Test
    void TestEventsByUser() {
        when(mockEvent.getId()).thenReturn(2L);
        tickets.clear();
        tickets.add(ticket1);
        tickets.add(ticket2);

        events.add(event1);
        events.add(event2);

        List<Event> eventsList = eventService.returnPurchasedEvents(mockEvent.getId(),tickets);

        Assertions.assertEquals(events, eventsList);
    }
}
