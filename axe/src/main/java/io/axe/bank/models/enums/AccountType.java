package io.axe.bank.models.enums;

public enum AccountType {
    CACC("Current"),

    COMM("Commission"),

    SVGS("Savings"),

    SLRY("Salary");

    private final String name;

    AccountType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
