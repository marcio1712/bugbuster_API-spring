package com.devapi.api.service.classes;

import com.devapi.api.domain.dtos.RatingDTO;
import com.devapi.api.domain.model.Rating;
import com.devapi.api.exception.InvalidRatingValueException; // Importando a exceção
import com.devapi.api.exception.RatingEmptyException;
import com.devapi.api.service.interfaces.IRating;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class RatingService implements IRating {
    @Autowired
    private static ModelMapper modelMapper = new ModelMapper();

    public RatingDTO convertToDTO(Rating rating) {
        return modelMapper.map(rating, RatingDTO.class);
    }

    public static Rating convertToEntity(RatingDTO ratingDTO) {
        return modelMapper.map(ratingDTO, Rating.class);
    }

    /***
     * Método responsável por retornar a média das avaliações de um certo evento.
     * @param ratings a lista de avaliações na qual vai ser calculada a média.
     * @return a média calculada.
     */
    @Override
    public float calculateAverageAvaliation(List<Rating> ratings) throws RatingEmptyException, InvalidRatingValueException {
        return calculateAverageRatingImpl(ratings);
    }

    /***
     * Método responsável por toda a implementação de cálculo de média das avaliações.
     * @param ratings a lista de avaliações na qual vai ser calculada a média.
     * @return a média calculada.
     */
    private float calculateAverageRatingImpl(List<Rating> ratings) throws RatingEmptyException, InvalidRatingValueException {
        float soma = 0;
        float media;

        if (ratings.isEmpty()) {
            throw new RatingEmptyException();
        }

        for (Rating element : ratings) {
            // Verificando se o valor da avaliação é inválido
            if (element.getValue() < 0) {
                throw new InvalidRatingValueException(); // Lançando a exceção personalizada
            }
            soma += element.getValue();
        }

        media = soma / ratings.size();
        return media;
    }
}

