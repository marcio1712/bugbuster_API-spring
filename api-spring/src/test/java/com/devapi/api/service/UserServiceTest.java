package com.devapi.api.service;

import com.devapi.api.domain.dtos.UserDTO;
import com.devapi.api.domain.model.UserKey;
import com.devapi.api.domain.model.User;
import com.devapi.api.service.classes.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
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
    static void SetUp() {
        userKey1 = new UserKey(1L, "111111", new User(1L, "Zcouve", "Zé das Couves", "zcouve@exemplo.com", "ADMIN"));
        userKey2 = new UserKey(2L, "222222", new User(2L, "MJane", "Mary Jane", "mjane@exemplo.com", "USER"));
        userKey3 = new UserKey(3L, "333333", new User(3L, "Btata", "Boi Tatá", "btata@exemplo.com", "ADMIN"));

        userKeys = new ArrayList<>();
        userService = new UserService();
    }


    @Test
    @DisplayName("Deve retornar VERDADEIRO para e-mails válidos")
    void TestValidateEmailValid() {
        boolean result = userService.validateEmail("zcouve@exemplo.com");
        Assertions.assertTrue(result);
    }

    @Test
    @DisplayName("Deve retornar FALSO para uma tentativa de cadastrar um evento com chave inválida")
    void TestUserInvalidKey() {
        when(user.getEmail()).thenReturn("usuarioinvalido@exemplo.com");

        userKeys.add(userKey1);
        userKeys.add(userKey2);
        userKeys.add(userKey3);

        boolean userValidate = userService.validate(user, userKeys);

        Assertions.assertFalse(userValidate);
    }

    @Test
    @DisplayName("Verifica o comportamento do método validate com lista de userKeys vazia")
    void TestUserValidationWithEmptyUserKeys() {
        when(user.getEmail()).thenReturn("mjane@exemplo.com");

        userKeys.clear();

        boolean userValidate = userService.validate(user, userKeys);

        Assertions.assertFalse(userValidate);
    }

    @Test
    @DisplayName("Verifica o comportamento do método validate com chaves duplicadas.")
    void TestUserValidationWithDuplicateKeys() {
        when(user.getEmail()).thenReturn("btata@exemplo.com");

        userKeys.add(userKey3);
        userKeys.add(userKey3); // Chave duplicada

        boolean userValidate = userService.validate(user, userKeys);

        Assertions.assertTrue(userValidate);
    }

    @Test
    @DisplayName("Verifica se o método convertToDTO lança uma exceção com entrada nula.")
    void TestConvertToDTOWithNullUser() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            userService.convertToDTO(null);
        }, "O método convertToDTO deve lançar uma exceção quando o usuário for nulo.");
    }

    @Test
    @DisplayName("Verifica se o método validateEmail lança exceção quando o email é nulo.")
    void TestValidateEmailThrowsExceptionWithNull() {
        Assertions.assertThrows(NullPointerException.class, () -> userService.validateEmail(null),
                "O método validateEmail deveria lançar exceção para entradas nulas.");
    }

    @Test
    @DisplayName("Verifica se o o comportamento do método validate com lista de chaves nula.")
    void TestValidateWithNullKeyList() {
        User user = new User(1L, "Zcouve", "Zé das Couves", "zcouve@exemplo.com", "ADMIN");
        Assertions.assertThrows(NullPointerException.class, () -> userService.validate(user, null),
                "O método validate deveria lançar exceção para lista de chaves nula.");
    }

    /***
     * Teste unitário que verifica se o método validate lida corretamente com lista de chaves vazia.
     */
    @Test
    @DisplayName("Verifica se o o comportamento do método validate com lista de chaves vazia.")
    void TestValidateWithEmptyKeyList() {
        User user = new User(1L, "Zcouve", "Zé das Couves", "zcouve@exemplo.com", "ADMIN");
        boolean result = userService.validate(user, new ArrayList<>());

        Assertions.assertFalse(result, "O método validate deveria retornar false para lista de chaves vazia.");
    }

    /***
     * Teste unitário que verifica se o método validateEmail retorna falso para e-mails sem arroba.
     */
    @Test
    @DisplayName("Deve retornar FALSO para e-mails sem arroba")
    void TestValidateEmailWithoutAtSymbol() {
        boolean result = userService.validateEmail("lala.email.com");
        Assertions.assertFalse(result, "O método validateEmail deveria retornar false para e-mails sem arroba.");
    }

    /***
     * Teste unitário que verifica se o método validateEmail retorna verdadeiro para e-mails válidos com subdomínios.
     */
    @Test
    @DisplayName("Deve retornar VERDADEIRO para e-mails válidos com subdomínios.")
    void TestValidateEmailWithSubdomain() {
        boolean result = userService.validateEmail("usuario@sub.dominio.com");
        Assertions.assertTrue(result, "O método validateEmail deveria retornar true para e-mails válidos com subdomínios.");
    }
}
