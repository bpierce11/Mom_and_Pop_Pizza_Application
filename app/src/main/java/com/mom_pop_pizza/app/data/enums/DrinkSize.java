package com.mom_pop_pizza.app.data.enums;

public enum DrinkSize {
    Small("Small"),
    Medium("Medium"),
    Large("Large");

    private final String displayName;

    DrinkSize(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
