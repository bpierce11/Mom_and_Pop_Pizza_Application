package com.mom_pop_pizza.app.controllers;

import com.google.inject.Inject;
import com.mom_pop_pizza.app.Navigator;
import javafx.fxml.FXML;

import java.io.IOException;

public class MainMenu extends Controller{
    @Inject
    public MainMenu(Navigator navigator) {
        super(navigator);
    }

    @FXML
    private void navigateToCart() throws IOException {
        redirectTo("pages/Cart.fxml");
    }

    @FXML
    private void navigateToPizzaBuilder() throws IOException {
        redirectTo("pages/BuildPizza.fxml");
    }

    @FXML
    private void navigateToDrinkSelection() throws IOException {
        redirectTo("pages/DrinksMenu.fxml");
    }
}
