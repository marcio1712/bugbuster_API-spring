package com.devapi.api.service.classes;

import com.devapi.api.domain.dtos.EditionDTO;
import com.devapi.api.domain.model.Edition;
import com.devapi.api.service.interfaces.IEditionService;
import com.devapi.api.utils.EditionStatus;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import java.sql.Date;

public class EditionService implements IEditionService {
    @Autowired
    private static ModelMapper modelMapper = new ModelMapper();

    public EditionDTO convertToDTO(Edition edition) {
        return modelMapper.map(edition, EditionDTO.class);
    }

    public static Edition convertToEntity(EditionDTO editionDTO) {
        return modelMapper.map(editionDTO, Edition.class);
    }

    /***
     * Este método é responsável por verificar o Status de uma implementação de acordo com uma data enviada.
     * @param edition a entidade edição que será verificada.
     * @param date a data em que será comparada com a data final do evento para saber o seu status.
     * @return o status da Edição.
     */
    private EditionStatus verifyEditionStatusImpl(Edition edition, Date date){

        EditionStatus status;

        if(edition.getStatus() != EditionStatus.CANCELED && edition.getStatus() != null) {
            if(date.after(edition.getFinalDate())) {
                status = EditionStatus.CONCLUDED;
            } else {
                status = EditionStatus.CONFIRMED;
            }
            return status;
        } else {
            status = EditionStatus.CANCELED;
        }

        return status;
    }

    /***
     * Método que usa a implementação da verificação do status de uma edição.
     * @param edition a entidade edição que será verificada.
     * @param date a data em que será comparada com a data final do evento para saber o seu status.
     * @return o status da Edição.
     */
    @Override
    public EditionStatus verifyEditionStatus(Edition edition, Date date) {
        EditionStatus status;
        status = verifyEditionStatusImpl(edition, date);

        return status;
    }
}
