package com.mom_pop_pizza.app.controllers;

import com.google.inject.Inject;
import com.mom_pop_pizza.app.Navigator;
import com.mom_pop_pizza.app.services.AccountManager;
import com.mom_pop_pizza.app.viewmodels.CreateCustomerViewModel;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class RegistrationController extends Controller {
    private static final Pattern emailRegex = Pattern.compile("(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])", Pattern.CASE_INSENSITIVE);
    private static final Pattern phoneRegex = Pattern.compile("^(\\+0?1\\s)?\\(?\\d{3}\\)?[\\s.-]?\\d{3}[\\s.-]?\\d{4}$", Pattern.CASE_INSENSITIVE);
    private static final Pattern addressRegex = Pattern.compile("^\\d+ (A-Za-z0-9'\\.,)+");
    private static final Pattern zipcodeRegex = Pattern.compile("^\\d{5}");
    private static final Pattern suiteRegex = Pattern.compile("^\\d*");
    private static final Pattern creditCardRegex = Pattern.compile("^\\d{15,16}");
    private static final Pattern monthDateRegex = Pattern.compile("^\\d{2}/\\d{4}");
    private static final Pattern creditCodeRegex = Pattern.compile("^\\d{2}/\\d{4}");
    private static final Pattern stateRegex = Pattern.compile("[A-Za-z ]");

    @FXML
    private Pane pn_Step1;
    @FXML
    private Pane pn_Step2;
    @FXML
    private Pane pn_Step3;

    @FXML
    private TextField txtFld_firstName;
    @FXML
    private TextField txtFld_lastName;
    @FXML
    private TextField txtFld_address;
    @FXML
    private TextField txtFld_suite;
    @FXML
    private TextField textFld_zip;
    @FXML
    private TextField txtFld_phoneNumber;
    @FXML
    private TextField txtFld_email;
    @FXML
    private PasswordField txtFld_newPassword;
    @FXML
    private PasswordField txtFld_confirmNewPassword;
    @FXML
    private TextField txtFld_CCNumber;
    @FXML
    private TextField txtFld_CCexp;
    @FXML
    private TextField txtFld_CCZip;
    @FXML
    private TextField txtFld_CCCode;
    @FXML
    private TextField txtFld_CCAddress;
    @FXML
    private TextField txtFld_CCSuite;
    @FXML
    private TextField txtFld_CCCity;
    @FXML
    private TextField txtFld_CCState;
    @FXML
    private ArrayList<TextField> billAddressItems;
    @FXML
    private Label lbl_validationMsgPart1;
    @FXML
    private Label lbl_validationMsgPart2_identifier;
    @FXML
    private Label lbl_validationMsgPart2_passwords;

    private AccountManager accountManager;

    @Inject
    public RegistrationController(Navigator navigator, AccountManager accountManager) {
        super(navigator);
        this.accountManager = accountManager;
    }

    @FXML
    private void advanceToPart2() {
        if(!arePart1CredentialsValid()){
            return;
        }
        pn_Step1.setVisible(false);
        pn_Step2.setVisible(true);
    }

    @FXML
    private void reverseToPart1() {
        pn_Step2.setVisible(false);
        pn_Step1.setVisible(true);
    }

    @FXML
    private void advanceToPart3() {
        if(!arePart2CredentialsValid()){
            return;
        }
        pn_Step2.setVisible(false);
        pn_Step3.setVisible(true);
    }

    @FXML
    private void reverseToPart2() {
        pn_Step3.setVisible(false);
        pn_Step2.setVisible(true);
    }

    @FXML
    private void navigateToLogin() throws IOException {
        redirectTo("pages/Login.fxml");
    }

    @FXML
    private void createCustomer() throws IOException, NoSuchAlgorithmException {
        var customer = new CreateCustomerViewModel(txtFld_phoneNumber.getText().replaceAll("[^\\d]", ""),
                txtFld_firstName.getText(),
                txtFld_lastName.getText(),
                txtFld_email.getText(),
                txtFld_newPassword.getText());

        accountManager.createCustomerAccount(customer);

        redirectTo("pages/Login.fxml");
    }

    @FXML
    private void toggleBillAddress(){
        for(var txtFld : billAddressItems){
            txtFld.setDisable(!txtFld.isDisabled());
        }
    }

    private boolean arePart1CredentialsValid(){
        lbl_validationMsgPart1.setVisible(false);
        var firstName = txtFld_firstName.getText();
        var lastName = txtFld_lastName.getText();

        var isValid = true;
        var invalidTextMessage = "Error(s):\n";

        if(firstName.length() < 1 || firstName.length() > 30){
            isValid = false;
            invalidTextMessage += "\t- First Name must be between 1 and 30 characters long.\n";
        }

        if(lastName.length() < 1 || lastName.length() > 50){
            isValid = false;
            invalidTextMessage += "\t- Last Name must be between 1 and 50 characters long.\n";
        }

        if(!isValid){
            lbl_validationMsgPart1.setText(invalidTextMessage);
            lbl_validationMsgPart1.setVisible(true);
            return false;
        }

        return true;
    }

    private boolean arePart2CredentialsValid(){
        var identifiersValid = identifiersValid();
        var passwordsValid = passwordsValid();
        return identifiersValid && passwordsValid;
    }

    private boolean passwordsValid(){
        lbl_validationMsgPart2_passwords.setVisible(false);
        var newPassword = txtFld_newPassword.getText();
        var confirmNewPassword = txtFld_confirmNewPassword.getText();

        if(newPassword.isEmpty() || confirmNewPassword.isEmpty()){
            lbl_validationMsgPart2_passwords.setText("Please enter passwords.");
            lbl_validationMsgPart2_passwords.setVisible(true);
            return false;
        }

        if(!newPassword.equals(confirmNewPassword)){
            lbl_validationMsgPart2_passwords.setText("New Password and Confirm New Password must match. \nPlease try again.");
            lbl_validationMsgPart2_passwords.setVisible(true);
            return false;
        }

        return true;
    }

    private boolean identifiersValid(){
        lbl_validationMsgPart2_identifier.setVisible(false);
        var email = txtFld_email.getText();
        var phoneNumber = txtFld_phoneNumber.getText();

        var isValid = true;
        var invalidTextMessage = "Error(s):\n";

        if(!emailRegex.matcher(email).matches()){
            isValid = false;
            invalidTextMessage += "\t- Invalid email. Please enter valid email.\n";
        }
        else if(email.length() > 100){
            isValid = false;
            invalidTextMessage += "\t- Email too long. Please enter shorter email.\n";
        }

        if(!phoneRegex.matcher(phoneNumber).matches()){
            isValid = false;
            invalidTextMessage += "\t- Invalid phone #. Please enter valid phone #.";
        }

        phoneNumber = phoneNumber.replaceAll("[^\\d]", "");
        if(isValid && !accountManager.isUniqueCredentials(email, phoneNumber)){
            isValid = false;
            invalidTextMessage += "\t- Email/phone # not available. Please use unique email\n\t\tand phone #.";
        }

        if(!isValid){
            lbl_validationMsgPart2_identifier.setText(invalidTextMessage);
            lbl_validationMsgPart2_identifier.setVisible(true);
            return false;
        }

        return true;
    }
}
