package br.com.gustavodiniz.api.services;

import br.com.gustavodiniz.api.models.UserModel;

public interface UserService {
    UserModel findById(Integer id);
}
