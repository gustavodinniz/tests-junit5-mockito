package br.com.gustavodiniz.api.repositories;

import br.com.gustavodiniz.api.models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserModel, Integer> {
}
