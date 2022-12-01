package com.mom_pop_pizza.app.data;

import com.mom_pop_pizza.app.data.domainmodels.Customer;
import com.mom_pop_pizza.app.data.domainmodels.Order;
import com.mom_pop_pizza.app.data.domainmodels.Pizza;

public class StateContainer {
    private Order order;

    public StateContainer(Order order) {
        this.order = order;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public void addPizza(Pizza pizza){
        order.addPizza(pizza);
    }

    public void removePizza(Pizza pizza){
        order.removePizza(pizza);
    }
}
