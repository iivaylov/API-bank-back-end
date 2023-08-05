package io.axe.bank.repositories.impls;

import io.axe.bank.models.User;
import io.axe.bank.repositories.UserDAO;
import io.axe.bank.repositories.jpa.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UserJPADataAccessService implements UserDAO {

    private final UserRepository userRepository;

    @Autowired
    public UserJPADataAccessService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public Optional<User> selectUserByEmail(String email) {
        return userRepository.findAll()
                .stream()
                .filter(user -> user.getEmail().equals(email))
                .findFirst();
    }

    @Override
    public Optional<User> selectUserById(Integer userId) {
        return userRepository.findById(userId);
    }

    @Override
    public void insertUser(User user) {
        userRepository.save(user);
    }

    @Override
    public void updateUser(User user) {
        userRepository.save(user);
    }

    @Override
    public void deleteUser(User user) {
        userRepository.delete(user);
    }

    @Override
    public boolean emailExists(String email) {
        return userRepository.findAll()
                .stream()
                .anyMatch(user -> user.getEmail().equals(email));
    }
}
