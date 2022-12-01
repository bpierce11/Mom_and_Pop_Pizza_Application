package com.mom_pop_pizza.app.controllers;

import com.google.inject.Inject;
import com.mom_pop_pizza.app.Navigator;
import javafx.fxml.FXML;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class OrderConfirmationController extends Controller {

    @Inject
    public OrderConfirmationController(Navigator navigator) throws InterruptedException {
        super(navigator);
    }

    @FXML
    private void redirectToMenu() throws IOException, InterruptedException {
        redirectTo("pages/MainMenu.fxml");
    }
}
