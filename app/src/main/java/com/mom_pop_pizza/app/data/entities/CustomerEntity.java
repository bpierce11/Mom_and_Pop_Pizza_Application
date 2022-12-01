package com.mom_pop_pizza.app.data.entities;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "Customers")
@NamedQuery(name = "selectWherePhoneNumber", query = "SELECT c FROM CustomerEntity c WHERE c.phoneNumber = :phoneNumber")
@NamedQuery(name = "selectWhereEmail", query = "SELECT c FROM CustomerEntity c WHERE lower(c.email) = lower(:email)")
public class CustomerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "PhoneNumber", nullable = false, length = 20)
    private String phoneNumber;

    @Column(name = "FirstName", nullable = false, length = 30)
    private String firstName;

    @Column(name = "LastName", nullable = false, length = 50)
    private String lastName;

    @Column(name = "Email", nullable = false, length = 100)
    private String email;

    @Column(name = "PasswordHash", nullable = false, length = 64)
    private String passwordHash;

    public CustomerEntity() { }

    public CustomerEntity(String phoneNumber, String firstName, String lastName, String email, String passwordHash) {
        this.phoneNumber = phoneNumber;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.passwordHash = passwordHash;
    }

    public UUID getId() {
        return id;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }
}
