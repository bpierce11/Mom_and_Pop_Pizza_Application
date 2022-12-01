package com.mom_pop_pizza.app.controllers;

import com.google.inject.Inject;
import com.mom_pop_pizza.app.Navigator;
import com.mom_pop_pizza.app.data.StateContainer;
import com.mom_pop_pizza.app.data.domainmodels.Pizza;
import com.mom_pop_pizza.app.data.enums.CrustType;
import com.mom_pop_pizza.app.data.enums.PizzaSize;
import com.mom_pop_pizza.app.data.enums.Topping;
import com.mom_pop_pizza.app.services.OrderManager;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class PizzaController extends Controller {
    @FXML
    private ArrayList<CheckBox> toppingsCheckBoxes;
    @FXML
    private ArrayList<RadioButton> sizeRadioButtons;
    @FXML
    private ArrayList<RadioButton> crustRadioButtons;
    @FXML
    private Text txt_price;
    @FXML
    private Label lbl_validationMsg;

    private OrderManager orderManager;
    private StateContainer stateContainer;
    private Pizza currentPizza;

    @Inject
    public PizzaController(Navigator navigator, OrderManager orderManager, StateContainer stateContainer) {
        super(navigator);
        this.orderManager = orderManager;
        this.stateContainer = stateContainer;
        currentPizza = new Pizza();
    }

    @FXML
    protected void initialize(){
        initializeComponents();
        refreshPizzaPrice();
    }

    private void initializeComponents(){
        var toggleGroup = new ToggleGroup();
        for(var rb : sizeRadioButtons){
            rb.setToggleGroup(toggleGroup);
            var text = rb.getText();
            for (var size : PizzaSize.values()){
                if(size.getDisplayName().equals(text)){
                    rb.setUserData(size);
                }
            }
        }
        toggleGroup.selectedToggleProperty().addListener((observableValue, toggle, t1) -> {
            currentPizza.setSize((PizzaSize)toggleGroup.getSelectedToggle().getUserData());
            refreshPizzaPrice();
        });

        var toggleGroup2 = new ToggleGroup();
        for(var rb : crustRadioButtons){
            rb.setToggleGroup(toggleGroup2);
            var text = rb.getText();
            for (var crust : CrustType.values()){
                if(crust.getDisplayName().equals(text)){
                    rb.setUserData(crust);
                }
            }
        }
        toggleGroup2.selectedToggleProperty().addListener((observableValue, toggle, t1) ->
            currentPizza.setCrustType((CrustType)toggleGroup2.getSelectedToggle().getUserData()));

        for(var cb : toppingsCheckBoxes){
            var text = cb.getId().toLowerCase();
            for(var topping : Topping.values()){
                var toppingName = topping.getDisplayName().replace(" ", "").toLowerCase();
                if(text.contains(toppingName)){
                    cb.setUserData(topping);
                }
            }

            cb.selectedProperty().addListener((observableValue, oldVal, newVal) -> {
                if(newVal){
                    currentPizza.addTopping((Topping)cb.getUserData());
                } else {
                    currentPizza.removeTopping((Topping)cb.getUserData());
                }
                validateToppings();
                refreshPizzaPrice();
            });
        }
    }

    private void refreshPizzaPrice(){
        var price = orderManager.calculatePizzaPrice(currentPizza);
        txt_price.setText(String.format("%.2f", price));
    }

    private boolean validateToppings(){
        lbl_validationMsg.setVisible(false);

        if(currentPizza.getToppings().size() < 1){
            lbl_validationMsg.setText("Please select at least 1 topping.");
            lbl_validationMsg.setVisible(true);
            return false;
        }

        if(currentPizza.getToppings().size() > 4){
            lbl_validationMsg.setText("Too many toppings. Please reduce the quantity of toppings.");
            lbl_validationMsg.setVisible(true);
            return false;
        }
        return true;
    }

    @FXML
    private void addPizza() throws IOException {
        if(!validateToppings()){
            return;
        }
        stateContainer.addPizza(currentPizza);
        redirectTo("pages/MainMenu.fxml");
    }

    @FXML
    private void backToMenu() throws IOException {
        redirectTo("pages/MainMenu.fxml");
    }
}
