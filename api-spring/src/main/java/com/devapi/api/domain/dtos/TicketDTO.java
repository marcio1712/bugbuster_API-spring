package com.devapi.api.domain.dtos;

import com.devapi.api.domain.model.Edition;
import com.devapi.api.domain.model.User;
import com.devapi.api.utils.TicketStatus;

public class TicketDTO {
    long id;
    User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public TicketStatus getStatus() {
        return status;
    }

    public void setStatus(TicketStatus status) {
        this.status = status;
    }

    public Edition getEdition() {
        return edition;
    }

    public void setEdition(Edition edition) {
        this.edition = edition;
    }

    TicketStatus status;
    Edition edition;
}
