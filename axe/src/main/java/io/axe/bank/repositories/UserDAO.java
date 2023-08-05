package io.axe.bank.repositories;

import io.axe.bank.models.User;

import java.util.Optional;

public interface UserDAO {

    Optional<User> selectUserByEmail(String email);

    Optional<User> selectUserById(Integer userId);

    void insertUser(User user);

    void updateUser(User user);

    void deleteUser(User user);

    boolean emailExists(String email);
}
