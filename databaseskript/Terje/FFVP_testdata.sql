USE ffvp;
delete from OrderLine;
delete from Orders;
delete from Customer;
delete from DroneStatus;
delete from PizzaStatus;
delete from PaymentStatus;
delete from PizzaType;
insert into Customer (`CustomerID`, `CName`, `Phone`, `HomeAddress`) /* Backticks are optional, needed to escape some names */
values 
(1, 'Terje', '42424242', 'Bortebakken 42, Kristiansand'),
(2, 'NotTerje', '42424242', 'Borteveien 69, Hokksund'),
(3, 'Terje3', '42424242', 'Bortebakken 44, Kristiansand'),
(4, 'Terje4', '42424242', 'Bortebakken 45, Kristiansand'),
(5, 'Terje5', '42424242', 'Bortebakken 46, Kristiansand')
;
select * from Customer;


insert into DroneStatus (DroneStatusID, DroneStatus)
values
(1, 'NoDronesAvailable'),
(2, 'Ready'),
(3, 'OnTheWay'),
(4, 'Returning'),
(5, 'Done')
;
select * from Dronestatus;


insert into PaymentStatus (PaymentStatusID, PaymentStatus)
values
(1, 'Accepted'),
(2, 'Pending'),
(3, 'Rejected')
;
select * from PaymentStatus;


insert into PizzaStatus (PizzaStatusID, PizzaStatus)
values
(1, 'Ordered'),
(2, 'Baking'),
(3, 'Done')
;
select * from PizzaStatus;


insert into PizzaType (PizzaTypeID, PizzaName)
values
(1,'Four Cheeses Pizza'),
(2,'Gorgonzola Pizza'),
(3,'Margherita Pizza'),
(4,'Goat Cheese and Onions Pizza'),
(5,'American Cheese'),
(6,'Veg. Flammkuchen')
;
select * from PizzaType;

insert into Orders (OrderID, DeliveryAddress, CustomerID, DroneStatusID, PaymentStatusID) /* Named `orders` not `order` to avoid error caused by overlap with keyword */
values
(1, 'Bortebakken 42, Kristiansand', 1, 2, 2),
(2, 'Fakeaddress 69, Hokksund', 2, 1, 3),
(3, 'Bortebakken 42a', 1, 3, 3),
(4, 'Bortebakken 44', 3, 4, 1)
;
select * from Orders;

insert into OrderLine (OrderID, PizzaTypeID, PizzaStatusID)
values
(1, 1, 1),

(1, 2, 2),
(2, 4, 1),
(3, 6, 1),
(4, 5, 3)
;
insert into OrderLine (OrderID, PizzaTypeID, PizzaStatusID,NumberOfPizzas)
values
(3, 5, 1, 100);
select * from OrderLine;
