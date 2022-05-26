package br.com.gustavodiniz.api.controllers;

import br.com.gustavodiniz.api.dtos.UserDTO;
import br.com.gustavodiniz.api.models.UserModel;
import br.com.gustavodiniz.api.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private UserService userService;

    @GetMapping(value = "/{id}")
    public ResponseEntity<UserDTO> findById(@PathVariable Integer id) {
        UserModel userModel = userService.findById(id);
        return ResponseEntity.ok().body(modelMapper.map(userModel, UserDTO.class));
    }
}
