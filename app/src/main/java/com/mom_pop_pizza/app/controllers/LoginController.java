package com.mom_pop_pizza.app.controllers;

import com.google.inject.Inject;
import com.mom_pop_pizza.app.Navigator;
import com.mom_pop_pizza.app.services.LoginManager;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Pattern;

public class LoginController extends Controller {
    private static final Pattern emailRegex = Pattern.compile("(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])", Pattern.CASE_INSENSITIVE);
    private static final Pattern phoneRegex = Pattern.compile("^(\\+0?1\\s)?\\(?\\d{3}\\)?[\\s.-]?\\d{3}[\\s.-]?\\d{4}$", Pattern.CASE_INSENSITIVE);

    @FXML
    private TextField txtFld_userIdentifier;
    @FXML
    private TextField txtFld_userPassword;
    @FXML
    private Label validationMessage;

    private LoginManager loginManager;

    @Inject
    public LoginController(Navigator navigator, LoginManager loginManager) {
        super(navigator);
        this.loginManager = loginManager;
    }

    @FXML
    private void login() throws NoSuchAlgorithmException, IOException {
        validationMessage.setVisible(false);
        var userId = txtFld_userIdentifier.getText();
        var password = txtFld_userPassword.getText();

        var loginSuccessful = false;

        if(userId.length() < 1 || password.length() < 1){
            validationMessage.setVisible(true);
            validationMessage.setText("Please enter a email/phone # and password.");
            return;
        }

        if(emailRegex.matcher(userId).matches()){
            loginSuccessful = loginManager.loginByEmail(userId, password);
        }
        else if(phoneRegex.matcher(userId).matches()) {
            userId = userId.replaceAll("[^\\d]", "");
            loginSuccessful = loginManager.loginByPhone(userId, password);
        }
        else{
            validationMessage.setVisible(true);
            validationMessage.setText("Invalid email/phone #. Please enter a valid email/phone #.");
            return;
        }

        if(!loginSuccessful){
            validationMessage.setVisible(true);
            validationMessage.setText("Incorrect email/phone #. Please try again.");
            return;
        }

        redirectTo("pages/MainMenu.fxml");
    }

    @FXML
    private void redirectToRegistration() throws IOException {
        redirectTo("pages/Registration.fxml");
    }
}
