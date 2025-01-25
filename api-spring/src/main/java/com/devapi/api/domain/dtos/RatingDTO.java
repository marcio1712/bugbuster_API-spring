package com.devapi.api.domain.dtos;

import com.devapi.api.domain.model.Event;

public class RatingDTO {
    long id;

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    int value;
    Event event;
}
