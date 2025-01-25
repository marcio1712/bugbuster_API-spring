package com.devapi.api.domain.service;

import com.devapi.api.domain.model.Event;
import com.devapi.api.domain.model.Rating;
import com.devapi.api.domain.model.User;
import com.devapi.api.exception.RatingEmptyException;
import com.devapi.api.exception.RatingNotFoundException;
import com.devapi.api.service.classes.RatingService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

class RatingServiceTest {


    private static List<Rating> ratings;
    private static RatingService ratingService;
    private static Rating rating1;
    private static Rating rating2;
    private static Rating rating3;

    @BeforeAll
    public static void SetUp(){
        ratings = new ArrayList<>();
        ratingService = new RatingService();

        rating1 = new Rating(0L, 5, new Event(0L, "Evento Teste", "ET","Teste unitario",
                new User(1L, "BrunoCotelo","Bruno Cotelo", "cotelo@gmail.com", "ADMIN")));

        rating2 = new Rating(1L, 5, new Event(0L, "Evento Teste", "ET","Teste unitario",
                new User(1L, "BrunoCotelo","Bruno Cotelo", "cotelo@gmail.com", "ADMIN")));

        rating3 = new Rating(2L, 2, new Event(0L, "Evento Teste", "ET","Teste unitario",
                new User(1L, "BrunoCotelo","Bruno Cotelo", "cotelo@gmail.com", "ADMIN")));

    }

    /***
     * Teste unitário que verifica o cálculo da média está correto.
     */
    @Test
    void TestAverageCalculus() throws RatingEmptyException {
        ratings.add(rating1);
        ratings.add(rating2);
        ratings.add(rating3);

        float media = ratingService.calculateAverageAvaliation(ratings);

        Assertions.assertEquals(4, media);
    }

    /***
     * Teste unitário que verifica se o método retorna null quando o evento não possui nenhuma avaliação.
     */
    @Test
    void TestExceptionEmptyAverage(){
        ratings.clear();
        Assertions.assertThrows(RatingEmptyException.class, () -> {
            ratingService.calculateAverageAvaliation(ratings);
        });
    }
}
