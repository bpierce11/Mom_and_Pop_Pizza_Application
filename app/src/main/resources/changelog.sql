CREATE TABLE IF NOT EXISTS Customers (
        id blob not null,
        PhoneNumber varchar(20) not null UNIQUE,
        Email varchar(100) not null UNIQUE,
        FirstName varchar(30) not null,
        LastName varchar(50) not null,
        PasswordHash varchar(64) not null,
        primary key (id)
    );