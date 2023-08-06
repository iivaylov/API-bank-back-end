package io.axe.bank;

import io.axe.bank.controllers.requests.RegisterRequest;
import io.axe.bank.controllers.requests.TransferRequest;
import io.axe.bank.models.Account;
import io.axe.bank.models.User;
import io.axe.bank.models.enums.AccountStatus;
import io.axe.bank.models.enums.AccountType;
import io.axe.bank.services.dtos.AccountDTO;
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

    public static AccountDTO createAccountDTO(){
        return new AccountDTO(
                1,
                "AXE#U26VNZ1SBD0TPO1V",
                AccountType.SVGS,
                AccountStatus.ENABLED,
                100.00,
                "USD"
        );
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

    public static Account createMockAccount(){
        Account mockAccount = new Account();
        mockAccount.setId(1);
        mockAccount.setIban("AXE#U26VNZ1SBD0TPO1V");
        mockAccount.setAccountType(AccountType.SVGS);
        mockAccount.setAccountStatus(AccountStatus.ENABLED);
        mockAccount.setBalance(100.00);
        mockAccount.setCurrency("USD");
        mockAccount.setClosed(false);

        return mockAccount;
    }

    public static AccountDTO createMockFromAccountDTO() {
        return new AccountDTO(1,
                "sender_account_iban",
                AccountType.SVGS,
                AccountStatus.ENABLED,
                1000.00,
                "USD");
    }

    public static Account createMockSenderAccount(){
       Account senderAccount = new Account();
       senderAccount.setId(1);
       senderAccount.setIban("sender_account_iban");
       senderAccount.setAccountType(AccountType.SVGS);
       senderAccount.setAccountStatus(AccountStatus.ENABLED);
       senderAccount.setBalance(1000.00);
       senderAccount.setCurrency("USD");
       senderAccount.setClosed(false);
       return senderAccount;
    }

    public static Account createMockReceiverAccount(){
        Account receiverAccount = new Account();
        receiverAccount.setId(2);
        receiverAccount.setIban("receiver_account_iban");
        receiverAccount.setAccountType(AccountType.SVGS);
        receiverAccount.setAccountStatus(AccountStatus.ENABLED);
        receiverAccount.setBalance(500.00);
        receiverAccount.setCurrency("USD");
        receiverAccount.setClosed(false);
        return receiverAccount;
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

    public static TransferRequest transfer(){
        TransferRequest transfer = new TransferRequest();
        transfer.setAccountToIban("receiver_account_iban");
        transfer.setAmountToTransfer(500.00);

        return transfer;
    }
}
