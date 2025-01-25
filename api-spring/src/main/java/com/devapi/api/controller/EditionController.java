package com.devapi.api.controller;


import com.devapi.api.domain.dtos.EditionDTO;
import com.devapi.api.domain.model.Edition;
import com.devapi.api.repository.EditionRepository;
import com.devapi.api.service.classes.EditionService;
import com.devapi.api.utils.EditionStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/editions")
public class EditionController {

    @Autowired
    private EditionRepository repository;

    @PostMapping
    public ResponseEntity<Edition> create(@RequestBody EditionDTO editionDTO){
        Edition edition = EditionService.convertToEntity(editionDTO);

        edition.setStatus(EditionStatus.CONFIRMED);
        repository.save(edition);
        return ResponseEntity.status(HttpStatus.CREATED).body(edition);
    }

    @GetMapping
    public List<Edition> read(){
        return repository.findAll();
    }
}
