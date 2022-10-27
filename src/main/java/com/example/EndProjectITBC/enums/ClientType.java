package com.example.EndProjectITBC.enums;

public enum ClientType {

    ADMIN(1),
    USER(2);

    private final Integer value;

    ClientType(final Integer value) {
       this.value = value;
    }

    public Integer getValue() {
        return value;
    }
}
