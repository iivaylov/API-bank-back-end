package io.axe.bank;

import io.axe.bank.controllers.requests.RegisterRequest;
import io.axe.bank.models.User;
import io.axe.bank.services.dtos.UserDTO;

import java.time.LocalDateTime;
import java.util.List;

public class Helpers {

    public static UserDTO createUserDTO() {
        return new UserDTO(
                "Yetta",
                "Mccoy",
                "user@example.com",
                "1-562-388-7481");
    }

    public static User createMockUser() {
        User mockUser = new User();
        mockUser.setId(1);
        mockUser.setFirstName("Yetta");
        mockUser.setLastName("Mccoy");
        mockUser.setEmail("user@example.com");
        mockUser.setPassword("SVI29IQF8YL");
        mockUser.setPhoneNumber("1-562-388-7481");
        mockUser.setTownName("Newport");
        mockUser.setCountry("Russian Federation");
        mockUser.setDeleted(false);
        mockUser.setCreatedAt(LocalDateTime.now());
        mockUser.setAccounts(List.of());
        return mockUser;
    }

    public static RegisterRequest registration() {
        RegisterRequest register = new RegisterRequest();
        register.setFirstname("Yetta");
        register.setLastname("Mccoy");
        register.setEmail("ultrices.mauris.ipsum@icloud.edu");
        register.setPassword("SVI29IQF8YL");
        register.setConfirmPassword("SVI29IQF8YL");
        register.setPhoneNumber("1-562-388-7481");
        register.setTownName("Newport");
        register.setCountry("Russian Federation");
        return register;
    }
}
