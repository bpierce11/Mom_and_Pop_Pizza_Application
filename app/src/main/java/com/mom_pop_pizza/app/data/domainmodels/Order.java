package com.mom_pop_pizza.app.data.domainmodels;

import java.util.ArrayList;
import java.util.UUID;

public class Order {
    private UUID id;
    private ArrayList<Pizza> pizzas;
    private ArrayList<DrinkOrder> drinks;
    private Customer customer;

    public Order() {
        id = UUID.randomUUID();
        pizzas = new ArrayList<>();
        drinks = new ArrayList<>();
    }

    public UUID getId() {
        return id;
    }

    public ArrayList<Pizza> getPizzas() {
        return pizzas;
    }

    public void setPizzas(ArrayList<Pizza> pizzas) {
        this.pizzas = pizzas;
    }

    public void addPizza(Pizza pizza){
        pizzas.add(pizza);
    }

    public void removePizza(Pizza pizza){
        this.pizzas.removeIf(pizza1 -> pizza1.getId().equals(pizza.getId()));
    }

    public ArrayList<DrinkOrder> getDrinks() {
        return drinks;
    }

    public void setDrinks(ArrayList<DrinkOrder> drinks) {
        this.drinks = drinks;
    }

    public void addDrink(DrinkOrder drink){
        drinks.add(drink);
    }

    public void removeDrink(DrinkOrder drink){
        drinks.removeIf(drink1 -> drink1.getId().equals(drink.getId()));
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
