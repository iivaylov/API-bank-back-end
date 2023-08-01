package io.axe.bank.services.impls;

import io.axe.bank.models.User;
import io.axe.bank.services.UserService;
import io.axe.bank.services.dtos.UserDTO;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Override
    public UserDTO getUserByEmail(String email) {
        return null;
    }

    @Override
    public UserDTO addUser(User user) {
        return null;
    }

    @Override
    public UserDTO updateUser(User user) {
        return null;
    }

    @Override
    public void deleteUser(String email) {

    }
}
