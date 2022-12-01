package com.mom_pop_pizza.app.data;

import liquibase.Contexts;
import liquibase.LabelExpression;
import liquibase.Liquibase;
import liquibase.Scope;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.resource.ClassLoaderResourceAccessor;

import java.sql.DriverManager;
import java.util.HashMap;
import java.util.Map;

public class MigrationRunner {
    public void runMigrations() throws Exception {
        Map<String, Object> config = new HashMap<>();

        Scope.child(config, () -> {
            var con = DriverManager.getConnection("jdbc:sqlite:Mom_Pop_Pizza.db");

            Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(con));

            Liquibase liquibase = new liquibase.Liquibase("changelog.sql", new ClassLoaderResourceAccessor(), database);

            liquibase.update(new Contexts(), new LabelExpression());
        });
    }
}
