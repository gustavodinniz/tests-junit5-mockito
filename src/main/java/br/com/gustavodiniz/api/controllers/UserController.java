package br.com.gustavodiniz.api.controllers;

import br.com.gustavodiniz.api.dtos.UserDTO;
import br.com.gustavodiniz.api.models.UserModel;
import br.com.gustavodiniz.api.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

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

    @GetMapping
    public ResponseEntity<List<UserDTO>> findAll() {

        return ResponseEntity.ok()
                .body(userService.findAll()
                        .stream()
                        .map(x -> modelMapper.map(x, UserDTO.class)).collect(Collectors.toList()));
    }

    @PostMapping
    public ResponseEntity<UserDTO> create(@RequestBody UserDTO userDTO) {
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(userService.create(userDTO).getId()).toUri();

        return ResponseEntity.created(uri).build();
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<UserDTO> update(@PathVariable Integer id, @RequestBody UserDTO userDTO) {
        userDTO.setId(id);
        UserModel userModel = userService.update(userDTO);
        return ResponseEntity.ok().body(modelMapper.map(userModel, UserDTO.class));
    }
}
