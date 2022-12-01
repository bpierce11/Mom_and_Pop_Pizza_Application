package com.mom_pop_pizza.app.services;

import com.google.inject.Inject;
import com.mom_pop_pizza.app.data.CustomerRepository;
import com.mom_pop_pizza.app.data.domainmodels.Customer;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class LoginManager {
    private CustomerRepository customerRepository;

    @Inject
    public LoginManager(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public boolean loginByPhone(String phoneNumber, String password) throws NoSuchAlgorithmException {
        var customer = customerRepository.getByPhoneNumber(phoneNumber);
        return loginCustomer(customer, password);
    }

    public boolean loginByEmail(String email, String password) throws NoSuchAlgorithmException {
        var customer = customerRepository.getByEmail(email);
        return loginCustomer(customer, password);
    }

    private boolean loginCustomer(Customer customer, String password) throws NoSuchAlgorithmException {
        if(customer == null){
            return false;
        }

        MessageDigest md = MessageDigest.getInstance("SHA-512");
        var passwordHashBytes = md.digest(password.getBytes(StandardCharsets.UTF_8));
        var passwordHash = new String(passwordHashBytes, StandardCharsets.UTF_8);
        return customer.getPasswordHash().equals(passwordHash);
    }
}
