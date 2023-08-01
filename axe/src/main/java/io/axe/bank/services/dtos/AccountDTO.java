package io.axe.bank.services.dtos;

import io.axe.bank.models.enums.AccountStatus;
import io.axe.bank.models.enums.AccountType;

public record AccountDTO(

        Integer id,

        String iban,

        AccountType accountType,

        AccountStatus accountStatus,

        Number balance,

        String currency

) {

}
