DROP DATABASE Webshop;

CREATE DATABASE Webshop;

use Webshop;

CREATE TABLE customers (
                           KundeID integer NOT NULL ,
                           Fornavn varchar (30) NOT NULL,
                           Etternavn varchar (30) NOT NULL ,
                           Passord char (60) NOT NULL ,
                           Epost varchar (40) NOT NULL ,
                           Telefonnummer integer (8) NOT NULL ,
                           Adresse varchar (40) NOT NULL ,
                           Postnummer integer (4) NOT NULL


);

INSERT INTO customers (KundeID, Fornavn, Etternavn, Epost, Passord, Telefonnummer, Adresse, Postnummer)
values (1,'Kristian','Larsen','adnasdjasudu22','kristianlarsen@gmail.com',92830112,'Bramstadveien 28',4626);

INSERT INTO customers (KundeID, Fornavn, Etternavn, Epost, Passord, Telefonnummer, Adresse, Postnummer)
values (2,'Adam','Sin','sadasidsaid21','adamsin@gmail.com',92822112,'Hallaveien 18',4626);

INSERT INTO customers (KundeID, Fornavn, Etternavn, Epost, Passord, Telefonnummer, Adresse, Postnummer)
values (3,'Bjørn','Olsen','iajsdiasjd17','bjornolsen@gmail.com',2819228,'Furuveien 28',4626);

UPDATE customers
SET adresse='Mehmetveien'
WHERE KundeID=1;

UPDATE customers
SET adresse='Abdulveien'
WHERE KundeID=2;

UPDATE customers
SET adresse='Omerveien'
WHERE KundeID=3;

DELETE FROM customers
where Fornavn='Adam';

INSERT INTO customers (KundeID, Fornavn, Etternavn, Epost, Passord, Telefonnummer, Adresse, Postnummer)
values (2, 'Mehmet', 'Eksi', 'babanisikem12', 'mehmeteksi@gmail.com', 48573991, 'Memo', 4626);

INSERT INTO customers (KundeID, Fornavn, Etternavn, Epost, Passord, Telefonnummer, Adresse, Postnummer)
values (4, 'Abdul', 'Kasim', 'pakistani211', 'abdulkasim@gmail.com', 2847719, 'pakistaniwarrior', 4626);

INSERT INTO customers (KundeID, Fornavn, Etternavn, Epost, Passord, Telefonnummer, Adresse, Postnummer)
values (4, 'Osamah', 'Al-Maliki', 'iraki212', 'osamahalmaliki@gmail.com', 28578992, 'iraqiwarrior', 4626);

SELECT KundeID, Fornavn, Epost FROM customers;