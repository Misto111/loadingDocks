package org.docks.loadingdocks.enums;

public enum Role {
    ADMIN,
    USER;

//Ваадин автоматично ще вземе toString() метода
    @Override
    public String toString() {
        switch (this) {
            case ADMIN:
                return "Admin";
            case USER:
                return "User";
            default:
                throw new IllegalArgumentException("Unexpected value: " + this);
        }
    }
}
