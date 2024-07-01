package com.dit.hp.janki.enums;

public enum TaskType {

    GET_AUTHENTICATION_TOKEN(1),
    VERIFY_CONTACT(2),
    VERIFY_OTP(3),
    SIGN_UP(4),
    DEFAULT_ADMIN_DIS(5),
    AMBULANCE_BOOKING(6),
    ALL_LABS(7),
    ALL_LAB_TESTS(8),
    LAB_BOOKING(9);

    int value;

    private TaskType(int value) { this.value = value; }
}
