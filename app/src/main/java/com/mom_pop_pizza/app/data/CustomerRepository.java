package com.mom_pop_pizza.app.data;

import com.mom_pop_pizza.app.data.domainmodels.Customer;
import com.mom_pop_pizza.app.data.entities.CustomerEntity;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import java.util.UUID;

public class CustomerRepository {

    private EntityManager entityManager;

    public CustomerRepository() {
        var factory = Persistence.createEntityManagerFactory("customer_pu");
        this.entityManager = factory.createEntityManager();
    }

    public Customer[] getAll(){
        return null;
    }

    public Customer getById(UUID id){
        return null;
    }

    public Customer getByPhoneNumber(String phoneNumber){
        var query = entityManager.createNamedQuery("selectWherePhoneNumber", CustomerEntity.class);
        query.setParameter("phoneNumber", phoneNumber);
        CustomerEntity customerEntity;
        try{
            customerEntity = query.getSingleResult();
        }
        catch (NoResultException e){
            return null;
        }

        return toCustomer(customerEntity);
    }

    public Customer getByEmail(String email){
        var query = entityManager.createNamedQuery("selectWhereEmail", CustomerEntity.class);
        query.setParameter("email", email);
        CustomerEntity customerEntity;
        try{
            customerEntity = query.getSingleResult();
        }
        catch (NoResultException e){
            return null;
        }

        return toCustomer(customerEntity);
    }

    public boolean update(Customer model){
        return false;
    }

    public boolean delete(Customer model){
        return false;
    }

    public boolean create(Customer model){
        var transaction = entityManager.getTransaction();
        transaction.begin();

        var customer = new CustomerEntity(
            model.getPhoneNumber(),
            model.getFirstName(),
            model.getLastName(),
            model.getEmail(),
            model.getPasswordHash());

        entityManager.persist(customer);

        transaction.commit();
        return true;
    }

    private Customer toCustomer(CustomerEntity entity){
        return new Customer(entity.getPhoneNumber(),
                entity.getFirstName(),
                entity.getLastName(),
                entity.getEmail(),
                entity.getPasswordHash());
    }
}
