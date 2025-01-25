package com.devapi.api.controller;

import com.devapi.api.domain.dtos.UserKeyDTO;
import com.devapi.api.domain.model.UserKey;
import com.devapi.api.repository.KeyRepository;
import com.devapi.api.service.classes.UserKeyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/keys")
public class KeyController {
    @Autowired
    private KeyRepository repository;

    @PostMapping
    public ResponseEntity<UserKey> create(@RequestBody UserKeyDTO userKeyDTO){
        UserKey userKey = UserKeyService.convertToEntity(userKeyDTO);

        repository.save(userKey);
        return ResponseEntity.status(HttpStatus.CREATED).body(userKey);
    }

    @GetMapping
    public List<UserKey> read(){
        return repository.findAll();
    }

}
