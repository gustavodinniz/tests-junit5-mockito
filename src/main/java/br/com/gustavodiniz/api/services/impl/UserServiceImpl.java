package br.com.gustavodiniz.api.services.impl;

import br.com.gustavodiniz.api.models.UserModel;
import br.com.gustavodiniz.api.repositories.UserRepository;
import br.com.gustavodiniz.api.services.UserService;
import br.com.gustavodiniz.api.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserModel findById(Integer id) {
        Optional<UserModel> userModelOptional = userRepository.findById(id);
        return userModelOptional.orElseThrow(() -> new ObjectNotFoundException("User not found."));
    }

    public List<UserModel> findAll() {
        return userRepository.findAll();
    }
}
