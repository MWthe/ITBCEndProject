package com.example.EndProjectITBC.enums;

public enum LogType {

    ERROR(1),
    WARNING(2),
    INFO(3);

    private final int value;

    LogType(final int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
