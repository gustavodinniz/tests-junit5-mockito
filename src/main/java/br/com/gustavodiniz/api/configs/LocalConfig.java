package br.com.gustavodiniz.api.configs;

import br.com.gustavodiniz.api.models.UserModel;
import br.com.gustavodiniz.api.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.List;

@Configuration
@Profile("local")
public class LocalConfig {

    @Autowired
    private UserRepository userRepository;

    @Bean
    public void startDB() {
        UserModel userModel1 = new UserModel(null, "Gustavo Diniz", "gustavodinniz@hotmail.com", "123");
        UserModel userModel2 = new UserModel(null, "Alberto Chaves", "alberto@hotmail.com", "123");
        UserModel userModel3 = new UserModel(null, "Marisa Gomes", "marisa@hotmail.com", "123");
        UserModel userModel4 = new UserModel(null, "Erica Diniz", "erica@hotmail.com", "123");
        UserModel userModel5 = new UserModel(null, "Camila Diniz", "camila@hotmail.com", "123");

        userRepository.saveAll(List.of(userModel1, userModel2, userModel3, userModel4, userModel5));
    }
}
