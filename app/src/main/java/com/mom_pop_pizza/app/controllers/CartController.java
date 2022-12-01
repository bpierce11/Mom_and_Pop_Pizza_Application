package com.mom_pop_pizza.app.controllers;

import com.google.inject.Inject;
import com.mom_pop_pizza.app.Navigator;
import com.mom_pop_pizza.app.Startup;
import com.mom_pop_pizza.app.data.StateContainer;
import com.mom_pop_pizza.app.data.domainmodels.DrinkOrder;
import com.mom_pop_pizza.app.data.domainmodels.Order;
import com.mom_pop_pizza.app.data.domainmodels.Pizza;
import com.mom_pop_pizza.app.services.OrderManager;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

public class CartController extends Controller{

    @FXML
    private VBox vb_itemContainer;
    @FXML
    private Button btn_submit;
    @FXML
    private Label lbl_subtotal;
    @FXML
    private Label lbl_deliveryFee;
    @FXML
    private Label lbl_tax;
    @FXML
    private Text txt_total;

    private StateContainer stateContainer;

    private OrderManager orderManager;

    @Inject
    public CartController(Navigator navigator, StateContainer stateContainer, OrderManager orderManager) {
        super(navigator);
        this.stateContainer = stateContainer;
        this.orderManager = orderManager;
    }

    @FXML
    private void initialize() throws URISyntaxException {
        refreshItems();
    }

    private void refreshItems() throws URISyntaxException {
        refreshGrid();
        refreshPrice();
    }

    private void refreshGrid() throws URISyntaxException {
        vb_itemContainer.getChildren().clear();
        var pizzas = stateContainer.getOrder().getPizzas();
        var drinks = stateContainer.getOrder().getDrinks();

        var alternateCell = false;
        for (var pizza : pizzas) {
            var hbox = new HBox();
            hbox.setSpacing(20);
            hbox.setMinHeight(100);
            hbox.setAlignment(Pos.CENTER);
            hbox.setStyle("-fx-background-color: " + (alternateCell ? "lightgrey" : "transparent") + ";");

            var imagePath = Startup.class.getResource("images/pizzaslice.png").toURI().toString();
            var imageView = new ImageView(new Image(imagePath));
            imageView.setPreserveRatio(true);
            imageView.setFitHeight(40);
            var imgHbox = new HBox();
            imgHbox.setAlignment(Pos.CENTER);
            imgHbox.setStyle("-fx-padding: 0 0 0 5");
            imgHbox.getChildren().add(imageView);

            var descriptionText = "";
            descriptionText += "Size: " + pizza.getSize() + "\n";
            descriptionText += "Crust: " + pizza.getCrustType() + "\n";
            descriptionText += "Toppings:" + pizza.getToppings().stream().reduce("",
                    (result, topping2) -> result += "\n\t" + topping2.getDisplayName(),
                    (result1, result2) -> result1 += result2);
            var description = new Label(descriptionText);
            description.setMinWidth(30);
            description.setFont(new Font(10));

            var region = new Region();
            HBox.setHgrow(region, Priority.ALWAYS);

            var quantity = new Label("x" + Integer.toString(pizza.getQuantity()));
            quantity.setMinWidth(10);
            quantity.setFont(new Font(20));

            var price = new Label(String.format("$%.2f", orderManager.calculatePizzaPrice(pizza)));
            price.setMinWidth(10);
            price.setFont(new Font(15));

            var button = new Button("X");
            button.setStyle("-fx-background-color: #f81b22; -fx-font-weight: bold;");
            button.setTextFill(Paint.valueOf("White"));
            button.setScaleX(1.2);
            button.setScaleY(1.2);
            button.setOnAction(actionEvent -> {
                removePizza(pizza);
                try {
                    refreshItems();
                } catch (URISyntaxException e) { }
            });
            HBox.setMargin(button, new Insets(0, 20, 0, 0));

            hbox.getChildren().addAll(imgHbox, description, region, quantity, price, button);
            vb_itemContainer.getChildren().add(hbox);
            alternateCell = !alternateCell;
        }

        for (var drink : drinks) {
            var hbox = new HBox();
            hbox.setSpacing(20);
            hbox.setMinHeight(100);
            hbox.setAlignment(Pos.CENTER);
            hbox.setStyle("-fx-background-color: " + (alternateCell ? "lightgrey" : "transparent") + ";");

            var imgHbox = new HBox();
            imgHbox.setAlignment(Pos.CENTER);
            var imagePath = Startup.class.getResource("images/mt.dew trans.png").toURI().toString();
            var imageView = new ImageView(new Image(imagePath));
            imageView.setPreserveRatio(true);
            imageView.setFitHeight(60);
            imgHbox.setStyle("-fx-padding: 0 10 0 20");
            imgHbox.getChildren().add(imageView);

            var descriptionText = "";
            descriptionText += "Size: " + drink.getSize() + "\n";
            descriptionText += "Type: " + drink.getDrinkType().getDisplayName() + "\n";

            var description = new Label(descriptionText);
            description.setMinWidth(30);
            description.setFont(new Font(10));

            var region = new Region();
            HBox.setHgrow(region, Priority.ALWAYS);

            var quantity = new Label("x" + Integer.toString(drink.getQuantity()));
            quantity.setMinWidth(10);
            quantity.setFont(new Font(20));

            var price = new Label(String.format("$%.2f", Double.valueOf(drink.getQuantity())));
            price.setMinWidth(10);
            price.setFont(new Font(15));

            var button = new Button("X");
            button.setStyle("-fx-background-color: #f81b22; -fx-font-weight: bold;");
            button.setTextFill(Paint.valueOf("White"));
            button.setScaleX(1.2);
            button.setScaleY(1.2);
            button.setOnAction(actionEvent -> {
                removeDrink(drink);
                try {
                    refreshItems();
                } catch (URISyntaxException e) { }
            });
            HBox.setMargin(button, new Insets(0, 20, 0, 0));

            hbox.getChildren().addAll(imgHbox, description, region, quantity, price, button);
            vb_itemContainer.getChildren().add(hbox);
            alternateCell = !alternateCell;
        }
    }

    private void refreshPrice(){
        var subtotal = orderManager.calculatePrice();
        var deliveryFee = validateOrder() ? 7.50d : 0.00d;
        var tax = (subtotal + deliveryFee) * 0.0725d;
        var total = subtotal + + deliveryFee + tax;

        lbl_subtotal.setText(String.format("$%.2f", subtotal));
        lbl_deliveryFee.setText(String.format("$%.2f", deliveryFee));
        lbl_tax.setText(String.format("$%.2f", tax));
        txt_total.setText(String.format("$%.2f", total));
    }

    @FXML
    private void backToMenu() throws IOException {

        redirectTo("pages/MainMenu.fxml");
    }

    private boolean validateOrder(){
        var order = stateContainer.getOrder();
        if(order.getPizzas().size() < 1 && order.getDrinks().size() < 1){
            btn_submit.setDisable(true);
            return false;
        }

        btn_submit.setDisable(false);
        return true;
    }

    private void removePizza(Pizza pizza) {
        stateContainer.removePizza(pizza);
    }
    private void removeDrink(DrinkOrder drink){
        stateContainer.getOrder().removeDrink(drink);
    }

    @FXML
    private void submitOrder() throws IOException {
        stateContainer.setOrder(new Order());
        redirectTo("pages/OrderConfirmation.fxml");
    }
}
