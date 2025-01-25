package com.devapi.api.service.classes;

import com.devapi.api.domain.dtos.TicketDTO;
import com.devapi.api.domain.model.Ticket;
import com.devapi.api.exception.InvalidDateException;
import com.devapi.api.repository.TicketRepository;
import com.devapi.api.service.interfaces.ITicket;
import com.devapi.api.utils.TicketStatus;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Date;
import java.util.concurrent.TimeUnit;

public class TicketService implements ITicket {

    @Autowired
    private static ModelMapper modelMapper = new ModelMapper();

    public TicketDTO convertToDTO(Ticket ticket) {
        return modelMapper.map(ticket, TicketDTO.class);
    }

    public static Ticket convertToEntity(TicketDTO ticketDTO) {
        return modelMapper.map(ticketDTO, Ticket.class);
    }

    private TicketRepository repository;

    /***
     * Método reponsável por retornar um ticket com seu status atualizado para cancelado.
     * @param ticket o ticket que será cancelado.
     * @param date a data do dia da solicitação do cancelamento.
     * @return o ticket com status cancelado.
     */
    @Override
    public Ticket cancelTicket(Ticket ticket, Date date) throws InvalidDateException {
        return cancelTicketImpl(ticket, date);
    }

    /***
     * Método reponsável por retornar um ticket com seu status atualizado para cancelado.
     * @param ticket o ticket que será cancelado.
     * @param date a data para verificação se é possível cancelar o ticket ou não.
     * @return o ticket com status cancelado.
     */
    private Ticket cancelTicketImpl(Ticket ticket, Date date) throws InvalidDateException {
        if(ticket.getStatus() == TicketStatus.CONFIRMED){
            Date dataFinal = ticket.getEdition().getFinalDate();

            long millisDataFinal = dataFinal.getTime();
            long millisDate = date.getTime();

            long diffMillis = Math.abs(millisDataFinal - millisDate);

            long diffDays = TimeUnit.MILLISECONDS.toDays(diffMillis);

            if(diffDays > 3L){
                ticket.setStatus(TicketStatus.CANCELED);
                return ticket;
            }
            else{
                throw new InvalidDateException();
            }
        } else {
            throw new InvalidDateException();
        }
    }
}
