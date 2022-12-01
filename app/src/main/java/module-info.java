module com.mom_pop_pizza.app {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.persistence;
    requires java.sql;
    requires org.hibernate.orm.core;
    requires liquibase.core;
    requires com.google.guice;

    opens com.mom_pop_pizza.app to javafx.fxml;
    exports com.mom_pop_pizza.app;
    exports com.mom_pop_pizza.app.controllers;
    exports com.mom_pop_pizza.app.data to com.google.guice;
    exports com.mom_pop_pizza.app.services to com.google.guice;
    opens com.mom_pop_pizza.app.controllers to javafx.fxml;
    opens com.mom_pop_pizza.app.data.entities;
}