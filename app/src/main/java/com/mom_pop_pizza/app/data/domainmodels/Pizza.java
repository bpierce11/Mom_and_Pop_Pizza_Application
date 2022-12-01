package com.mom_pop_pizza.app.data.domainmodels;

import com.mom_pop_pizza.app.data.enums.CrustType;
import com.mom_pop_pizza.app.data.enums.PizzaSize;
import com.mom_pop_pizza.app.data.enums.Topping;

import java.util.ArrayList;
import java.util.UUID;

public class Pizza {
    private UUID id;
    private int quantity;
    private CrustType crustType;
    private PizzaSize size;
    private ArrayList<Topping> toppings;

    public Pizza() {
        id = UUID.randomUUID();
        quantity = 1;
        crustType = CrustType.Regular;
        size = PizzaSize.Small;
        toppings = new ArrayList<>();
    }

    public UUID getId() {
        return id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public CrustType getCrustType() {
        return crustType;
    }

    public void setCrustType(CrustType crustType) {
        this.crustType = crustType;
    }

    public PizzaSize getSize() {
        return size;
    }

    public void setSize(PizzaSize size) {
        this.size = size;
    }

    public ArrayList<Topping> getToppings() {
        return toppings;
    }

    public void setToppings(ArrayList<Topping> toppings) {
        this.toppings = toppings;
    }

    public void addTopping(Topping topping){
        toppings.add(topping);
    }

    public void removeTopping(Topping topping) {
        toppings.remove(topping);
    }
}
