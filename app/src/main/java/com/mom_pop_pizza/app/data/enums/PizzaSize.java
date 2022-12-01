package com.mom_pop_pizza.app.data.enums;

public enum PizzaSize {
    Small(4d, "Small"),
    Medium(6d, "Medium"),
    Large(8d, "Large"),
    ExtraLarge(10d, "Extra-Large");

    private final double price;
    private final String displayName;
    PizzaSize(double price, String displayName) {
        this.price = price;
        this.displayName = displayName;
    }

    public double getPrice() {
        return price;
    }

    public String getDisplayName() {
        return displayName;
    }
}
