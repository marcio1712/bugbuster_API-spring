package com.devapi.api.service;

import com.devapi.api.domain.model.Event;
import com.devapi.api.domain.model.Rating;
import com.devapi.api.domain.model.User;
import com.devapi.api.domain.dtos.RatingDTO;
import com.devapi.api.domain.dtos.EventDTO;
import com.devapi.api.domain.dtos.UserDTO;
import com.devapi.api.exception.InvalidRatingValueException;
import com.devapi.api.exception.RatingEmptyException;
import com.devapi.api.service.classes.RatingService;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class RatingServiceTest {

    private RatingService ratingService;
    private List<Rating> ratings;
    private Rating rating1;
    private Rating rating2;
    private Rating rating3;

    @BeforeAll
    public static void setupAll() {
    }

    @BeforeEach
    void setUp() {
        ratingService = new RatingService();
        ratings = new ArrayList<>();

        User user = new User(1L, "Lucca", "Lucca Franca", "lucca@id.uff", "ADMIN");
        Event event = new Event(0L, "Evento Teste", "ET", "Teste unitario de evento", user);

        rating1 = new Rating(0L, 5, event);
        rating2 = new Rating(1L, 4, event);
        rating3 = new Rating(2L, 3, event);
    }

    @Test
    @DisplayName("Simula calculo de media com valores validos")
    void testCalculateAverageAvaliationWithValidRatings() throws RatingEmptyException, InvalidRatingValueException {
        ratings.add(rating1);
        ratings.add(rating2);
        ratings.add(rating3);
        float expectedAverage = (5 + 4 + 3) / 3f;

        float result = ratingService.calculateAverageAvaliation(ratings);

        Assertions.assertEquals(expectedAverage, result, "A média não está correta.");
    }

    @Test
    @DisplayName("Simula calculo media sem valores")
    void testCalculateAverageAvaliationWithEmptyList() {
        Assertions.assertThrows(RatingEmptyException.class, () -> {
            ratingService.calculateAverageAvaliation(Collections.emptyList());
        });
    }

    @Test
    @DisplayName("Simula calculo media com um valor")
    void testCalculateAverageAvaliationWithSingleRating() throws RatingEmptyException, InvalidRatingValueException {
        ratings.add(rating1);
        float expectedAverage = 5f;

        float result = ratingService.calculateAverageAvaliation(ratings);

        Assertions.assertEquals(expectedAverage, result, "A média com uma avaliação está incorreta.");
    }

    @Test
    @DisplayName("Simula calculo media com valores trocados")
    void testCalculateAverageWithMixedRatings() throws RatingEmptyException, InvalidRatingValueException {
        ratings.add(rating1);
        ratings.add(new Rating(3L, 1, rating1.getEvent()));
        ratings.add(new Rating(4L, 2, rating1.getEvent()));

        float expectedAverage = (5 + 1 + 2) / 3f;

        float result = ratingService.calculateAverageAvaliation(ratings);

        Assertions.assertEquals(expectedAverage, result, 0.0001, "A média com avaliações mistas está incorreta.");
    }

    @Test
    @DisplayName("Simula calculo media com valor invalido")
    void testCalculateAverageWithInvalidRatings() {
        ratings.add(new Rating(0L, -1, rating1.getEvent()));
        ratings.add(rating2);

        Assertions.assertThrows(InvalidRatingValueException.class, () -> {
            ratingService.calculateAverageAvaliation(ratings);
        });
    }

    @Test
    @DisplayName("Simula calculo media com valor nulo")
    void testCalculateAverageWithNullRating() {
        ratings.add(new Rating(0L, 5, rating1.getEvent()));
        ratings.add(null);

        Assertions.assertThrows(NullPointerException.class, () -> {
            ratingService.calculateAverageAvaliation(ratings);
        });
    }

    @Test
    @DisplayName("Simula calculo media com valor igual a zero")
    void testCalculateAverageWithZeroValueRatings() throws RatingEmptyException, InvalidRatingValueException {
        ratings.add(new Rating(0L, 0, rating1.getEvent()));
        ratings.add(new Rating(1L, 3, rating1.getEvent()));

        float result = ratingService.calculateAverageAvaliation(ratings);

        Assertions.assertEquals(1.5f, result, "A média deve ignorar avaliações com valor zero.");
    }

    @Test
    @DisplayName("Simula calculo media com valores altos")
    void testCalculateAverageWithHighRatings() throws RatingEmptyException, InvalidRatingValueException {
        ratings.add(new Rating(0L, 1000, rating1.getEvent()));
        ratings.add(new Rating(1L, 999, rating1.getEvent()));
        ratings.add(new Rating(2L, 1000, rating1.getEvent()));

        float expectedAverage = (1000 + 999 + 1000) / 3f;

        float result = ratingService.calculateAverageAvaliation(ratings);

        Assertions.assertEquals(expectedAverage, result, 0.0001, "A média com avaliações muito altas está incorreta.");
    }

    @Test
    @DisplayName("Simula calculo media com valores iguais")
    void testCalculateAverageWithEqualRatings() throws RatingEmptyException, InvalidRatingValueException {
        ratings.add(new Rating(0L, 4, rating1.getEvent()));
        ratings.add(new Rating(1L, 4, rating1.getEvent()));
        ratings.add(new Rating(2L, 4, rating1.getEvent()));

        float expectedAverage = 4f;

        float result = ratingService.calculateAverageAvaliation(ratings);

        Assertions.assertEquals(expectedAverage, result, "A média não está correta quando todas as avaliações são iguais.");
    }

    @Test
    @DisplayName("Simula calculo media com muitos valores")
    void testCalculateAverageWithLargeNumberOfRatings() throws RatingEmptyException, InvalidRatingValueException {
        for (int i = 0; i < 10000; i++) {
            ratings.add(new Rating((long) i, 5, rating1.getEvent()));
        }

        float expectedAverage = 5f;

        float result = ratingService.calculateAverageAvaliation(ratings);

        Assertions.assertEquals(expectedAverage, result, "A média com grande quantidade de avaliações não está correta.");
    }

    @Test
    @DisplayName("Simula calculo media de multiplos eventos")
    void testCalculateAverageWithMultipleEvents() throws RatingEmptyException, InvalidRatingValueException {
        User user = new User(1L, "BrunoCotelo", "Bruno Cotelo", "cotelo@gmail.com", "ADMIN");
        Event event1 = new Event(0L, "Evento 1", "ET1", "Teste evento 1", user);
        Event event2 = new Event(1L, "Evento 2", "ET2", "Teste evento 2", user);

        ratings.add(new Rating(0L, 5, event1));
        ratings.add(new Rating(1L, 4, event2));
        ratings.add(new Rating(2L, 3, event1));

        float expectedAverage = (5 + 3) / 2f;

        float result = ratingService.calculateAverageAvaliation(ratings);

        Assertions.assertEquals(expectedAverage, result, "A média de avaliações para múltiplos eventos está incorreta.");
    }

    @Test
    @DisplayName("Simula conversao para DTO")
    void testConvertToDTO() {
        Rating rating = new Rating(1L, 5, new Event(0L, "Evento Teste", "ET", "Teste", new User()));

        RatingDTO ratingDTO = ratingService.convertToDTO(rating);

        Assertions.assertNotNull(ratingDTO, "A conversão para DTO falhou.");
        Assertions.assertEquals(rating.getValue(), ratingDTO.getValue(), "O valor da avaliação não corresponde.");
    }

    @Test
    @DisplayName("Simula conversao para Entidade")
    void testConvertToEntity() {
        RatingDTO ratingDTO = new RatingDTO(1L, 5, new Event(0L, "Evento Teste", "ET", "Teste", new User()));

        Rating rating = RatingService.convertToEntity(ratingDTO);

        Assertions.assertNotNull(rating, "A conversão para a entidade falhou.");
        Assertions.assertEquals(ratingDTO.getValue(), rating.getValue(), "O valor da avaliação não corresponde.");
    }
}
