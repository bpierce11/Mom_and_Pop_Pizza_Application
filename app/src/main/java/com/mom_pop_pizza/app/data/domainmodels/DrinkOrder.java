package com.mom_pop_pizza.app.data.domainmodels;

import com.mom_pop_pizza.app.data.enums.Drink;
import com.mom_pop_pizza.app.data.enums.DrinkSize;

import java.util.UUID;

public class DrinkOrder {
    private UUID id;
    private DrinkSize size;
    private int quantity;
    private Drink drinkType;

    public DrinkOrder() {
        id = UUID.randomUUID();
        size = DrinkSize.Small;
    }

    public UUID getId() {
        return id;
    }

    public DrinkSize getSize() {
        return size;
    }

    public void setSize(DrinkSize size) {
        this.size = size;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Drink getDrinkType() {
        return drinkType;
    }

    public void setDrinkType(Drink drinkType) {
        this.drinkType = drinkType;
    }
}
