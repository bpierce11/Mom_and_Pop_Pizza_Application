module com.mom_pop_pizza.app {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.mom_pop_pizza.app to javafx.fxml;
    exports com.mom_pop_pizza.app;
    exports com.mom_pop_pizza.app.controllers;
    opens com.mom_pop_pizza.app.controllers to javafx.fxml;
}