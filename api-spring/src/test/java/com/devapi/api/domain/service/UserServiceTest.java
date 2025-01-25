package com.devapi.api.domain.service;

import com.devapi.api.domain.dtos.UserDTO;
import com.devapi.api.domain.model.UserKey;
import com.devapi.api.domain.model.User;
import com.devapi.api.service.classes.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class UserServiceTest {

    private static UserKey userKey1;
    private static UserKey userKey2;
    private static UserKey userKey3;

    private static List<UserKey> userKeys;

    private User user = mock(User.class);

    private static UserService userService;

    @BeforeAll
    static void SetUp(){
        userKey1 = new UserKey(1L, "123456", new User(1L, "BrunoCotelo","Bruno Cotelo", "cotelo@gmail.com", "ADMIN"));
        userKey2 = new UserKey(2L, "654321", new User(1L, "BrunoCotelo","Bruno Cotelo", "luanavidal@gmail.com", "ADMIN"));
        userKey3 = new UserKey(3L, "987654", new User(1L, "BrunoCotelo","Bruno Cotelo", "denersonberudio@gmail.com", "ADMIN"));

        userKeys = new ArrayList<>();
        userService = new UserService();
    }


    /***
     * Teste unitário que verifica se o usuário consegue cadastrar um evento com uma key válida.
     */
    @Test
    void TestUserValidKey(){
        when(user.getEmail()).thenReturn("cotelo@gmail.com");

        userKeys.add(userKey1);
        userKeys.add(userKey2);
        userKeys.add(userKey3);

        boolean userValidate;

        userValidate = userService.validate(user, userKeys);

        Assertions.assertTrue(userValidate);
    }

    /***
     * Teste unitário que verifica se o usuário consegue cadastrar um evento com uma key inválida.
     */
    @Test
    void TestUserInvalidKey(){
        when(user.getEmail()).thenReturn("testeunitario@hotmail.com");

        userKeys.add(userKey1);
        userKeys.add(userKey2);
        userKeys.add(userKey3);

        boolean userValidate;

        userValidate = userService.validate(user, userKeys);

        Assertions.assertFalse(userValidate);
    }

    @Test
    void TestDTOConversion(){
        User user1 = new User(1L, "BrunoCotelo","Bruno Cotelo", "cotelo@gmail.com", "ADMIN");
        UserDTO userDTO = userService.convertToDTO(user1);

        Assertions.assertEquals(user1.getEmail(), userDTO.getEmail());
        Assertions.assertEquals(user1.getId(), userDTO.getId());
        Assertions.assertEquals(user1.getLogin(), userDTO.getLogin());
        Assertions.assertEquals(user1.getAfiliacao(), userDTO.getAfiliacao());
    }

    @Test
    void TestEmailValidation(){
        User user1 = new User(1L, "BrunoCotelo","Bruno Cotelo", "cotelo@gmail.com", "ADMIN");

        boolean result = userService.validateEmail(user1.getEmail());
        boolean resultFalse = userService.validateEmail("coteloSemArrobagmail.com");

        Assertions.assertTrue(result);
        Assertions.assertFalse(resultFalse);
    }
}
