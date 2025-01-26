package com.devapi.api.service.interfaces;

import com.devapi.api.domain.model.Rating;
import com.devapi.api.exception.InvalidRatingValueException;
import com.devapi.api.exception.RatingEmptyException;

import java.util.List;

public interface IRating {

    float calculateAverageAvaliation(List<Rating> ratings) throws RatingEmptyException, InvalidRatingValueException;
}
