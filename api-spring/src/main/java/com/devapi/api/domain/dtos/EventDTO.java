package com.devapi.api.domain.dtos;

import com.devapi.api.domain.model.User;

public class EventDTO {
    long id;

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

    public long getId() { // Getter para o atributo 'id'
        return id;
    }

    public void setId(long id) { // Setter para o atributo 'id'
        this.id = id;
    }

    String name;
    String acronym;
    String description;
    User user;
}
