package com.devapi.api.controller;

import com.devapi.api.domain.dtos.UserDTO;
import com.devapi.api.domain.model.User;

import com.devapi.api.service.classes.UserService;
import com.devapi.api.repository.EditionRepository;
import com.devapi.api.repository.EventRepository;
import com.devapi.api.repository.TicketRepository;
import com.devapi.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepository repository;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private EditionRepository editionRepository;

    @PostMapping
    public ResponseEntity<User> create(@RequestBody UserDTO userDTO){
        User user = UserService.convertToEntity(userDTO);
        repository.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @GetMapping
    public List<User> read(){
        return repository.findAll();
    }
}