package com.mom_pop_pizza.app;

import com.google.inject.Guice;
import com.mom_pop_pizza.app.controllers.Controller;
import com.mom_pop_pizza.app.controllers.HelloController;
import com.mom_pop_pizza.app.data.CustomerRepository;
import com.mom_pop_pizza.app.data.MigrationRunner;
import com.mom_pop_pizza.app.services.AccountManager;
import com.mom_pop_pizza.app.services.LoginManager;
import com.mom_pop_pizza.app.viewmodels.CreateCustomerViewModel;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

public class Startup extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        runMigrations();

        var navigator = initializeInjectedNavigation(stage);

        navigator.navigateTo("pages/Login.fxml");
        stage.setTitle("Mom & Pop Pizzeria");
        stage.show();

        //"1111111111", "John", "Doe", "JDoe@gmail.com", "123@bc456"
    }

    public static void main(String[] args) {
        launch();
    }

    public static void runMigrations() throws Exception {
        (new MigrationRunner()).runMigrations();
    }

    public static Navigator initializeInjectedNavigation(Stage stage){
        var diModule = new DIModule();
        var navigator = new Navigator(stage, diModule);
        return  navigator;
    }
}