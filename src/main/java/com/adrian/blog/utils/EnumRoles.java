package com.adrian.blog.utils;

/**
 * The Roles Enum class
 *
 * @author Adrian Paul
 * @version 1.0
 * Date 14/09/2018.
 */
public enum EnumRoles {
    ROLE_ADMIN(1), ROLE_USER(2);
    private int value;

    EnumRoles(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
