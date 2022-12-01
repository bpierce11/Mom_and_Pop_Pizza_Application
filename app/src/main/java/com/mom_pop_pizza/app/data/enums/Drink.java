package com.mom_pop_pizza.app.data.enums;

import java.util.UUID;

public enum Drink {
    Pepsi("Pepsi"),
    DietPepsi("Diet Pepsi"),
    Orange("Orange"),
    DietOrange("Diet Orange"),
    RootBeer("Root Beer"),
    DietRootBeer("Diet Root Beer");

    private final String displayName;

    Drink(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
