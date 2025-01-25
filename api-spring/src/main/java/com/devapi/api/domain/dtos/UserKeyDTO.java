package com.devapi.api.domain.dtos;

import com.devapi.api.domain.model.User;

public class UserKeyDTO {
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getKeyUser() {
        return keyUser;
    }

    public void setKeyUser(String keyUser) {
        this.keyUser = keyUser;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    long id;

    public UserKeyDTO(long id, String keyUser, User user) {
        this.id = id;
        this.keyUser = keyUser;
        this.user = user;
    }

    public UserKeyDTO() {
    }

    String keyUser;
    User user;
}
