package com.mom_pop_pizza.app.controllers;

import com.mom_pop_pizza.app.Navigator;
import com.mom_pop_pizza.app.Startup;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;

public abstract class Controller {

    private Navigator navigator;

    protected Controller(Navigator navigator) {
        this.navigator = navigator;
    }

    protected void redirectTo(String fxmlPath) throws IOException {
        navigator.navigateTo(fxmlPath);
    }
}
