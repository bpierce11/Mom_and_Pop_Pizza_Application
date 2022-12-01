package com.mom_pop_pizza.app.data.enums;

public enum Topping {
    Ham("Ham"),
    Pepperoni("Pepperoni"),
    ItalianSausage("Italian Sausage"),
    Chicken("Chicken"),
    Bacon("Bacon"),
    PhillySteak("Philly Steak"),
    Onions("Onions"),
    DicedTomatoes("Diced Tomatoes"),
    Mushrooms("Mushrooms"),
    Pineapple("Pineapple"),
    GreenPeppers("Green Peppers"),
    ExtraCheese("Cheese");

    private final String displayName;

    Topping(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}