package com.devapi.api.domain.dtos;

import com.devapi.api.domain.model.Event;

public class RatingDTO {

     long id;
     int value;
     Event event;

    public RatingDTO() {
    }

    public RatingDTO(long id, int value, Event event) {
        this.id = id;
        this.value = value;
        this.event = event;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

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
}

