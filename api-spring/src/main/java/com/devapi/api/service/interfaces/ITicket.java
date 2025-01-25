package com.devapi.api.service.interfaces;
import com.devapi.api.domain.model.Ticket;
import com.devapi.api.exception.InvalidDateException;

import java.sql.Date;

public interface ITicket {

    Ticket cancelTicket(Ticket ticket, Date date) throws InvalidDateException;
}
