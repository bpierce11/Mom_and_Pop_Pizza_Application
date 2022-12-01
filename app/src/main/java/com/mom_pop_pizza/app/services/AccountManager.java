package com.mom_pop_pizza.app.services;

import com.google.inject.Inject;
import com.mom_pop_pizza.app.data.CustomerRepository;
import com.mom_pop_pizza.app.data.domainmodels.Customer;
import com.mom_pop_pizza.app.viewmodels.CreateCustomerViewModel;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class AccountManager {

    private CustomerRepository customerRepository;

    @Inject
    public AccountManager(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public boolean createCustomerAccount(CreateCustomerViewModel createCustomer) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-512");
        var passwordHashBytes = md.digest(createCustomer.getPassword().getBytes(StandardCharsets.UTF_8));

        var customer = new Customer(createCustomer.getPhoneNumber(),
                createCustomer.getFirstName(),
                createCustomer.getLastName(),
                createCustomer.getEmail(),
                new String(passwordHashBytes, StandardCharsets.UTF_8));

        return customerRepository.create(customer);
    }

    public boolean isUniqueCredentials(String email, String phoneNumber){
        return customerRepository.getByEmail(email) == null && customerRepository.getByPhoneNumber(phoneNumber) == null;
    }
}
