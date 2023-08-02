package io.axe.bank.repositories;

import io.axe.bank.models.User;

import java.util.Optional;

public interface UserDAO {

    Optional<User> selectUserByEmail(String email);

    void insertUser(User user);

    void updateUser(User user);

    boolean emailExists(String email);
}
