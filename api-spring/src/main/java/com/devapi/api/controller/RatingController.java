package com.devapi.api.controller;

import com.devapi.api.domain.dtos.RatingDTO;
import com.devapi.api.domain.model.Rating;
import com.devapi.api.repository.RatingRepository;
import com.devapi.api.service.classes.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ratings")
public class RatingController {
    @Autowired
    private RatingRepository repository;

    @PostMapping
    public ResponseEntity<Rating> create(@RequestBody RatingDTO ratingDTO){
        Rating rating = RatingService.convertToEntity(ratingDTO);

        if(rating.getValue() > 5){
            rating.setValue(5);
        } else if (rating.getValue() <= 0) {
            rating.setValue(1);
        }

        repository.save(rating);
        return ResponseEntity.status(HttpStatus.CREATED).body(rating);
    }

    @GetMapping
    public List<Rating> read(){
        return repository.findAll();
    }
}
