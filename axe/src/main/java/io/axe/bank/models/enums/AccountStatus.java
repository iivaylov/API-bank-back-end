package io.axe.bank.models.enums;

public enum AccountStatus {
    ENABLED("enabled"),

    DELETED("deleted"),

    BLOCKED("blocked");

    private final String name;

    AccountStatus(String name) {
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
