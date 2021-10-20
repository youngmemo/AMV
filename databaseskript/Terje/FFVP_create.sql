USE ffvp;

/*first add "leaf" entities - the slowly changing category type tables, since they define PKs for the FKs in the "core" tables*/

CREATE OR REPLACE TABLE Customer (
    CustomerID SMALLINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    CName VARCHAR(50) NOT NULL,
    Phone VARCHAR(50) NOT NULL,
    HomeAddress VARCHAR(50) NOT NULL
);

CREATE OR REPLACE TABLE PizzaType (
    PizzaTypeID SMALLINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    PizzaName VARCHAR(50) NOT NULL
);

CREATE OR REPLACE TABLE PizzaStatus (
    PizzaStatusID SMALLINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    PizzaStatus VARCHAR(50) NOT NULL
);

CREATE OR REPLACE TABLE PaymentStatus (
    PaymentStatusID SMALLINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    PaymentStatus VARCHAR(50) NOT NULL
);

CREATE OR REPLACE TABLE DroneStatus (
    DroneStatusID SMALLINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    DroneStatus VARCHAR(50) NOT NULL
);

/* Now we got all the types and status tables, and can create the orders and orderline tables. Orders first since it provides PK for OrderLine FK */

CREATE OR REPLACE TABLE Orders ( /* Named `orders` not `order` to avoid error caused by overlap with keyword */
    OrderID SMALLINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    OrderTime timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    DeliveryAddress VARCHAR(50) NOT NULL,
    CustomerID SMALLINT, /* Can't be NOT NULL since we have ON DELETE rule SET NULL below */
    DroneStatusID SMALLINT,
    PaymentStatusID SMALLINT,
    CONSTRAINT fk_customerID FOREIGN KEY (CustomerID) REFERENCES Customer (CustomerID) ON DELETE SET NULL,  /* See comment above */
    CONSTRAINT fk_droneStatusID FOREIGN KEY (DroneStatusID) REFERENCES DroneStatus (DroneStatusID) ON DELETE SET NULL,
    CONSTRAINT fk_paymentStatusID FOREIGN KEY (PaymentStatusID) REFERENCES PaymentStatus (PaymentStatusID) ON DELETE SET NULL
);


CREATE OR REPLACE TABLE OrderLine (
    OrderLineID SMALLINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    OrderID SMALLINT NOT NULL, /* Makes no sense to have an orderline without an order. Therefore also ON DELETE CASCADE */
    PizzaTypeID SMALLINT,
    PizzaStatusID SMALLINT,
    NumberOfPizzas SMALLINT NOT NULL DEFAULT 1,
    Size VARCHAR(50) NOT NULL DEFAULT 'OneSizeFitsAll',
    CONSTRAINT fk_orderID FOREIGN KEY (OrderID) REFERENCES Orders (OrderID) ON DELETE CASCADE, /* See comment above */
    CONSTRAINT fk_pizzaTypeID FOREIGN KEY (PizzaTypeID) REFERENCES PizzaType (PizzaTypeID) ON DELETE SET NULL,
    CONSTRAINT fk_pizzaStatusID FOREIGN KEY (PizzaStatusID) REFERENCES PizzaStatus (PizzaStatusID) ON DELETE SET NULL
);