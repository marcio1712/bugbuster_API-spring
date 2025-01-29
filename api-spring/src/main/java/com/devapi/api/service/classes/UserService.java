package com.devapi.api.service.classes;

import com.devapi.api.domain.dtos.UserDTO;
import com.devapi.api.domain.model.UserKey;
import com.devapi.api.domain.model.User;
import com.devapi.api.service.interfaces.IUser;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class UserService implements IUser {
    @Autowired
    private static ModelMapper modelMapper = new ModelMapper();

    public UserDTO convertToDTO(User user) {
        return modelMapper.map(user, UserDTO.class);
    }

    public static User convertToEntity(UserDTO userDTO) {
        return modelMapper.map(userDTO, User.class);
    }

    /***
     * Método responsável por válidar um usuário com a
     * @param user o usuário que será verificado.
     * @param userKeys a lista de keys cadastradas no banco de dados.
     * @return um boolean que retorna se o usuário está validado ou não.
     */
    @Override
    public boolean validate(User user, List<UserKey> userKeys) {
        return validateImpl(user, userKeys);
    }

    /***
     * Método responsável por verificar se um usuário possui a key de autorização para cadastrar um evento.
     * @param user o usuário que será verificado.
     * @param userKeys a lista de keys cadastradas no banco de dados.
     * @return um boolean que retorna se o usuário está validado ou não.
     */
    private boolean validateImpl(User user, List<UserKey> userKeys){

        for(UserKey element : userKeys){
            if(element.getUser().getEmail().equals(user.getEmail())){
                return true;
            }
        }
        return false;
    }

    public boolean validateEmail(String email){
        return validateEmailImpl(email);
    }

    private boolean validateEmailImpl(String email){
        return email.contains("@");
    }
}
