package fr.mybodysocial.mybodyfunding.enums;

import lombok.Getter;


@Getter
public enum UserStatus {
    /**
     * Entity is disabled. It cannot do anything until it gets back to enabled.
     */
    ENABLED(true),
    DISABLED(false);

    private final boolean value;

    UserStatus(boolean pValue) {
        this.value = pValue;
    }

}
