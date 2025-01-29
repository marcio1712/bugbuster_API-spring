package com.devapi.api.service.classes;

import com.devapi.api.domain.dtos.UserKeyDTO;
import com.devapi.api.domain.model.UserKey;
import com.devapi.api.service.interfaces.IUserKeyService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;


public class UserKeyService implements IUserKeyService {
    @Autowired
    private static ModelMapper modelMapper = new ModelMapper();

    public UserKeyDTO convertToDTO(UserKey userKey) {
        return modelMapper.map(userKey, UserKeyDTO.class);
    }

    public static UserKey convertToEntity(UserKeyDTO userKeyDTO) {
        return modelMapper.map(userKeyDTO, UserKey.class);
    }
}