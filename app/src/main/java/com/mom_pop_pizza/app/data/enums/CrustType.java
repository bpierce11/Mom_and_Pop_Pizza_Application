package com.mom_pop_pizza.app.data.enums;

public enum CrustType {
    Thin("Thin"),
    Regular("Regular"),
    Pan("Pan");

    private final String displayName;

    CrustType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
