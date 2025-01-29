package com.devapi.api.controller;

import com.devapi.api.domain.dtos.EventDTO;
import com.devapi.api.domain.model.Event;
import com.devapi.api.domain.model.Ticket;
import com.devapi.api.repository.EventRepository;
import com.devapi.api.repository.KeyRepository;

import com.devapi.api.repository.TicketRepository;
import com.devapi.api.repository.UserRepository;
import com.devapi.api.service.classes.EventService;
import com.devapi.api.service.classes.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/events")
public class EventController {

    @Autowired
    private EventRepository repository;
    @Autowired
    private KeyRepository keyRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TicketRepository ticketRepository;

    private UserService userService = new UserService();

    @PostMapping
    public ResponseEntity<Event> create(@RequestBody EventDTO eventDTO) {
        Event event = EventService.convertToEntity(eventDTO);

        if(userService.validate(event.getUser(), keyRepository.findAll())){
            repository.save(event);
            return ResponseEntity.status(HttpStatus.CREATED).body(event);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(event);
        }
    }

    @GetMapping
    public List<Event> read(){
        return repository.findAll();
    }

    /**
     * Método Implementado para localizar os eventos complados pelo usuário
     * @param id Identificador do usuário
     * @return Lista de eventos em que o usuário está cadastrado
     */

    @GetMapping
    public ResponseEntity<List<Event>> eventsBoughtByUser(@PathVariable("userId") long userId){
        if(userRepository.existsById(userId)) {
            List<Ticket> allTickets = ticketRepository.findAll();
            List<Event> eventosComprados = EventService.returnPurchasedEvents(userId, allTickets);

            return ResponseEntity.status(HttpStatus.OK).body(eventosComprados);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
}
