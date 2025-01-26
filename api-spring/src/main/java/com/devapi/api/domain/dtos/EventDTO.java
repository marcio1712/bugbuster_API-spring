package com.devapi.api.domain.dtos;

import com.devapi.api.domain.model.User;

public class EventDTO {
    long id;
    String name;
    String acronym;
    String description;
    User user;

    public EventDTO(long id, String name, String acronym, String description, User user) {
        this.id = id;
        this.name = name;
        this.acronym = acronym;
        this.description = description;
        this.user = user;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAcronym() {
        return acronym;
    }

    public void setAcronym(String acronym) {
        this.acronym = acronym;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
