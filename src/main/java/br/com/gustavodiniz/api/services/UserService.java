package br.com.gustavodiniz.api.services;

import br.com.gustavodiniz.api.dtos.UserDTO;
import br.com.gustavodiniz.api.models.UserModel;

import java.util.List;

public interface UserService {
    UserModel findById(Integer id);

    List<UserModel> findAll();

    UserModel create(UserDTO userDTO);
}
