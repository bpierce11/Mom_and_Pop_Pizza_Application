package com.mom_pop_pizza.app.controllers;

import com.google.inject.Inject;
import com.mom_pop_pizza.app.Navigator;
import com.mom_pop_pizza.app.controllers.Controller;
import com.mom_pop_pizza.app.data.StateContainer;
import com.mom_pop_pizza.app.data.domainmodels.DrinkOrder;
import com.mom_pop_pizza.app.data.enums.Drink;
import com.mom_pop_pizza.app.data.enums.DrinkSize;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.text.Text;

import java.io.IOException;
import java.util.ArrayList;

public class DrinkController extends Controller {
    @FXML
    private ArrayList<RadioButton> sizeRbs;
    @FXML
    private ArrayList<RadioButton> typeRbs;
    @FXML
    private Button btn_increaseQuantity;
    @FXML
    private Button btn_reduceQuantity;
    @FXML
    private Label lbl_quantity;
    @FXML
    private Text txt_price;

    private StateContainer stateContainer;
    private DrinkOrder drinkOrder;

    @Inject
    public DrinkController(Navigator navigator, StateContainer stateContainer) {
        super(navigator);
        drinkOrder = new DrinkOrder();
        drinkOrder.setSize(DrinkSize.Small);
        drinkOrder.setDrinkType(Drink.Pepsi);
        drinkOrder.setQuantity(1);
        this.stateContainer = stateContainer;
    }

    @FXML
    private void initialize(){
        refreshQuantity();
        initializeRadioButtons();
    }

    private void initializeRadioButtons(){
        var sizeGroup = new ToggleGroup();
        for(var rb : sizeRbs){
            rb.setToggleGroup(sizeGroup);
            sizeGroup.selectedToggleProperty().addListener((observableValue, toggle, t1) -> {
                var sizeName = sizeGroup.getSelectedToggle().getUserData().toString().trim();
                drinkOrder.setSize(DrinkSize.valueOf(sizeName));
            });
        }

        var typeGroup = new ToggleGroup();
        for(var rb : typeRbs){
            rb.setToggleGroup(typeGroup);
            typeGroup.selectedToggleProperty().addListener((observableValue, toggle, t1) -> {
                var typeName = typeGroup.getSelectedToggle().getUserData().toString().trim();
                drinkOrder.setDrinkType(Drink.valueOf(typeName));
            });
        }
    }

    @FXML
    private void increaseQuantity(){
        var quantity = drinkOrder.getQuantity();
        drinkOrder.setQuantity(++quantity);

        btn_reduceQuantity.setDisable(false);
        if(quantity > 98){
            btn_increaseQuantity.setDisable(true);
        }

        refreshQuantity();
        refreshPrice();
    }

    @FXML
    private void decreaseQuantity(){
        var quantity = drinkOrder.getQuantity();
        drinkOrder.setQuantity(--quantity);

        btn_increaseQuantity.setDisable(false);
        if(quantity < 1){
            btn_reduceQuantity.setDisable(true);
        }

        refreshQuantity();
        refreshPrice();
    }

    private void refreshQuantity(){
        lbl_quantity.setText(Integer.toString(drinkOrder.getQuantity()));
    }

    private void refreshPrice(){
        txt_price.setText(Double.valueOf(drinkOrder.getQuantity()).toString());
    }

    @FXML
    private void addDrink() throws IOException {
        stateContainer.getOrder().addDrink(drinkOrder);
        redirectTo("pages/MainMenu.fxml");
    }

    @FXML
    private void backToMenu() throws IOException {
        redirectTo("pages/MainMenu.fxml");
    }
}
