package com.mom_pop_pizza.app.services;

import com.google.inject.Inject;
import com.mom_pop_pizza.app.data.StateContainer;
import com.mom_pop_pizza.app.data.domainmodels.Pizza;

public class OrderManager {
    private StateContainer stateContainer;

    @Inject
    public OrderManager(StateContainer stateContainer) {
        this.stateContainer = stateContainer;
    }

    public double calculatePrice(){
        var order = stateContainer.getOrder();
        var cost = 0d;

        var drinks = order.getDrinks();
        for(var drink : drinks){
            cost += (double)drink.getQuantity() * 1.00d;
        }

        var pizzas = order.getPizzas();
        for (var pizza : pizzas) {
            cost += this.calculatePizzaPrice(pizza);
        }

        return cost;
    }

    public double calculatePizzaPrice(Pizza pizza){
        var cost = 0d;
        var size = pizza.getSize();
        var toppingCount = pizza.getToppings().size();
        cost += size.getPrice();

        cost += toppingCount * switch (size){
            case Small -> 0.5d;
            case Medium -> 0.75d;
            case Large -> 1.00d;
            case ExtraLarge -> 1.25d;
        };

        return cost * pizza.getQuantity();
    }
}
