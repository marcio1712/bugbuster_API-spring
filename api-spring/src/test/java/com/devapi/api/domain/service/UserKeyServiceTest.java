package com.devapi.api.domain.service;

import com.devapi.api.domain.dtos.UserKeyDTO;
import com.devapi.api.domain.model.User;
import com.devapi.api.domain.model.UserKey;
import com.devapi.api.service.classes.UserKeyService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class UserKeyServiceTest {

    private static UserKey userKey;

    private static UserKeyDTO userKeyDTO;
    private static UserKeyService userKeyService;

    @BeforeAll
    static void SetUp(){
        userKeyService = new UserKeyService();
        userKey = new UserKey(1L,
                "123456",
                new User(1L, "BrunoCotelo","Bruno Cotelo", "cotelo@gmail.com", "ADMIN")
        );

        userKeyDTO = new UserKeyDTO(1L, "123456", new User(1L, "BrunoCotelo","Bruno Cotelo", "cotelo@gmail.com", "ADMIN"));

    }

    @Test
    void TestDTOConversion(){
        UserKeyDTO userKeyDTO1 = userKeyService.convertToDTO(userKey);

        Assertions.assertEquals(userKey.getId(), userKeyDTO1.getId());
        Assertions.assertEquals(userKey.getKeyUser(), userKeyDTO1.getKeyUser());
        Assertions.assertEquals(userKey.getUser(), userKeyDTO1.getUser());
    }

    @Test
    void TestEntityConversion(){
        UserKey userKeyEntity = userKeyService.convertToEntity(userKeyDTO);

        Assertions.assertEquals(userKeyDTO.getId(), userKeyEntity.getId());
        Assertions.assertEquals(userKeyDTO.getKeyUser(), userKeyEntity.getKeyUser());
        Assertions.assertEquals(userKeyDTO.getUser(), userKeyEntity.getUser());
    }
}
