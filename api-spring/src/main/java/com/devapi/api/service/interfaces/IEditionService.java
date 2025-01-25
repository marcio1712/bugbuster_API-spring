package com.devapi.api.service.interfaces;

import com.devapi.api.domain.model.Edition;
import com.devapi.api.utils.EditionStatus;

import java.sql.Date;

public interface IEditionService {
    EditionStatus verifyEditionStatus(Edition event, Date date);
}
