package com.devapi.api.service.classes;

import com.devapi.api.domain.dtos.EventDTO;
import com.devapi.api.domain.model.Event;
import com.devapi.api.domain.model.Rating;
import com.devapi.api.domain.model.Ticket;
import com.devapi.api.exception.RatingNotFoundException;
import com.devapi.api.repository.RatingRepository;
import com.devapi.api.service.interfaces.IEvent;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class EventService implements IEvent {
    @Autowired
    private static ModelMapper modelMapper = new ModelMapper();

    public EventDTO convertToDTO(Event event) {
        return modelMapper.map(event, EventDTO.class);
    }

    public static Event convertToEntity(EventDTO eventDTO) {
        return modelMapper.map(eventDTO, Event.class);
    }


    private RatingRepository repository;

    /***
     * Método responsável por retornar uma lista de avaliações de um determinado evento
     * @param event O evento na qual será procurado as avaliações
     * @param listRatings A lista de avaliações todos eventos cadastrados.
     * @return uma lista de avaliações.
     */
    @Override
    public List<Rating> findRatingsByEventId(Event event, List<Rating> listRatings) throws RatingNotFoundException {
        return findRatingsByEventIdImpl(event, listRatings);
    }

    /***
     * Método responsável por retornar as avaliações de um evento específico
     * @param event O evento na qual será procurado as avaliações
     * @param listRatings A lista de avaliações todos eventos cadastrados.
     * @return uma lista de avaliações.
     */
    private List<Rating> findRatingsByEventIdImpl(Event event, List<Rating> listRatings) throws RatingNotFoundException {

        List<Rating> ratings = new ArrayList<>();

        if (!listRatings.isEmpty()) {
            for(Rating element : listRatings){
                if(element.getEvent().getId() == event.getId()){
                    ratings.add(element);
                }
            }
            return ratings;
        }else{
            throw new RatingNotFoundException();
        }
    }

    /**
     * Método responsável por retornar todos os eventos que um usuário está cadastrado
     * @param userId Identificador do usuário verificado
     * @return Lista com os eventos que o usuário participou
     */
    public static List<Event> returnPurchasedEvents(long userId, List<Ticket> allTickets) {
        List<Event> purchasedEvents = new ArrayList<>();

        for(Ticket element: allTickets) {
            if(element.getUser().getId().equals(userId)) {
                purchasedEvents.add(element.getEdition().getEvent());
            }
        }
        return purchasedEvents;
    }

}
