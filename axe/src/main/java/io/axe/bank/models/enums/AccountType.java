package io.axe.bank.models.enums;

import lombok.Getter;

@Getter
public enum AccountType {
    CACC("Current"),

    COMM("Commission"),

    SVGS("Savings"),

    SLRY("Salary");

    private final String name;

    AccountType(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
