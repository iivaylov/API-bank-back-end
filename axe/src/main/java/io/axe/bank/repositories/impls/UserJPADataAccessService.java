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
        return Optional.empty();
    }

    @Override
    public void insertUser(User user) {

    }

    @Override
    public boolean existsUserWithEmail(String email) {
        return false;
    }
}
