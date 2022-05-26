package br.com.gustavodiniz.api.services.impl;

import br.com.gustavodiniz.api.dtos.UserDTO;
import br.com.gustavodiniz.api.models.UserModel;
import br.com.gustavodiniz.api.repositories.UserRepository;
import br.com.gustavodiniz.api.services.UserService;
import br.com.gustavodiniz.api.services.exceptions.DataIntegrityViolationException;
import br.com.gustavodiniz.api.services.exceptions.ObjectNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public UserModel findById(Integer id) {
        Optional<UserModel> userModelOptional = userRepository.findById(id);
        return userModelOptional.orElseThrow(() -> new ObjectNotFoundException("User not found."));
    }

    public List<UserModel> findAll() {
        return userRepository.findAll();
    }

    @Override
    public UserModel create(UserDTO userDTO) {
        findByEmail(userDTO);
        return userRepository.save(modelMapper.map(userDTO, UserModel.class));
    }

    @Override
    public UserModel update(UserDTO userDTO) {
        findByEmail(userDTO);
        return userRepository.save(modelMapper.map(userDTO, UserModel.class));
    }

    @Override
    public void delete(Integer id) {
        findById(id);
        userRepository.deleteById(id);
    }

    private void findByEmail(UserDTO userDTO) {
        Optional<UserModel> userModelOptional = userRepository.findByEmail(userDTO.getEmail());
        if (userModelOptional.isPresent() && !userModelOptional.get().getId().equals(userDTO.getId())) {
            throw new DataIntegrityViolationException("E-mail already registered in the system.");
        }
    }
}
