package com.devapi.api.service.interfaces;

import com.devapi.api.domain.model.Event;
import com.devapi.api.domain.model.Rating;
import com.devapi.api.exception.RatingNotFoundException;

import java.util.List;

public interface IEvent {

    List<Rating> findRatingsByEventId(Event event, List<Rating> listRatings) throws RatingNotFoundException;
}
