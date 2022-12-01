package com.mom_pop_pizza.app;

import com.google.inject.Inject;
import com.google.inject.Injector;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class Navigator {
    private Stage stage;
    private Injector injector;

    public Navigator(Stage stage, DIModule module) {
        this.stage = stage;
        module.addSingleton(Navigator.class, this);
        this.injector = module.createInjector();
    }

    public void navigateTo(String path) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(path));
        fxmlLoader.setControllerFactory(controllerType -> {
            var controller = injector.getInstance(controllerType);
            return controller;
        });
        Scene scene = new Scene(fxmlLoader.load(), 428, 920);
        stage.setScene(scene);
    }
}
