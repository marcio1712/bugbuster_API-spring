package com.devapi.api.controller;

import com.devapi.api.domain.dtos.TicketDTO;
import com.devapi.api.domain.model.Ticket;
import com.devapi.api.domain.model.User;
import com.devapi.api.repository.TicketRepository;
import com.devapi.api.service.classes.TicketService;
import com.devapi.api.service.classes.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tickets")
public class TicketController {

    @Autowired
    private TicketRepository repository;

    @PostMapping
    public ResponseEntity<Ticket> create(@RequestBody TicketDTO ticketDTO){
        Ticket ticket = TicketService.convertToEntity(ticketDTO);
        repository.save(ticket);

        return ResponseEntity.status(HttpStatus.CREATED).body(ticket);
    }

    @GetMapping
    public List<Ticket> read(){
        return repository.findAll();
    }
}
