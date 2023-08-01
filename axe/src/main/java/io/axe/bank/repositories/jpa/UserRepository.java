package io.axe.bank.repositories.jpa;

import io.axe.bank.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {

}
