DROP DATABASE Webshop;

<<<<<<< HEAD
CREATE DATABASE WEBSHOP;
use WEBSHOP;

CREATE TABLE Customers (
                          kundeID integer PRIMARY KEY NOT NULL,
                           Fornavn varchar (30) NOT NULL,
                           Etternavn varchar (30) NOT NULL,
                           Epost varchar (40) NOT NULL,
                           Telefonnummer integer (8) NOT NULL,
                           Passord varchar (20) NOT NULL,
                           Adresse varchar (20) NOT NULL,
                           Postnummer int (4) NOT NULL

);
INSERT INTO Customers (kundeID, Fornavn, Etternavn, Epost, Telefonnummer, Passord, Adresse, Postnummer)
values(1, 'Tom', 'Larsen', 'tomlarsen@hotmail.com', 41384997, 'tomlarsen123', 'solbakken 7', 4626);
INSERT INTO Customers (kundeID, Fornavn, Etternavn, Epost, Telefonnummer, Passord, Adresse, Postnummer)
values(2,'Petter', 'Jakobsen', 'petterjakobsen@hotmail.com', 98643913, 'petter123', 'åsane 12', 4020 );
INSERT INTO Customers (kundeID, Fornavn, Etternavn, Epost, Telefonnummer, Passord, Adresse, Postnummer)
values(3,'Jakob', 'Sørensen', 'jakobsørensen@hotmail.com', 48089121, 'jakob123', 'møvig 15', 4515 );

update Customers
SET Adresse='Tranestien 9'
WHERE kundeID=3;

DELETE FROM Customers
WHERE Fornavn='Tom';

INSERT INTO Customers (kundeID, Fornavn, Etternavn, Epost, Telefonnummer, Passord, Adresse, Postnummer)
values(4,'Mehmet', 'Eksi', 'MehmetEksi@hotmail,com', 41624571, 'memo123', 'Slettheiveien 66', 4626 );

INSERT INTO Customers (kundeID, Fornavn, Etternavn, Epost, Telefonnummer, Passord, Adresse, Postnummer)
values(5,'Berat', 'Gunes', 'BeratGunes@hotmail.com', 41654751, 'berat', 'Tinnheiveien 15', 4208 );



SELECT kundeID, Fornavn, Epost FROM Customers;
=======
CREATE DATABASE Webshop;

use Webshop;

CREATE TABLE kunder (
                        KundeID integer PRIMARY KEY NOT NULL ,
                        Fornavn varchar (30) NOT NULL,
                        Etternavn varchar (30) NOT NULL ,
                        Passord varchar (60) NOT NULL ,
                        Epost varchar (40) NOT NULL ,
                        Telefonnummer integer (8) NOT NULL ,
                        Adresse varchar (40) NOT NULL ,
                        Postnummer integer (4) NOT NULL


);

INSERT INTO kunder (KundeID, Fornavn, Etternavn, Passord, Epost, Telefonnummer, Adresse, Postnummer)
values (1,'Kristian','Larsen','adnasdjasudu22','kristianlarsen@gmail.com',92830112,'Bramstadveien 28',4626);

INSERT INTO kunder (KundeID, Fornavn, Etternavn, Passord, Epost, Telefonnummer, Adresse, Postnummer)
values (2,'Adam','Sin','sadasidsaid21','adamsin@gmail.com',92822112,'Hallaveien 18',4626);

INSERT INTO kunder (KundeID, Fornavn, Etternavn, Passord, Epost, Telefonnummer, Adresse, Postnummer)
values (3,'Bjørn','Olsen','iajsdiasjd17','bjornolsen@gmail.com',2819228,'Furuveien 28',4626);

UPDATE kunder
SET adresse='Mehmetveien'
WHERE KundeID=1;

UPDATE kunder
SET adresse='Abdulveien'
WHERE KundeID=2;

UPDATE kunder
SET adresse='Omerveien'
WHERE KundeID=3;

DELETE FROM kunder
where Fornavn='Adam';

INSERT INTO kunder (KundeID, Fornavn, Etternavn, Passord, Epost, Telefonnummer, Adresse, Postnummer)
values (2, 'Mehmet', 'Eksi', 'babanisikem12', 'mehmeteksi@gmail.com', 48573991, 'Memo', 4626);

INSERT INTO kunder (KundeID, Fornavn, Etternavn, Passord, Epost, Telefonnummer, Adresse, Postnummer)
values (4, 'Abdul', 'Kasim', 'pakistani211', 'abdulkasim@gmail.com', 2847719, 'pakistaniwarrior', 4626);

SELECT KundeID, Fornavn, Epost FROM kunder;
>>>>>>> 9df1a34769f589da7dd8ca92d4c813d92505263e

CREATE TABLE ordre (
                       OrderID integer PRIMARY KEY NOT NULL,
                       KundeID integer NOT NULL ,
<<<<<<< HEAD
                       OrdreDato date NOT NULL,
                       Produkter varchar (50),
                       FOREIGN KEY (KundeID) REFERENCES Customers(KundeID)


);

INSERT INTO ordre (OrderID, KundeID, OrdreDato, Produkter)
values(1, 4, '2011-11-11', 'memo');


=======
                       OrdreDato date NOT NULL ,
                       Produkter varchar (50) ,
                       FOREIGN KEY (KundeID) REFERENCES kunder(KundeID)
);

INSERT INTO ordre (OrderID, KundeID, OrdreDato, Produkter)
values (1, 2, '2011-11-11', 'Fatima sin nesehårsfjerner');
>>>>>>> 9df1a34769f589da7dd8ca92d4c813d92505263e
