package br.com.gustavodiniz.api.services.impl;

import br.com.gustavodiniz.api.dtos.UserDTO;
import br.com.gustavodiniz.api.models.UserModel;
import br.com.gustavodiniz.api.repositories.UserRepository;
import br.com.gustavodiniz.api.services.exceptions.DataIntegrityViolationException;
import br.com.gustavodiniz.api.services.exceptions.ObjectNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class UserServiceImplTest {

    public static final Integer ID = 1;
    public static final String NAME = "Marcos Leonardo";
    public static final String EMAIL = "marcos@mail.com";
    public static final String PASSWORD = "123";
    public static final int INDEX = 0;
    @InjectMocks
    private UserServiceImpl service;

    @Mock
    private UserRepository repository;

    @Mock
    private ModelMapper modelMapper;

    private UserModel userModel;
    private UserDTO userDTO;
    private Optional<UserModel> userModelOptional;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        startUser();
    }

    @Test
    void whenFindByIdThenReturnAnUserInstance() {
        when(repository.findById(anyInt())).thenReturn(userModelOptional);

        UserModel response = service.findById(ID);

        assertNotNull(response);
        assertEquals(UserModel.class, response.getClass());
        assertEquals(ID, response.getId());
        assertEquals(NAME, response.getName());
        assertEquals(EMAIL, response.getEmail());
    }

    @Test
    void whenFindByIdThenReturnAnObjectNotFoundException() {
        when(repository.findById(anyInt())).thenThrow(new ObjectNotFoundException("User not found."));

        try {
            service.findById(ID);
        } catch (Exception exception) {
            assertEquals(ObjectNotFoundException.class, exception.getClass());
            assertEquals("User not found.", exception.getMessage());
        }
    }

    @Test
    void whenFindAllThenReturnAnListOfUsers() {
        when(repository.findAll()).thenReturn(List.of(userModel));

        List<UserModel> response = service.findAll();

        assertNotNull(response);
        assertEquals(1, response.size());
        assertEquals(UserModel.class, response.get(INDEX).getClass());
        assertEquals(ID, response.get(INDEX).getId());
        assertEquals(NAME, response.get(INDEX).getName());
        assertEquals(EMAIL, response.get(INDEX).getEmail());
        assertEquals(PASSWORD, response.get(INDEX).getPassword());
    }

    @Test
    void whenCreateTheReturnSuccess() {
        when(repository.save(any())).thenReturn(userModel);

        UserModel response = service.create(userDTO);

        assertNotNull(response);
        assertEquals(UserModel.class, response.getClass());
        assertEquals(ID, response.getId());
        assertEquals(NAME, response.getName());
        assertEquals(EMAIL, response.getEmail());
        assertEquals(PASSWORD, response.getPassword());
    }

    @Test
    void whenCreateThenReturnAnDataIntegrityViolationException() {
        when(repository.findByEmail(anyString())).thenReturn(userModelOptional);

        try {
            userModelOptional.get().setId(2);
            service.create(userDTO);
        } catch (Exception exception) {
            assertEquals(DataIntegrityViolationException.class, exception.getClass());
            assertEquals("E-mail already registered in the system.", exception.getMessage());
        }

    }

    @Test
    void whenUpdateTheReturnSuccess() {
        when(repository.save(any())).thenReturn(userModel);

        UserModel response = service.update(userDTO);

        assertNotNull(response);
        assertEquals(UserModel.class, response.getClass());
        assertEquals(ID, response.getId());
        assertEquals(NAME, response.getName());
        assertEquals(EMAIL, response.getEmail());
        assertEquals(PASSWORD, response.getPassword());
    }

    @Test
    void whenUpdateThenReturnAnDataIntegrityViolationException() {
        when(repository.findByEmail(anyString())).thenReturn(userModelOptional);

        try {
            userModelOptional.get().setId(2);
            service.update(userDTO);
        } catch (Exception exception) {
            assertEquals(DataIntegrityViolationException.class, exception.getClass());
            assertEquals("E-mail already registered in the system.", exception.getMessage());
        }

    }

    @Test
    void deleteWithSuccess() {
        when(repository.findById(anyInt())).thenReturn(userModelOptional);
        doNothing().when(repository).deleteById(anyInt());
        service.delete(ID);
        verify(repository, times(1)).deleteById(anyInt());
    }

    @Test
    void deleteWithObjectNotFoundException() {
        when(repository.findById(anyInt())).thenThrow(new ObjectNotFoundException("User not found."));
        try {
            service.delete(ID);
        } catch (Exception exception) {
            assertEquals(ObjectNotFoundException.class, exception.getClass());
            assertEquals("User not found.", exception.getMessage());
        }
    }

    private void startUser() {
        userModel = new UserModel(ID, NAME, EMAIL, PASSWORD);
        userDTO = new UserDTO(ID, NAME, EMAIL, PASSWORD);
        userModelOptional = Optional.of(new UserModel(ID, NAME, EMAIL, PASSWORD));
    }
}