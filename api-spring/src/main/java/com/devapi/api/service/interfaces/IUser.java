package com.devapi.api.service.interfaces;

import com.devapi.api.domain.model.UserKey;
import com.devapi.api.domain.model.User;

import java.util.List;

public interface IUser {

    boolean validate(User user, List<UserKey> userKeys);

    boolean validateEmail(String email);
}
