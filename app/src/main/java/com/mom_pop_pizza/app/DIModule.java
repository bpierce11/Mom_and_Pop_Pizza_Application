package com.mom_pop_pizza.app;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.mom_pop_pizza.app.data.CustomerRepository;
import com.mom_pop_pizza.app.data.StateContainer;
import com.mom_pop_pizza.app.data.domainmodels.Order;
import com.mom_pop_pizza.app.services.AccountManager;
import com.mom_pop_pizza.app.services.LoginManager;

import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DIModule extends AbstractModule {

    private ArrayList<SimpleEntry<Class, Object>> singletons = new ArrayList<>();

    @Override
    protected void configure() {
        bind(CustomerRepository.class);
        bind(LoginManager.class);
        bind(AccountManager.class);
        bind(StateContainer.class).toInstance(new StateContainer(new Order()));
        for (var singleton : singletons){
            bind(singleton.getKey()).toInstance(singleton.getValue());
        }
    }

    public <T> void addSingleton(Class<T> classType, T instance){
        singletons.add(new SimpleEntry<>(classType, instance));
    }

    public Injector createInjector(){
        return Guice.createInjector(this);
    }
}
