package io.axe.bank.models.enums;

import lombok.Getter;

@Getter
public enum AccountStatus {
    ENABLED("enabled"),

    DELETED("deleted"),

    BLOCKED("blocked");

    private final String name;

    AccountStatus(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
